package com.example.lokesh.musicsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends BaseActivity {
    ListView lvSearchResult;
    ArrayList<Track> trackList;
    static boolean searchResult_quit = false;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(searchResult_quit){
            finish();;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Search Results");

        lvSearchResult = (ListView) findViewById(R.id.lvSearchResults);
        trackList = getIntent().getExtras().getParcelableArrayList("TRACK_LIST");

        TrackListAdapter adapter = new TrackListAdapter(this, R.layout.list_item, trackList,"SearchResults");
        adapter.notifyDataSetChanged();
        lvSearchResult.setAdapter(adapter);

        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", "" + position);
                Intent i = new Intent(SearchResults.this, TrackDetails.class);
                i.putExtra("SELECTED_TRACK", trackList.get(position));
                startActivity(i);
            }
        });
    }
}
