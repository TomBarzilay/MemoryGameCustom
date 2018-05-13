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

import java.util.List;

/**
 * Created by TomBarzilay on 02/12/2017.
 */

public class SearchPhotosAdapter extends RecyclerView.Adapter<SearchPhotosAdapter.ViewHolder> {
   private List<Hit> dataSource;
     private Context context;
    private   boolean [] check;
    private String [] urls;

   public SearchPhotosAdapter(List<Hit> dataSource, Context context){
        this.dataSource=dataSource;
        this.context =context;

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
            check = new boolean[dataSource.size()];
        }
    }
    public SearchPhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchPhotosAdapter.ViewHolder holder, final int position) {
    final Hit hit = dataSource.get(position);
        Picasso.with(context).load(hit.getWebformatURL()).into(holder.imageView);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check[position]){
                    holder.checkBox.setChecked(false);
                    check [position]=false;
                }   else {

                    holder.checkBox.setChecked(true);
                    check[position]=true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public String saveImages() {
        int cnt = 0;
        int level = SharedPrefs.getPrefs(context).getInt(ChooseLevelActivity.LEVEL,0);
        String selectedImages="";
        for (int i=0;i<check.length;i++){
            if (check[i]){
                String url =dataSource.get(i).getWebformatURL();
                selectedImages=selectedImages+url+"<>";

            }
        }
        if (cnt==level) {return selectedImages;} else{ return null;}
    }

}
