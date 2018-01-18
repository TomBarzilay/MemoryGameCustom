package com.example.tom.memorygamecustom;

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

public class GameActivity extends AppCompatActivity {
    private Drawable[] images;
    private int count;
    private TomsButton[] dataSource;
    private TomsButton chosenCard;
    private TomsButton chosenCard2;
    private int height;
    RelativeLayout progressBar;
    private GameRules gameRules;
    RecyclerView gridView;
    private Drawable questionMark;
    private int halfCount;
    private boolean alreadyStarted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionMark = getDrawable(R.drawable.questionmark);
        setContentView(R.layout.game_activity);
        gridView = (RecyclerView) findViewById(R.id.grid);
        try {
            getLevel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // initializeButtons();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (alreadyStarted==false){init();
        alreadyStarted=true;}


    }

    public void init() {

        StaggeredGridLayoutManager
                layoutManager = new StaggeredGridLayoutManager(getColumnsNumber(), StaggeredGridLayoutManager.VERTICAL);
        gridView.setLayoutManager(layoutManager);
        height = gridView.getHeight();

        try {
            images = SharedPrefs.getImages(this);
        } catch (OutOfMemoryError outOfMemoryError) {
            startLoadingBar();
            saveTheMemory(images);


        }

        gameRules = new GameRules(this,images,questionMark,halfCount);
        if (images!=null){gridView.setAdapter(new MyAdapter(GameActivity.this, count, height / getRowNumber(), gameRules));}

    }

    private void startLoadingBar() {
    RelativeLayout rel = findViewById(R.id.bigger);
         progressBar = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.compress_progress_bar,null,false);
        rel.addView(progressBar, RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
    }


    //brings the int that will decide how the game will be build:easy,medium or hard.
    private void getLevel() throws InterruptedException {
        int level = SharedPrefs.getPrefs(this).getInt(ChooseLevelActivity.LEVEL, 0);
        halfCount=level;
        count = level*2;
    }

    private boolean checkIfSame(TomsButton view1, TomsButton view2) {
        if (view1.getType() == view2.getType() && view1 != view2) {
            return true;
        } else return false;
    }

    private void matchFound() {


        chosenCard.setVisibility(View.GONE);
        chosenCard2.setVisibility(View.GONE);
        chosenCard = null;
        chosenCard2 = null;
    }



    private synchronized void matchNotFound() throws InterruptedException {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    chosenCard.setBackground(questionMark);
                    chosenCard2.setBackground(questionMark);
                    chosenCard = null;
                    chosenCard2 = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


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
    private void saveTheMemory(Drawable[] arrayOfDrawables) {

        new AsyncTask<Void, Drawable [], Drawable[]>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Drawable[] doInBackground(Void... voids) {
                Drawable[] compressedImages = SharedPrefs.CompressPhotos(GameActivity.this);
                return compressedImages;
            }

            @Override
            protected void onPostExecute(Drawable[] drawable) {
                super.onPostExecute(drawable);
                pushTheDrawables(drawable);
            }
        }.execute();
    }
    private void pushTheDrawables(Drawable[] drawables) {
        gameRules.setImages(drawables);
        progressBar.setVisibility(View.GONE);
        gridView.setAdapter(new MyAdapter(GameActivity.this, count, height / getRowNumber(), gameRules));
    }

}

