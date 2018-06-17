package com.example.tom.memorygamecustom.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tom.memorygamecustom.helpers.PermissionManager;
import com.example.tom.memorygamecustom.R;
import com.example.tom.memorygamecustom.helpers.SharedPrefs;

public class ChooseLevelActivity extends AppCompatActivity {
    public static final String LEVEL ="level";
    public static final int MEDIA_REQUEST = 102;
    //list of permissions needed
    private String [] permissions =new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //permission asking from the user
        PermissionManager.check(this,permissions, MEDIA_REQUEST);


    }

    @Override
        //if the user dosen't approve, he will be asked again
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        for (int i =0;i<grantResults.length;i++){

            if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                new AlertDialog.Builder(this).setMessage(R.string.permission_is_required).setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionManager.check(ChooseLevelActivity.this,permissions,MEDIA_REQUEST);
                    }
                }).setNegativeButton(getString(R.string.denial), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChooseLevelActivity.this.finish();
                    }
                }).create().show();
            }
        }
    }

    public void ChooseLevel(View v){
        /*the number of cards in the game will be decided by what the user choose
            easy - 12 cards
            medium - 16 cards
            hard - 24 cards
        */
        int tag = Integer.parseInt(v.getTag().toString());
        SharedPrefs.getPrefs(this).edit().putInt(LEVEL,tag).apply();
        startActivity(new Intent(this,ChooseImagesSourceActivity.class));
        finish();
    }
}
