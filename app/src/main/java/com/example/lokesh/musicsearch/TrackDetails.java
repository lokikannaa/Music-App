package com.example.lokesh.musicsearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackDetails extends BaseActivity implements SearchMusicAsyncTask.IQuestionData {
    ImageView ivLargeImage;
    TextView tvName;
    TextView tvArtist;
    TextView tvURL;
    ListView lvSimilarTracks;
    Track track;
    ArrayList<Track> similarTrackList;
    static boolean trackDetails_Quit = false;

    @Override
    protected void onResume() {
        super.onResume();
        if(trackDetails_Quit){
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        setTitle("Track Details");


        ivLargeImage = (ImageView) findViewById(R.id.ivLargeImage1);
        tvName = (TextView) findViewById(R.id.tvTrackName1);
        tvArtist = (TextView) findViewById(R.id.tvArtist1);
        tvURL = (TextView) findViewById(R.id.tvURL1);
        lvSimilarTracks = (ListView) findViewById(R.id.lvSimilarTracks);

        track = (Track) getIntent().getExtras().get("SELECTED_TRACK");
        try {
            new SearchMusicAsyncTask(TrackDetails.this).execute(getEncodedParams(track.getArtist(), track.getName()), "GET_SIMILAR");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        lvSimilarTracks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TrackDetails.this, TrackDetails.class);
                i.putExtra("SELECTED_TRACK", similarTrackList.get(position));
                startActivity(i);
            }
        });


        Picasso.with(this)
                .load(track.getLargeImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(ivLargeImage);

        tvName.setText(track.getName().toString());
        tvArtist.setText(track.getArtist().toString());
        tvURL.setText(track.getMainURL().toString());

        tvURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvURL.getText().toString()));
                startActivity(browserIntent);
            }
        });
    }

    public String getEncodedParams(String artist, String track) throws UnsupportedEncodingException {
        String URL = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&format=json&limit=10&api_key=79e45983d0688a923a9bf72302a9a0df&artist=" + artist + "&track=" + track;
        Log.d("demo", "getEncodedParams: " + URL);
        return URL;
    }

    @Override
    public void setData(ArrayList<Track> trackList) {
        this.similarTrackList = trackList;
        TrackListAdapter adapter = new TrackListAdapter(TrackDetails.this, R.layout.list_item, similarTrackList,"TrackDetails");
        lvSimilarTracks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

