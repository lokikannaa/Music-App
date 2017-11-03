package com.example.lokesh.musicsearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.LayoutRes;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Lokesh on 11/10/2017.
 */

public class TrackListAdapter extends ArrayAdapter<Track> {

    Context context;
    int resource;
    List<Track> trackList;
    SharedPreference sharedPreference;
    String pageName;

    public TrackListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Track> objects, String pageName) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.trackList = objects;
        this.pageName = pageName;
        sharedPreference = new SharedPreference();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        final String URL;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);
        }

        ImageView ivSmallImage = (ImageView) convertView.findViewById(R.id.ivSmallImage);
        TextView tvTrackName = (TextView) convertView.findViewById(R.id.tvTrackName);
        TextView tvArtist = (TextView) convertView.findViewById(R.id.tvArtist);
        final ImageButton ibFavorite = (ImageButton) convertView.findViewById(R.id.ibFavourite);

        if (sharedPreference.getFavorites(context) != null) {
            MainActivity.favCount = sharedPreference.getFavorites(context).size();
        }

        if (checkFavoriteItem(trackList.get(position))) {
            ibFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            ibFavorite.setTag("Fav");
        } else {
            ibFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            ibFavorite.setTag("Non_Fav");
        }

        ibFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Track track = (Track) trackList.get(position);
                String tag = ibFavorite.getTag().toString();
                if (tag.equalsIgnoreCase("Non_Fav")) {
                    if (MainActivity.favCount < 20) {
                        sharedPreference.addFavorite(context, track);
                        ibFavorite.setTag("Fav");
                        ibFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                        MainActivity.favCount++;
                        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cannot add more than 20 items in Favourites!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    MainActivity.favCount--;
                    ibFavorite.setTag("Non_Fav");
                    if (pageName.equalsIgnoreCase("MainActivity")){
                        MainActivity ma = new MainActivity();
                        trackList.remove(position);
                        MainActivity.adapter.notifyDataSetChanged();
                    }
                    sharedPreference.removeFavorite(context, track);
                    ibFavorite.setImageResource(android.R.drawable.btn_star_big_off);
                    Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvTrackName.setText(trackList.get(position).getName());
        tvArtist.setText(trackList.get(position).getArtist());
        Picasso.with(context)
                .load(trackList.get(position).getSmallImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivSmallImage);

        return convertView;
    }

    public boolean checkFavoriteItem(Track checkTrack) {
        boolean check = false;

        List<Track> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (Track track : favorites) {
                if (track.equals(checkTrack)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

}
