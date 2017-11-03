package com.example.lokesh.musicsearch;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lokesh on 14/10/2017.
 */

public class SharedPreference {

    public static final String PREFS_NAME = "MyMusicPref";
    public static final String FAV_TRACK_LIST = "favTrackList";

    public SharedPreference() {
        super();
    }

    public void saveFavorites(Context context, List<Track> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAV_TRACK_LIST, jsonFavorites);
        editor.commit();
        MainActivity.adapter.notifyDataSetChanged();
    }

    public void addFavorite(Context context, Track track) {
        List<Track> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Track>();
        favorites.add(track);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Track track) {
        ArrayList<Track> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(track);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Track> getFavorites(Context context) {
        SharedPreferences settings;
        ArrayList<Track> favorites;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAV_TRACK_LIST)) {
            String jsonFavorites = settings.getString(FAV_TRACK_LIST, null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Track>>() {
            }.getType();
            favorites = gson.fromJson(jsonFavorites, type);
        } else
            return favorites = new ArrayList<>();

        return (ArrayList<Track>) favorites;
    }


}