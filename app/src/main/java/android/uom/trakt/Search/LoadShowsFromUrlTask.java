package android.uom.trakt.Search;

import android.content.Context;
import android.os.AsyncTask;

import android.uom.trakt.BuildConfig;
import android.uom.trakt.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lydts on 1/16/2018.
 */

public class LoadShowsFromUrlTask extends AsyncTask<String,String,String> {
    private IShowsLoadedListener iShowsLoadedListener;
    Context mContext;
    String mUrl;
    int scroll;

    private final String USER_AGENT = "Android";

    public LoadShowsFromUrlTask(Context mContext, String mUrl, IShowsLoadedListener iShowsLoadedListener) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.iShowsLoadedListener = iShowsLoadedListener;
        int scroll = 0;

    }

    public LoadShowsFromUrlTask(Context mContext, String mUrl, int scroll, IShowsLoadedListener iShowsLoadedListener) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.iShowsLoadedListener = iShowsLoadedListener;
        this.scroll = scroll;

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            if (this.isCancelled())
                return "cancled";

            URL obj = new URL(this.mUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            /**
             Content-Type:application/json
             trakt-api-version:2
             trakt-api-key:[client_id]
             * */
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("trakt-api-version", "2");
            con.setRequestProperty("trakt-api-key", BuildConfig.CLIENT_ID);

            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);

            if (this.isCancelled())
                return "cancled";

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                if (this.isCancelled())
                    return "cancled";
                return response.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if (s.equals("cancled") || this.isCancelled())
            return;

        ArrayList<Show> showList = new ArrayList<>();
            if(mUrl.contains("popular"))
                showList = parsePopularResponse(s);
            else if(mUrl.contains("trending"))
                showList = parseTrendingResponse(s);
        if (s.equals("cancled") || this.isCancelled())
            return;
        iShowsLoadedListener.onShowsLoaded(showList,this.scroll);
    }

    private ArrayList<Show> parsePopularResponse(String s) {
        ArrayList<Show> showList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Show show = new Show();
                System.out.println("Got in popular shows");
                if (!jsonObject.isNull("title"))
                    show.setTitle(jsonObject.getString("title"));
                if (!jsonObject.isNull("year"))
                    show.setYear(jsonObject.getInt("year"));
                if (!jsonObject.isNull("overview"))
                    show.setOverview(jsonObject.getString("overview"));
                if (!jsonObject.isNull("runtime"))
                    show.setRuntime(jsonObject.getInt("runtime"));
                if (!jsonObject.isNull("trailer"))
                    show.setTrailer(jsonObject.getString("trailer"));
                if (!jsonObject.isNull("rating"))
                    show.setRating(jsonObject.getDouble("rating"));

                if (!jsonObject.isNull("ids")) {
                    JSONObject joID = jsonObject.getJSONObject("ids");
                    if (!joID.isNull("trakt"))
                        show.setTrakt(joID.getLong("trakt"));
                    if (!joID.isNull("imdb"))
                        show.setImdb(joID.getString("imdb"));
                    if (!joID.isNull("tmdb")) {
                        show.setTmdb(joID.getString("tmdb"));
                        System.out.println("TMDB: " +joID.getInt("tmdb"));
                        new LoadShowImagesFromUrlTask(show).execute();
                    }
                    if (!joID.isNull("slug"))
                        show.setSlug(joID.getString("slug"));
                }
                showList.add(show);
            }
            return showList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Show> parseTrendingResponse(String s) {
        System.out.println("Got in trending shows");
        ArrayList<Show> showList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Show show = new Show();
                if(!jsonObject.isNull("show")) {
                    jsonObject = jsonObject.getJSONObject("show");

                    if (!jsonObject.isNull("title"))
                        show.setTitle(jsonObject.getString("title"));
                    if (!jsonObject.isNull("year"))
                        show.setYear(jsonObject.getInt("year"));
                    if (!jsonObject.isNull("overview"))
                        show.setOverview(jsonObject.getString("overview"));
                    if (!jsonObject.isNull("runtime"))
                        show.setRuntime(jsonObject.getInt("runtime"));
                    if (!jsonObject.isNull("trailer"))
                        show.setTrailer(jsonObject.getString("trailer"));
                    if (!jsonObject.isNull("rating"))
                        show.setRating(jsonObject.getDouble("rating"));

                    if (!jsonObject.isNull("ids")) {
                        JSONObject joID = jsonObject.getJSONObject("ids");
                        if (!joID.isNull("imdb"))
                            show.setImdb(joID.getString("imdb"));
                        if (!joID.isNull("tmdb"))
                            show.setTmdb(joID.getString("tmdb"));
                        if (!joID.isNull("trakt"))
                            show.setTrakt(joID.getInt("trakt"));
                        if (!joID.isNull("slug"))
                            show.setSlug(joID.getString("slug"));
                    }
                    new LoadShowImagesFromUrlTask(show).execute();
                    showList.add(show);
                }  //uncomment for trending movies
            }
            return showList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
