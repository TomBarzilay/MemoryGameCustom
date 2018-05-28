package com.example.tom.memorygamecustom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by TomBarzilay on 02/12/2017.
 */

public class ImageSelectionGridAdapter extends RecyclerView.Adapter<ImageSelectionGridAdapter.ViewHolder> {
   private ArrayList<ImageSelectionObject> dataSource;
     private Context context;
     boolean isFromNet;

   public ImageSelectionGridAdapter(ArrayList<ImageSelectionObject> dataSource, Context context, boolean isFromNet){
        this.dataSource=dataSource; //the data source is an arrayList of objects that contains a string path and a boolean
        this.context=context;
        this.isFromNet=isFromNet;
   }

    public String saveImages() {
        String paths="";
        for (int i=0;i<dataSource.size();i++){
            if (dataSource.get(i).getIsChecked())
        paths=paths+dataSource.get(i).getPath()+"<>";
        //create a String of all the paths
        }
        return paths;
   }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout item;
        private ImageView  imageView;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            item= (FrameLayout) itemView;
            imageView=itemView.findViewById(R.id.image);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
    public ImageSelectionGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageSelectionGridAdapter.ViewHolder holder, final int position) {
    final ImageSelectionObject iso = dataSource.get(position);
    if (isFromNet){
        Picasso.with(context).load( iso.getPath()).resize(200,200).into(holder.imageView);
    }else
        //if check box is checked uncheck and the other way around
    {Picasso.with(context).load( new File(iso.getPath())).resize(200,200).into(holder.imageView);}
        holder.checkBox.setChecked(iso.getIsChecked());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked=iso.getIsChecked();
                holder.checkBox.setChecked(!isChecked);
                iso.setChecked(!isChecked);
            }
        });
    }
    public ArrayList<ImageSelectionObject> getCheckedItems(){
       ArrayList<ImageSelectionObject> checkedObjects = new ArrayList<ImageSelectionObject>();
       for (ImageSelectionObject i : dataSource){
           if (i.getIsChecked()){
               checkedObjects.add(i);
           }
       }
    return checkedObjects;
   }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    }
