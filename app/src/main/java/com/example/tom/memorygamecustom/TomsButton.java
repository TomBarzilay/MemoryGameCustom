package com.example.tom.memorygamecustom;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Tom on 13/10/2017.
 */

public class TomsButton extends android.support.v7.widget.AppCompatImageView {
    private int Type;
    public TomsButton(Context context) {
        super(context);
    }

    TomsButton(Context context,int width,int height){
        super(context);
    }

    public TomsButton setType(int type) {
        Type = type;
        return  this;
    }

    public int getType(){
        return this.Type;
    }
}
