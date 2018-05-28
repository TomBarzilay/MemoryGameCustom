package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private Drawable[] images; // an array of drawables that contains the images for the game;
    private int count;// the number of cards in the game
    private int height;// the max height of the user device screen
    private RelativeLayout progressBar;
    private GameRules gameRules;// an object responsible of the behavior of the buttons(on click listener and more)
    private RecyclerView gridView; // the main layout
    private Drawable questionMark;// the default image showed when nothing is clicked
    private int halfCount;
    private boolean alreadyStarted=false;
    private boolean isFromNet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionMark = getDrawable(R.drawable.questionmark);
        setContentView(R.layout.game_activity);
        gridView = (RecyclerView) findViewById(R.id.grid);
        isFromNet = SharedPrefs.getPrefs(this).getBoolean(getString(R.string.isfromnet),false);
        try {
            getLevel(); //gets what the user chose and build the quantity of game cards according to it.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // initializeButtons();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (alreadyStarted==false){init();//starts the initialization after the activity was drawn, so the app can get the max height
        alreadyStarted=true;}


    }

    public void init() {

        StaggeredGridLayoutManager
                layoutManager = new StaggeredGridLayoutManager(getColumnsNumber(), StaggeredGridLayoutManager.VERTICAL);
        gridView.setLayoutManager(layoutManager);
        height = gridView.getHeight();

        try {
             if (isFromNet){
                 images = SharedPrefs.getImages();
             }else {
                 images = SharedPrefs.getImages(this);
             }
             } catch (OutOfMemoryError outOfMemoryError) {
            startLoadingBar();
            saveTheMemory(images);// a method that compress the images so there wont be a out of memroy error


        }

        gameRules = new GameRules(this,images,questionMark,halfCount);
        if (images!=null){gridView.setAdapter(new MyAdapter(GameActivity.this, count, height / getRowNumber(), gameRules));}

    }

    private void startLoadingBar() {
    RelativeLayout rel = (RelativeLayout) findViewById(R.id.bigger);
         progressBar = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.compress_progress_bar,null,false);
        rel.addView(progressBar, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
    }


    //brings the int that will decide how the game will be build:easy,medium or hard.
    private void getLevel() throws InterruptedException {
        int level = SharedPrefs.getPrefs(this).getInt(ChooseLevelActivity.LEVEL, 0);
        halfCount=level;
        count = level*2;
    }






    private int getColumnsNumber() {
        switch (count) {
            case 12:
                return 3;
            case 16:
                return 4;
            case 24:
                return 4;
        }
        return 0;
    }

    private int getRowNumber() {
        switch (count) {
            case 12:
                return 4;
            case 16:
                return 4;
            case 24:
                return 6;
        }
        return 0;
    }
    private void saveTheMemory( Drawable[] arrayOfDrawables) {
        ArrayList<String> arrayListofPaths = SharedPrefs.getListOfPaths();
        SaveMemoryTask saveMemoryTask = new SaveMemoryTask(this);
        saveMemoryTask.execute(arrayListofPaths);

    }
    public void pushTheDrawables(Drawable[] drawables) {
        gameRules.setImages(drawables);
        progressBar.setVisibility(View.GONE);
        gridView.setAdapter(new MyAdapter(GameActivity.this, count, height / getRowNumber(), gameRules));
    }

}

