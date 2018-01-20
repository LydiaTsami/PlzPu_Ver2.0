package android.uom.trakt.Search;

import android.os.AsyncTask;
import android.util.Log;

import android.uom.trakt.BuildConfig;
import android.uom.trakt.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lydts on 1/13/2018.
 */

public class LoadImagesFromUrlTask extends AsyncTask<String,String,String> {
    private Movie movie;
    int responseCode;
    String fullimageurl;

    public LoadImagesFromUrlTask(Movie movie){
        this.movie=movie;
    }

    @Override
    protected String doInBackground(String... params){
        if(movie.getTitle()!=null) {
            String movieurl = "https://api.themoviedb.org/3/movie/" + movie.getTmdb() + "?api_key=" + BuildConfig.TMDB_API_KEY;
            try {
                URL obj = new URL(movieurl);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                responseCode = con.getResponseCode();

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
            } catch (Exception e) {
                Log.i("Response error ", e + "");
            }

            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println("Response code: " +responseCode);
        if(responseCode == 200)
          parseResponse(s);
    }

    private void parseResponse(String s) {
        String imagepath = "https://image.tmdb.org/t/p/w500";
        try {
            JSONObject jsonObject = new JSONObject(s);
            String path=null;
            path = jsonObject.getString("poster_path");
            imagepath = imagepath + path;
        } catch (JSONException e) {
            System.out.println("json ex: " + e);
        }
        movie.setUrl(imagepath);
    }

}
