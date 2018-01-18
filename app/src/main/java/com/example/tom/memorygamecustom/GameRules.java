package com.example.tom.memorygamecustom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.io.IOException;

/**
 * Created by TomBarzilay on 23/12/2017.
 */

public class GameRules implements View.OnClickListener {

    private TomsButton chosenCard;//a variable button will hold the reference of the first button that was clicked
    private TomsButton chosenCard2;//a variable button will hold the reference of the second button that was clicked
    private Drawable[] images;
    private int stateCheck;
    private Drawable questionMark;
    private Context context;
    private int halfCount;
    private int ifWinCount;
    MediaPlayer mnf;
    MediaPlayer mf;
    GameRules(Context context, Drawable [] images, Drawable questionMark,int halfCount){
        this.context=context;
        this.images = images;
         this.halfCount = halfCount;
        this.questionMark=questionMark;

        mnf = MediaPlayer.create(context,R.raw.match_not_found);
        mf=MediaPlayer.create(context,R.raw.oh_yeah);

    }
    @Override
    public void onClick(final View view) {

        if (chosenCard == null) {
            chosenCard = (TomsButton) view;
            chosenCard.setBackground(images[chosenCard.getType()]);
            stateCheck = 10;
        } else {
            chosenCard2 = (TomsButton) view;
            chosenCard2.setBackground(images[chosenCard2.getType()]);
            if (checkIfSame((TomsButton) view, chosenCard)) {
                stateCheck = 20;
            } else{ stateCheck = 30;
        }
        }
        switch (stateCheck) {
            case 10://first click

                break;
            case 20://match was found

                matchFound();


                break;
            case 30://match not found
                try {
                    matchNotFound();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;

        }
        //}
        //};


    };

private boolean checkIfSame(TomsButton view1,TomsButton view2){
        if (view1.getType()==view2.getType()&&view1!=view2){ return true;} else return false;
        }
private void matchFound() {
        mf.start();
        if (ifWinCount<halfCount-1){
            ifWinCount++;
        }else{
            gameFinished();
        }

        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                chosenCard.setVisibility(View.GONE);
                chosenCard2.setVisibility(View.GONE);
                chosenCard=null;
                chosenCard2=null;

            }
        });
        }

    private void gameFinished() {
    new AlertDialog.Builder(context).setMessage("wellDone").show();
    }

    private synchronized void matchNotFound() throws InterruptedException {

            mnf.start();
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    chosenCard.setBackground(questionMark);
                    chosenCard2.setBackground(questionMark);
                    chosenCard=null;
                    chosenCard2=null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void setImages(Drawable[] images) {
        this.images = images;
    }
    }


