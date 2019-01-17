package com.example.smorosou.runninggame.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//import com.example.smorosou.runninggame.R;
import com.example.smorosou.runninggame.classes.GameSurface;
import com.example.smorosou.runninggame.classes.Utils;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // set no title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setContentView(new GameSurface(this));
    }

    public Context getContext() {
        return this;
    }


    /**
     * About bar
     * @param item
     */
    @SuppressWarnings ("UnusedParameters")
    public void showAbout (MenuItem item)
    {
        Utils.showInfoDialog (GameActivity.this, R.string.about_dialog_title,
                R.string.about_dialog_banner);
    }

}
