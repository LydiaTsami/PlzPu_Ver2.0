package com.unbounds.trakt.Search;

import android.os.AsyncTask;
import android.util.Log;

import com.unbounds.trakt.BuildConfig;
import com.unbounds.trakt.api.model.Show;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lydts on 1/14/2018.
 */

public class LoadShowImagesFromUrlTask extends AsyncTask<String,String,String> {
    private Show show;
    int responseCode;
    String fullimageurl;

    public LoadShowImagesFromUrlTask(Show show){
        this.show=show;
    }

    @Override
    protected String doInBackground(String... params){
        if(show.getTitle()!=null) {
            String url = "https://api.themoviedb.org/3/tv/" + show.getTmdb() + "?api_key=" + BuildConfig.TMDB_API_KEY;
            System.out.println("Image path shows: " + url);
            try {
                URL obj = new URL(url);
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
        System.out.println("Response code for show images: " +responseCode);
        if(show.getTitle()!=null)
            parseResponse(s);
    }

    private void parseResponse(String s) {
        String imagepath = "https://image.tmdb.org/t/p/w500";
        try {
            if(s!=null) {
                JSONObject jsonObject = new JSONObject(s);
                String path = null;
                path = jsonObject.getString("poster_path");
                imagepath = imagepath + path;
            }
        } catch (JSONException e) {
            System.out.println("json ex: " + e);
        }
        show.setUrl(imagepath);
    }

}