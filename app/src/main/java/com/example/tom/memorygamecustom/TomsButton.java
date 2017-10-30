package com.example.tom.memorygamecustom;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Tom on 13/10/2017.
 */

public class TomsButton extends android.support.v7.widget.AppCompatImageView {
   int Type;
    public TomsButton(Context context) {
        super(context);
    }

    public void setType(int type) {
        Type = type;
    }

    public int getType(){
        return this.Type;
    }
}
