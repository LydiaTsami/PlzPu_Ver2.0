package com.unbounds.trakt.Search;

import android.content.Context;
import android.os.AsyncTask;

import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.api.model.Movie;

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

public class LoadMoviesFromUrlTask extends AsyncTask<String,String,String> {
    private IMoviesLoadedListener iMoviesLoadedListener;
    Context mContext;
    String mUrl;
    int scroll;

    private final String USER_AGENT = "Android";

    public LoadMoviesFromUrlTask(Context mContext, String mUrl, IMoviesLoadedListener iMoviesLoadedListener) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.iMoviesLoadedListener = iMoviesLoadedListener;
        int scroll = 0;

    }

    public LoadMoviesFromUrlTask(Context mContext, String mUrl, int scroll, IMoviesLoadedListener iMoviesLoadedListener) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.iMoviesLoadedListener = iMoviesLoadedListener;
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

        ArrayList<Movie> movieList = new ArrayList<>();

            if(mUrl.contains("popular"))
                movieList = parsePopularResponse(s);
            else if(mUrl.contains("trending"))
                movieList = parseTrendingResponse(s);
        if (s.equals("cancled") || this.isCancelled())
            return;
        iMoviesLoadedListener.onPopularMoviesLoaded(movieList,this.scroll);
    }

    private ArrayList<Movie> parsePopularResponse(String s) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                    System.out.println("Got in popular");
                if (!jsonObject.isNull("title"))
                    movie.setTitle(jsonObject.getString("title"));
                if (!jsonObject.isNull("year"))
                    movie.setYear(jsonObject.getInt("year"));
                if (!jsonObject.isNull("tagline"))
                    movie.setTagline(jsonObject.getString("tagline"));
                if (!jsonObject.isNull("overview"))
                    movie.setOverview(jsonObject.getString("overview"));
                if (!jsonObject.isNull("runtime"))
                    movie.setRuntime(jsonObject.getInt("runtime"));
                if (!jsonObject.isNull("trailer"))
                    movie.setTrailer(jsonObject.getString("trailer"));
                if (!jsonObject.isNull("homepage"))
                    movie.setHomepage(jsonObject.getString("homepage"));
                if (!jsonObject.isNull("rating"))
                    movie.setRating(jsonObject.getDouble("rating"));
                if (!jsonObject.isNull("genres")) {
                    JSONArray jj = jsonObject.getJSONArray("genres");
                    movie.setGenres(jj.toString());
                }

                if (!jsonObject.isNull("ids")) {
                    JSONObject joID = jsonObject.getJSONObject("ids");
                    if (!joID.isNull("trakt"))
                        movie.setTrakt(joID.getLong("trakt"));
                    if (!joID.isNull("imdb"))
                        movie.setImdb(joID.getString("imdb"));
                    if (!joID.isNull("tmdb")) {
                        movie.setTmdb(joID.getString("tmdb"));
                        new LoadImagesFromUrlTask(movie).execute();
                    }
                    if (!joID.isNull("slug"))
                        movie.setSlug(joID.getString("slug"));
                }
                System.out.println("Popular Movie : "+movie.getTitle() +" pathh: " +movie.getImageUrl());
                movieList.add(movie);
            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Movie> parseTrendingResponse(String s) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();
                    System.out.println("Got in trending");
                    if(!jsonObject.isNull("movie")) {
                        jsonObject = jsonObject.getJSONObject("movie");

                        if (!jsonObject.isNull("title"))
                            movie.setTitle(jsonObject.getString("title"));
                        if (!jsonObject.isNull("year"))
                            movie.setYear(jsonObject.getInt("year"));
                        if (!jsonObject.isNull("tagline"))
                            movie.setTagline(jsonObject.getString("tagline"));
                        if (!jsonObject.isNull("overview"))
                            movie.setOverview(jsonObject.getString("overview"));
                        if (!jsonObject.isNull("runtime"))
                            movie.setRuntime(jsonObject.getInt("runtime"));
                        if (!jsonObject.isNull("trailer"))
                            movie.setTrailer(jsonObject.getString("trailer"));
                        if (!jsonObject.isNull("homepage"))
                            movie.setHomepage(jsonObject.getString("homepage"));
                        if (!jsonObject.isNull("rating"))
                            movie.setRating(jsonObject.getDouble("rating"));
                        if (!jsonObject.isNull("genres")) {
                            JSONArray jj = jsonObject.getJSONArray("genres");
                            movie.setGenres(jj.toString());
                        }

                        if (!jsonObject.isNull("ids")) {
                            JSONObject joID = jsonObject.getJSONObject("ids");
                            if (!joID.isNull("imdb"))
                                movie.setImdb(joID.getString("imdb"));
                            if (!joID.isNull("tmdb"))
                                movie.setTmdb(joID.getString("tmdb"));
                            if (!joID.isNull("trakt"))
                                movie.setTrakt(joID.getInt("trakt"));
                            if (!joID.isNull("slug"))
                                movie.setSlug(joID.getString("slug"));
                        }
                        new LoadImagesFromUrlTask(movie).execute();
                        System.out.println("Trending Movie : "+movie.getTitle() +" pathh: " +movie.getImageUrl());
                        movieList.add(movie);
                    }  //uncomment for trending movies
                }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
