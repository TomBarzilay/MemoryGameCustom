package com.example.tom.memorygamecustom;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseLevelActivity extends AppCompatActivity {
public static final String LEVEL ="level";
public static final int MEDIA_REQUEST = 102;
private String [] permissions =new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionManager.check(this,permissions, MEDIA_REQUEST);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        for (int i =0;i<grantResults.length;i++){
            if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                new AlertDialog.Builder(this).setMessage("sorry we must get permission to access your gallery if you want a custom game made of your photos").setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionManager.check(ChooseLevelActivity.this,permissions,MEDIA_REQUEST);
                    }
                }).setNegativeButton("i dont allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChooseLevelActivity.this.finish();
                    }
                }).create().show();
            }
        }
    }

    public void ChooseLevel(View v){
        int tag = Integer.parseInt(v.getTag().toString());
        SharedPrefs.getPrefs(this).edit().putInt(LEVEL,tag).apply();
    startActivity(new Intent(this,GalleryFoldersActivity.class));
    finish();
    }
}
