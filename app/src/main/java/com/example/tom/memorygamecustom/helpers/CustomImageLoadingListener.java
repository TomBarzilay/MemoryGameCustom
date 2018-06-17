package com.example.tom.memorygamecustom.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.tom.memorygamecustom.activities.GameActivity;
import com.example.tom.memorygamecustom.dataholders.ImageSelectionObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;

import java.util.ArrayList;

/**
 * Created by TomBarzilay on 25/05/2018.
 */

public class CustomImageLoadingListener implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener {
    private ImageLoader imageLoader;
    private Drawable drawable;
    private Context context;
    private int size;
    private Context context1;
    private ArrayList<Drawable> drawables;
    private ArrayList<ImageSelectionObject> pathsList;
    private String [] pathArray;



    public CustomImageLoadingListener(Context context, ArrayList<ImageSelectionObject> pathsList, int size){

            this.context=context;
            this.size = size;
            drawables= new ArrayList<Drawable>();
            this.pathsList =pathsList;
            imageLoader =ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        }
    public void execute(String url){
            imageLoader.loadImage(url,this);

    }


    @Override
    public void onLoadingStarted(String s, View view) {

    }

    @Override
    public void onLoadingFailed(String s, View view, FailReason failReason) {

    }

    @Override
    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            //creates a drawble from the loaded bitmap
            drawable = new BitmapDrawable(context.getResources(),bitmap);


        if (drawables.size()!=(size-1)){
                //size variable is the quantity of images needed for the game, easy - 6 medium - 8 hard -12.
                 drawables.add(drawable);
                int position = drawables.size();
                //uses recursion to fill the list in drawables
                execute(pathsList.get(position).getPath());

            }else{
                //after the arraylist is full change it to array and send the array to the SharedPrefs object
                drawables.add(drawable);
                Drawable [] drawables1 = new Drawable[size];
                drawables1 = drawables.toArray(drawables1);
                SharedPrefs.setImages(drawables1);
                Intent intent = new Intent(context,GameActivity.class);
                context.startActivity(intent);
            }
    }

    @Override
    public void onLoadingCancelled(String s, View view) {

    }
}
