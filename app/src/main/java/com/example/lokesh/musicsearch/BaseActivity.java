package com.example.lokesh.musicsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.popup_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.Home:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                return true;
            case R.id.Quit:
                MainActivity.main_Quit = true;
                SearchResults.searchResult_quit = true;
                TrackDetails.trackDetails_Quit = true;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
