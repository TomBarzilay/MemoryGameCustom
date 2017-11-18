package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
GridLayout main;//the group view that hold the buttons
    Drawable[] images; //an array that gets the data from the shared preference class
    TomsButton[][] gameCards; // an array of buttons that create the table
    Drawable mainPic;//a picture that will be shown before the user click a button and revels a
    TomsButton chosenCard;//a variable button will hold the reference of the first button that was clicked
    TomsButton chosenCard2;//a variable button will hold the reference of the second button that was clicked
    Drawable questionMark;
    int stateCheck;
   public static ProgressDialog r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionMark = getDrawable(R.drawable.questionmark);
        setContentView(R.layout.activity_game);
        main=(GridLayout) findViewById(R.id.mainGame);
        getLevel();

    }
private void createGame(int col,int row, int matches,int width,int height){
 prepareGame(col,row);
//setMainPic(col);
gameCards= new TomsButton[col][row];
    int[] pairsCheck = new int[matches];
    for (int x =0;x<gameCards.length;x++){
        for (int y =0;y<gameCards[x].length;y++){

            int index =new Random().nextInt(matches);
            while(pairsCheck[index]>=2) {
            /*    int oCount=0;
                for (int o : pairsCheck){
                     oCount=oCount+o;

                    if (oCount==matches*2)break;
                }*/
                index = new Random().nextInt(matches);
            }
            pairsCheck[index]++;
            main.addView(buildButton(index,row),width,height);
        }
    }

}
private void prepareGame(int col,int row){
    main.setColumnCount(col);
    main.setRowCount(row);
}
private TomsButton buildButton(int index,int col){
    TomsButton t = new TomsButton(this);
    t.setType(index);
    t.setBackground(questionMark);
    t.setScaleType(ImageView.ScaleType.CENTER_CROP);
    int size =main.getWidth()/col;
   // t.setMaxHeight(50);
    //t.setMaxWidth(50);
    t.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
           AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                        return null;
                }
                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    switch (stateCheck){
                        case 10://first click

                            break;
                        case 20://match was found
                            try {
                                matchFound();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 30://match not found
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            chosenCard.setBackground(questionMark);
                            chosenCard2.setBackground(questionMark);
                            chosenCard=null;
                            chosenCard2=null;
                            break;

                    }
                }
            };

        if (chosenCard==null){
            chosenCard= (TomsButton) view;
            chosenCard.setBackground(images[chosenCard.getType()]);
            stateCheck=10;
        }else {
            chosenCard2 = (TomsButton)view;
            chosenCard2.setBackground(images[chosenCard2.getType()]);
            chosenCard.setBackground(images[chosenCard.getType()]);

            if (checkIfSame((TomsButton) view,chosenCard)){
                stateCheck=20;
            }else stateCheck=30;
            asyncTask.execute();
        }
        }
    });
    return t;
}
//brings the int that will decide how the game will be build:easy,medium or hard.
private void getLevel(){
    int level = SharedPrefs.getPrefs(this).getInt(ChooseLevelActivity.LEVEL,0);
    int row =0;
    int col =0;
    int height =0;
    int width = 0;
    int matches =0;
    try {
        images = SharedPrefs.getImages(this);
    }catch (OutOfMemoryError outOfMemoryError){
        r = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        r.setTitle("saving the pictures");
        r.setMessage("loading...");
        r.setCancelable(true);
        r.show();

    }

    switch (level){
        case 6:
            row=4;
            col=3;
            matches=level;
            width=440;
            height=440;
            break;
        case 8:
            row=4;
            col=4;
            matches=level;
            width=317;
            height=300;
            break;
        case 12:
            row=6;
            col=4;
            matches=level;
            width=317;
            height=235;
            break;
        case 0:
            new AlertDialog.Builder(this).setMessage("the level is bad");
    }
    createGame(col,row,matches,width,height);
}
/*private void setMainPic(int col){
    Drawable drawable = getDrawable(R.drawable.questionmark);
    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
// Scale it to 50 x 50
    int size =main.getWidth()/col;
     mainPic = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

}*/
private boolean checkIfSame(TomsButton view1,TomsButton view2){
    if (view1.getType()==view2.getType()){ return true;} else return false;
}
private void matchFound() throws InterruptedException {
    Thread.sleep(300);
    chosenCard.setVisibility(View.GONE);
    chosenCard2.setVisibility(View.GONE);
    chosenCard=null;
    chosenCard2=null;
}
public static void imagesAreDone(Context context){
    try{SharedPrefs.getImages(context);
        if (r!=null)r.cancel();}
    catch (OutOfMemoryError e ){
    new AlertDialog.Builder(context).setMessage("youre fucked").create().show();

    }

}
}
