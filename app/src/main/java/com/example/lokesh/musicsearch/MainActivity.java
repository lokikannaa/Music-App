package com.example.lokesh.musicsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends BaseActivity implements SearchMusicAsyncTask.IQuestionData{

    ArrayList<Track> trackList;
    static boolean main_Quit = false;

    EditText etSeachMusic;
    static final String FAV_TRACK_LIST = "favTrackList";
    String URL;
    static TrackListAdapter adapter;
    static ArrayList<Track> favTrackList;
    static  int favCount = 0;
    SharedPreference sharedPreference;
    static ListView lvFav;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(main_Quit){
            finish();
        }
        etSeachMusic = (EditText) findViewById(R.id.etSearchText);
        sharedPreference = new SharedPreference();
        lvFav = (ListView) findViewById(R.id.lvFavorites);

        trackList = new ArrayList<>();
        favTrackList = new ArrayList<>();
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSeachMusic.getText().toString().equals("")) {
                    String val = etSeachMusic.getText().toString();
                    val.replaceAll(" ","%20");
                    URL = "http://ws.audioscrobbler.com/2.0/?format=json&method=track.search&track=" + val + "&api_key=79e45983d0688a923a9bf72302a9a0df&limit=20";
                    Log.d("demo","onClick1:" + URL);
                    new SearchMusicAsyncTask(MainActivity.this).execute(URL, "SEARCH");
                } else {
                    Toast.makeText(MainActivity.this, "Enter Search Key!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        favTrackList = sharedPreference.getFavorites(this);
        if (favTrackList != null) {
            adapter = new TrackListAdapter(this, R.layout.list_item, favTrackList,"MainActivity");
            adapter.notifyDataSetChanged();
            lvFav.setAdapter(adapter);
        }
        lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, TrackDetails.class);
                i.putExtra("SELECTED_TRACK", favTrackList.get(position));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSeachMusic = (EditText) findViewById(R.id.etSearchText);
        sharedPreference = new SharedPreference();
        lvFav = (ListView) findViewById(R.id.lvFavorites);
//        SharedPreferences preferences = getSharedPreferences("MyMusicPref", 0);
//        preferences.edit().remove("shared_pref_key").commit();

        trackList = new ArrayList<>();
        favTrackList = new ArrayList<>();
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL = "http://ws.audioscrobbler.com/2.0/?format=json&method=track.search&track=" + etSeachMusic.getText() + "&api_key=79e45983d0688a923a9bf72302a9a0df&limit=20";
                Log.d("demo", "onClick:" + URL);
                new SearchMusicAsyncTask(MainActivity.this).execute(URL, "SEARCH");
            }
        });
        favTrackList = sharedPreference.getFavorites(this);
        if (favTrackList != null) {
            adapter = new TrackListAdapter(this, R.layout.list_item, favTrackList,"MainActivity");
            adapter.notifyDataSetChanged();
            lvFav.setAdapter(adapter);
        }
        lvFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, TrackDetails.class);
                i.putExtra("SELECTED_TRACK", favTrackList.get(position));
                startActivity(i);
            }
        });

    }

    @Override
    public void setData(ArrayList<Track> trackList) {
        this.trackList = trackList;
        Intent i = new Intent(MainActivity.this,SearchResults.class);
        i.putExtra("TRACK_LIST",trackList);
        startActivity(i);
    }

    public boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
