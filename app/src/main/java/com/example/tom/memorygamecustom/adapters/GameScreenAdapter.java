package com.example.tom.memorygamecustom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tom.memorygamecustom.dataholders.GameCards;
import com.example.tom.memorygamecustom.helpers.GameRules;
import com.example.tom.memorygamecustom.R;

import java.util.Random;

/**
 * Created by TomBarzilay on 03/12/2017.
 */

public class GameScreenAdapter extends RecyclerView.Adapter {
    private int count;
    private Context context;
    private GameCards[] dataSource;
    private int height;
    private GameRules onClickListener;
    public GameScreenAdapter(Context context, int count, int calculatedHeight, GameRules ON){
    this.context = context;
    this.count=count;
    height = calculatedHeight;
    onClickListener =ON;
    dataSource = createButtons();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        GameCards gameCards;
        public ViewHolder(View itemView) {
            super(itemView);
            gameCards = (GameCards) itemView;

        }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GameCards itemView = new GameCards(context);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder V = (ViewHolder) viewHolder;
        V.gameCards.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height ));
        V.gameCards.setScaleType(ImageView.ScaleType.CENTER_CROP);
        V.gameCards.setOnClickListener(onClickListener);
        V.gameCards.setBackground(context.getDrawable(R.drawable.questionmark));
        V.gameCards.setType(dataSource[i].getType());

    }

    @Override
    public int getItemCount() {
        return count;
    }
private GameCards[] createButtons(){
    int pairs = count/2;
    int [] pairsCheck = new  int[pairs];
    GameCards[] buttons = new GameCards[count];
    for (int i=0;i<count;i++) {
        int index = new Random().nextInt(pairs);
        while (pairsCheck[index] >= 2) {

            index = new Random().nextInt(pairs);

        }
        pairsCheck[index]++;
        GameCards gameCards = new GameCards(context).setType(index);
        buttons[i] = gameCards;
    }
return buttons;
    }

}
