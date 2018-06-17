package com.example.tom.memorygamecustom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.tom.memorygamecustom.adapters.ImageSelectionGridAdapter;
import com.example.tom.memorygamecustom.helpers.CustomImageLoadingListener;
import com.example.tom.memorygamecustom.dataholders.Hit;
import com.example.tom.memorygamecustom.dataholders.ImageSearchResult;
import com.example.tom.memorygamecustom.dataholders.ImageSelectionObject;
import com.example.tom.memorygamecustom.helpers.PixabayService;
import com.example.tom.memorygamecustom.R;
import com.example.tom.memorygamecustom.helpers.ServiceGenerator;
import com.example.tom.memorygamecustom.helpers.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//4
public class ImageSelectionGridActivity extends AppCompatActivity {
    private Call<ImageSearchResult> call;
    private Callback<ImageSearchResult> callback;
    private RecyclerView rv;
    private ImageSelectionGridAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String q;
    private ArrayList<ImageSelectionObject> imageSelectionObjects;
    private List<Hit> hitsList;
    private ArrayList<String> imgList;
    private int count;
    private int size;
    private boolean isFromNet;
/*this activity shows a preview of all the images in a grid view for the user to choose*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_grid);
        rv = findViewById(R.id.recycler);// the layout of the activity
        isFromNet = SharedPrefs.getPrefs(this).getBoolean(getString(R.string.isfromnet),false); // acording to this boolean the activity behavior will change
        count= SharedPrefs.getPrefs(this).getInt(ChooseLevelActivity.LEVEL,0);
        if (isFromNet){
            initFromInternet();
        }else {
            initFromLocal();
            size= imgList.size();
            changeTheListType();
            initAdapter();
        }

    }

    public void initFromInternet(){
        q =getIntent().getStringExtra(SearchImagesActivity.q); // q is the input of the user from the former activity, the search word.
        //1
        PixabayService pixabayService = ServiceGenerator.retrofit.create(PixabayService.class);// uses retrofit library to create a class for making the call.
        //2
        call = pixabayService.searchImages(ServiceGenerator.KEY, q); // sends a http request to get back an array of paths
        callback = new Callback<ImageSearchResult>() {
            @Override
            public void onResponse(Call<ImageSearchResult> call, final Response<ImageSearchResult> response) {
                ImageSearchResult data = response.body();
                 hitsList = data.getHits();
                size=hitsList.size();
                changeTheListType();// create an array of imageSelectionObject wich contains a String path and a boolean isChecked from the hitsList
                initAdapter();




            }

            @Override
            public void onFailure(Call<ImageSearchResult> call, Throwable t) {
                Log.d("#$$$$$$$$$$$$$$$$$4", t.getCause().getMessage());
                new AlertDialog.Builder(ImageSelectionGridActivity.this).setMessage("there is a problem with you internet connection").show();
            }
        };
        call.enqueue(callback);
    }
    private void initFromLocal(){
        // get an array list of paths from the former activity
        imgList = getIntent().getStringArrayListExtra(GalleryFoldersActivity.IMAGE_PATH);


    }
    private void initAdapter(){
        //creating the adapter
        layoutManager = new GridLayoutManager(ImageSelectionGridActivity.this,3);
        rv.setLayoutManager(layoutManager);
        adapter = new ImageSelectionGridAdapter(imageSelectionObjects , ImageSelectionGridActivity.this,isFromNet);
        rv.setAdapter(adapter);
    }

    public void saveImages(View view) {

        //gets a String of all the paths of the images chosen.
        AlertDialog badRequest = new AlertDialog.Builder(this).setTitle("Sorry").setMessage("you have to choose exactly "+count+" images").create();
        if (isFromNet){
            //execute a method to load images from urls to an array of drawables and save them in the shared preferences
           ArrayList<ImageSelectionObject> checkedSelectionObjects = adapter.getCheckedItems();
              Log.d("##$$$$$$$$$$$$$$$$$$$",""+checkedSelectionObjects.size());
            if (checkedSelectionObjects.size()==count) {
                CustomImageLoadingListener loader = new CustomImageLoadingListener(this, checkedSelectionObjects, count);
                loader.execute(imageSelectionObjects.get(0).getPath());
            }else {
                badRequest.show();
            }
        }else {
                String urls = adapter.saveImages();
                if (urls.split("<>").length==count) {
                    SharedPrefs.setArrayListPath(urls);
                    SharedPrefs.setImages(this, urls);
                    startActivity(new Intent(this, GameActivity.class));
                }else {
                    badRequest.show();
                }
                }







        }

    private ArrayList<ImageSelectionObject> changeTheListType(){
        imageSelectionObjects = new ArrayList<ImageSelectionObject>();

                for (int i=0;i<size;i++){
                    if (isFromNet) {
                    imageSelectionObjects.add(new ImageSelectionObject(hitsList.get(i).getPreviewURL()));
            }else{
                        imageSelectionObjects.add(new ImageSelectionObject(imgList.get(i)));
                    }
            }
        return imageSelectionObjects;
    }
}