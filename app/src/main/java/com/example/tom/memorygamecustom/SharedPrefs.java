package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;

/**
 * Created by Tom on 13/10/2017.
 */

public class SharedPrefs {
    private static final String PREFS = "prefs";
    private static final String IMAGES = "images";


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
/*    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }*/
public static Drawable[]CompressPhotos(Context context) {
    String imageSet = getPrefs(context).getString(IMAGES, null);
    if (imageSet != null) {
        String[] imagesPathsArray = imageSet.split("<>");
        Drawable[] images = new Drawable[imagesPathsArray.length];
        BitmapFactory.Options opp = new BitmapFactory.Options();
        opp.inSampleSize=4;
        for (int i = 0; i < imagesPathsArray.length; i++) {
            Bitmap original = BitmapFactory.decodeFile(imagesPathsArray[i],opp);
            Drawable compressed = new BitmapDrawable(original);
            images[i] = compressed;
        }
        return images;
    } else{new AlertDialog.Builder(context).setMessage("the string array is not valid").show();
    return  null;}
}
}