package com.example.tom.memorygamecustom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by TomBarzilay on 03/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private int count;
    private Context context;
    private TomsButton [] dataSource;
    private int height;
    private GameRules onClickListener;
    MyAdapter(Context context, int count, int calculatedHeight, GameRules ON){
    this.context = context;
    this.count=count;
    height = calculatedHeight;
    onClickListener =ON;
    dataSource = createButtons();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TomsButton tomsButton;
        public ViewHolder(View itemView) {
            super(itemView);
            tomsButton= (TomsButton) itemView;

        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TomsButton itemView = new TomsButton(context);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder V = (ViewHolder) viewHolder;
        V.tomsButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height ));
        V.tomsButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        V.tomsButton.setOnClickListener(onClickListener);
        V.tomsButton.setBackground(context.getDrawable(R.drawable.questionmark));
        V.tomsButton.setType(dataSource[i].getType());

    }

    @Override
    public int getItemCount() {
        return count;
    }
private TomsButton [] createButtons(){
    int pairs = count/2;
    int [] pairsCheck = new  int[pairs];
    TomsButton [] buttons = new TomsButton[count];
    for (int i=0;i<count;i++) {
        int index = new Random().nextInt(pairs);
        while (pairsCheck[index] >= 2) {

            index = new Random().nextInt(pairs);

        }
        pairsCheck[index]++;
        TomsButton tomsButton = new TomsButton(context).setType(index);
        buttons[i] = tomsButton;
    }
return buttons;
    }

}
