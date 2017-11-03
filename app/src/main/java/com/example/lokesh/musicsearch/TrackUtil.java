package com.example.lokesh.musicsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Lokesh on 11/10/2017.
 */

public class TrackUtil {
    static public class JSONParsor {
        static ArrayList<Track> parseTrack(String in) throws JSONException, ParseException {
            ArrayList<Track> TracksList = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray trackArray = root.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");
            for (int count = 0; count < trackArray.length(); count++) {
                JSONObject trackJSONObject = trackArray.getJSONObject(count);
                Track track = createFromJSON1(trackJSONObject);
                TracksList.add(track);
            }
            return TracksList;
        }


        static ArrayList<Track> parseSimilarTrack(String in) throws JSONException, ParseException {
            ArrayList<Track> TracksList = new ArrayList<>();
            JSONObject root = new JSONObject(in);
            JSONArray trackArray = root.getJSONObject("similartracks").getJSONArray("track");
            for (int count = 0; count < trackArray.length(); count++) {
                JSONObject trackJSONObject = trackArray.getJSONObject(count);
                Track track = createFromJSON2(trackJSONObject);
                TracksList.add(track);
            }
            return TracksList;
        }

        static Track createFromJSON2(JSONObject jsonObj) throws JSONException, ParseException {
            Track track = new Track();

            track.setName(jsonObj.getString("name"));
            track.setArtist(jsonObj.getJSONObject("artist").getString("name"));
            track.setMainURL(jsonObj.getString("url"));
            track.setSmallImageURL(jsonObj.getJSONArray("image").getJSONObject(0).getString("#text"));
            track.setLargeImageURL(jsonObj.getJSONArray("image").getJSONObject(2).getString("#text"));

            return track;
        }

        static Track createFromJSON1(JSONObject jsonObj) throws JSONException, ParseException {
            Track track = new Track();

            track.setName(jsonObj.getString("name"));
            track.setArtist(jsonObj.getString("artist"));
            track.setMainURL(jsonObj.getString("url"));
            track.setSmallImageURL(jsonObj.getJSONArray("image").getJSONObject(0).getString("#text"));
            track.setLargeImageURL(jsonObj.getJSONArray("image").getJSONObject(2).getString("#text"));

            return track;
        }


    }
}
