package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Tom on 13/10/2017.
 */

public class SharedPrefs {
    private static final String PREFS = "prefs";
    public static final String IMAGES = "images";
     static Drawable[] images;
    private static ArrayList<String> listOfPaths;

    static public SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS, context.MODE_PRIVATE);
    }

    public static synchronized void setImages(Context context, String paths) {
        getPrefs(context).edit().putString(IMAGES, paths).apply();
    }

    public static void setImages(Drawable[] images1) {
       SharedPrefs.images = images1;
    }
    public static synchronized Drawable[] getImages(){
        return images;
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

    public static void setArrayListPath(String strings){
          String [] pathsArray=  strings.split("<>");
          listOfPaths = new ArrayList<>();
          for (int i =0;i<pathsArray.length;i++){
              listOfPaths.add(pathsArray[i]);
          }

    }

    public static ArrayList<String> getListOfPaths() {
        return listOfPaths;
    }

    public static Drawable[]CompressPhotos(Context context) {
    String imageSet = getPrefs(context).getString(IMAGES, null);
    if (imageSet != null) {
        String[] imagesPathsArray = imageSet.split("<>");
        Drawable[] images = new Drawable[imagesPathsArray.length];
        BitmapFactory.Options opp = new BitmapFactory.Options();
        opp.inSampleSize=4;
        for (int i = 0; i < imagesPathsArray.length; i++) {
            Bitmap original = BitmapFactory.decodeFile(imagesPathsArray[i],opp);
            Drawable compressed = new BitmapDrawable(context.getResources(),original);
            images[i] = compressed;
        }
        return images;
    } else{new AlertDialog.Builder(context).setMessage("the string array is not valid").show();
    return  null;}
}
}