  package com.example.tom.memorygamecustom.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.tom.memorygamecustom.dataholders.GameCards;
import com.example.tom.memorygamecustom.R;

  /**
 * Created by TomBarzilay on 23/12/2017.
 */

public class GameRules implements View.OnClickListener {

    private GameCards chosenCard;//a variable button will hold the reference of the first button that was clicked
    private GameCards chosenCard2;//a variable button will hold the reference of the second button that was clicked
    private Drawable[] images;
    private int stateCheck;
    private Drawable questionMark;
    private Context context;
    private int halfCount;
    private int ifWinCount;
    MediaPlayer mnf;
    MediaPlayer mf;
     public GameRules(Context context, Drawable [] images, Drawable questionMark,int halfCount){
        this.context=context;
        this.images = images;
         this.halfCount = halfCount;
        this.questionMark=questionMark;

        mnf = MediaPlayer.create(context, R.raw.match_not_found);
        mf=MediaPlayer.create(context,R.raw.oh_yeah);

    }
    @Override
    public void onClick(final View view) {

        if (chosenCard == null) {
            chosenCard = (GameCards) view;
            chosenCard.setBackground(images[chosenCard.getType()]);
            stateCheck = 10;
        } else {
            chosenCard2 = (GameCards) view;
            chosenCard2.setBackground(images[chosenCard2.getType()]);
            if (checkIfSame((GameCards) view, chosenCard)) {
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

private boolean checkIfSame(GameCards view1, GameCards view2){
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
                    Thread.sleep(800);
                    Drawable deadPool = context.getDrawable(R.drawable.deadpool);
                    chosenCard.setBackground(deadPool);
                    chosenCard2.setBackground(deadPool);
                    chosenCard=null;
                    chosenCard2=null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (OutOfMemoryError error){

                }

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
                    Thread.sleep(600);
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


