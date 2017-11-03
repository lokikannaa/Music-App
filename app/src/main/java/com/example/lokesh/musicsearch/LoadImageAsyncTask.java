package com.example.lokesh.musicsearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Lokesh on 11/10/2017.
 */

class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap>  {
    IData mainActivity;

    public LoadImageAsyncTask(IData mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mainActivity.setupImage(bitmap);
    }

    public static interface IData {
        public void setupImage(Bitmap bitmap);
    }
}

