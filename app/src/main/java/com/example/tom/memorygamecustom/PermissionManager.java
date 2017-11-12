package com.example.tom.memorygamecustom;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

public class PermissionManager {
    //checks for needed permission - and request if not granted yet
    public static void check(Activity activity, String [] permissions, int requestCode){
           for (int i =0; i<permissions.length;i++){
               if (ActivityCompat.checkSelfPermission(activity,permissions[i]) != PackageManager.PERMISSION_GRANTED)

                           ActivityCompat.requestPermissions(activity,new String[]{permissions[i]} ,requestCode);
           }


    }
}
