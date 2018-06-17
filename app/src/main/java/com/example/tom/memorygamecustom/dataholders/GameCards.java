package com.example.tom.memorygamecustom.dataholders;

import android.content.Context;

/**
 * Created by Tom on 13/10/2017.
 */

public class GameCards extends android.support.v7.widget.AppCompatImageView {
    private int type;
    public GameCards(Context context) {
        super(context);
    }

    GameCards(Context context, int width, int height){
        super(context);
    }

    public GameCards setType(int type) {
        this.type = type;
        return  this;
    }

    public int getType(){
        return this.type;
    }
}
