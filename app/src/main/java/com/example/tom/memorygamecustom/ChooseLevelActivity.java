package com.example.tom.memorygamecustom;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseLevelActivity extends AppCompatActivity {
public static final String LEVEL ="level";
public static final int MEDIA_REQUEST = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.check(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MEDIA_REQUEST);
    }
    public void ChooseLevel(View v){
        int tag = Integer.parseInt(v.getTag().toString());
        SharedPrefs.getPrefs(this).edit().putInt(LEVEL,tag).apply();
    startActivity(new Intent(this,GalleryFoldersActivity.class));
    finish();
    }
}
