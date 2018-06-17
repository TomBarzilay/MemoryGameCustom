package com.example.tom.memorygamecustom.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tom.memorygamecustom.R;

public class ChooseImagesSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_images_source);
    }
    //the user chooses if to get images from his internal storage or from the web
    public void chooseImageSource(View view){
        if (view.getTag()!=null){startActivity(new Intent(this,SearchImagesActivity.class));}else{startActivity(new Intent(this,GalleryFoldersActivity.class));}

    }
}
