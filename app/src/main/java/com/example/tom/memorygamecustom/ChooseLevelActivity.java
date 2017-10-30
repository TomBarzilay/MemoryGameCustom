package com.example.tom.memorygamecustom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseLevelActivity extends AppCompatActivity {
public static final String LEVEL ="level";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ChooseLevel(View v){
        int tag = Integer.parseInt(v.getTag().toString());
    startActivity(new Intent(this,ChoosePicActivity.class).putExtra(LEVEL,tag));
    finish();
    }
}
