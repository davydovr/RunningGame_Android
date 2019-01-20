package com.example.smorosou.runninggame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.smorosou.runninggame.classes.GameSurface;
import com.example.smorosou.runninggame.classes.Utils;
import com.google.gson.Gson;

import static java.lang.Boolean.getBoolean;

public class MainActivity extends AppCompatActivity {

    private ImageView mBackground;
    public static boolean mPrefUseWhiteBackground;
    private GameSurface mGameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupFAB();
        setupContent();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
    }

    private void setupContent()
    {
        mBackground = findViewById(R.id.image_background);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId ()) {

            case R.id.action_settings:
                return true;

            case R.id.action_toggle_background_color_white: {
                toggleMenuItem(item);
                mPrefUseWhiteBackground = item.isChecked();
                return true;
            }
            default:
                // If the user clicked on some unknown menu item, then the super... has to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean getPrefBackgroundColor() {
        return mPrefUseWhiteBackground;
    }

    /**
     * About bar
     * @param item
     */
    @SuppressWarnings ("UnusedParameters")
    public void showAbout (MenuItem item)
    {
        Utils.showInfoDialog (MainActivity.this, R.string.about_dialog_title,
                R.string.about_dialog_banner);
    }

    public boolean onPrepareOptionsMenu (Menu menu)
    {
        menu.findItem (R.id.action_toggle_background_color_white).setChecked (mPrefUseWhiteBackground);
        return super.onPrepareOptionsMenu (menu);
    }



    private void toggleMenuItem (MenuItem item)
    {
        item.setChecked (!item.isChecked ());
    }

    @Override
    protected void onSaveInstanceState (Bundle outstate) {

        super.onSaveInstanceState(outstate);
        outstate.putString("key", String.valueOf(mPrefUseWhiteBackground));     //returns "true" or "false"

    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        mGameSurface = getPrefBackgroundColorFromGson(savedInstanceState.getString("key"));

    }

    private GameSurface getPrefBackgroundColorFromGson(String key) {
        Gson gson = new Gson ();
        return gson.fromJson (key, GameSurface.class);
    }
}











