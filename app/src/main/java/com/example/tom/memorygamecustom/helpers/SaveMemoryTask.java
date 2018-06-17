package com.example.tom.memorygamecustom.helpers;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.example.tom.memorygamecustom.activities.GameActivity;

import java.util.ArrayList;

/**
 * Created by TomBarzilay on 28/05/2018.
 */

public class SaveMemoryTask extends AsyncTask<ArrayList<String>,Drawable[],Drawable[]>{
    private GameActivity context;
       public SaveMemoryTask(GameActivity context){
           this.context=context;
       }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected Drawable[] doInBackground(ArrayList<String>... arrayList) {
        BitmapFactory.Options opp = new BitmapFactory.Options();
        opp.inSampleSize=4;
        Drawable [] images = new Drawable[arrayList[0].size()];

        for (int i = 0; i < arrayList[0].size(); i++) {
            Bitmap original = BitmapFactory.decodeFile(arrayList[0].get(i),opp);
            Drawable compressed = new BitmapDrawable(context.getResources(),original);
            images[i] = compressed;
        }
        return images;

    }

    @Override
    protected void onPostExecute(Drawable[] drawable) {
        super.onPostExecute(drawable);
        new AlertDialog.Builder(context).setMessage(drawable[0].toString());
        context.pushTheDrawables(drawable);
    }

}
