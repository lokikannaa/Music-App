package com.example.lokesh.musicsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Lokesh on 11/10/2017.
 */

public class SearchMusicAsyncTask extends AsyncTask<String, Void, ArrayList<Track>> {

    IQuestionData iActivity;
    public SearchMusicAsyncTask(IQuestionData activity) {this.iActivity = activity;}

    public static interface IQuestionData{
        public void setData(ArrayList<Track> trackList);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ArrayList<Track> doInBackground(String... params) {
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    if(params[1].equals("SEARCH")) {
                        return TrackUtil.JSONParsor.parseTrack(sb.toString());
                    }else if(params[1].equals("GET_SIMILAR")){
                        return TrackUtil.JSONParsor.parseSimilarTrack(sb.toString());
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPostExecute(ArrayList<Track> trackList) {
        iActivity.setData(trackList);
    }
}
