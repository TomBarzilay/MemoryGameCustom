package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import java.util.HashSet;

/**
 * Created by Tom on 13/10/2017.
 */

public class SharedPrefs {
    private static final String PREFS = "prefs";
    private static final String IMAGES = "images";
    //private static final String LEVEL = "level";

    static public SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS, context.MODE_PRIVATE);
    }

    public static synchronized void setImages(Context context, String paths) {
        getPrefs(context).edit().putString(IMAGES, paths).apply();
    }

    public static synchronized Drawable[] getImages(Context context) {
        String imageSet = getPrefs(context).getString(IMAGES, null);
        if (imageSet != null) {
            String [] imagesPathsArray =imageSet.split("<>");
            Drawable[] images = new Drawable[imagesPathsArray.length];
            for (int i = 0; i < imagesPathsArray.length; i++) {
                images[i] = Drawable.createFromPath(imagesPathsArray[i]);
            }
            return images;
        } else {
            new AlertDialog.Builder(context).setMessage("something went wrong with the images").show();
            return null;
        }
    }

    /*public static void setLevel(Context context, int level) {
        getPrefs(context).edit().putInt(LEVEL, level).apply();
    }

    public static int getLevel(Context context) {
        int level = getPrefs(context).getInt(LEVEL, 0);
        if (level != 0) {
            return level;
        } else {
            new AlertDialog.Builder(context).setMessage("level implementaion went wrong").show();
        return 0;
        }

    }*/
}