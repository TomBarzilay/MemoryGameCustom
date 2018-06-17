package com.example.tom.memorygamecustom.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tom.memorygamecustom.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TomBarzilay on 17/06/2018.
 */

public class FoldersAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String [] folderList;
    private Context context;
    public FoldersAdapter(HashMap<String, ArrayList<String>> map,Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        folderList= new String[map.keySet().size()];
        folderList = map.keySet().toArray(folderList); //gets an array of strings from the list of lists map.
        this.context=context;


    }


    public int getCount() {
        return folderList.length;
    }

    public String getItem(int position) {
        return (String) folderList[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final FoldersAdapter.ViewHolder holder;
        if (convertView==null){
            holder= new FoldersAdapter.ViewHolder();
            convertView=mInflater.inflate(R.layout.folder_gallery_item,null);
            holder.textView = (TextView)convertView.findViewById(R.id.textOne);
            holder.imageView = (ImageView)convertView.findViewById(R.id.pic);
            convertView.setTag(holder);
        }else {
            holder = (FoldersAdapter.ViewHolder) convertView.getTag();
        }

        holder.textView.setText(this.getItem(position));
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}


