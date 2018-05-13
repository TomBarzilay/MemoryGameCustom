package com.example.tom.memorygamecustom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//4
public class WebImagesGridActivity extends AppCompatActivity {
    private Call<ImageSearchResault> call;
    private Callback<ImageSearchResault> callback;
    private RecyclerView rv;
    private SearchPhotosAdapter adapter;
    private   RecyclerView.LayoutManager layoutManager;
    private   String q;
    private int level;
    private String [] urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_grid);
        rv = findViewById(R.id.recycler);
        q =getIntent().getStringExtra(SearchImagesActivity.q);
        //1
        PixabayService pixabayService = ServiceGenerator.retrofit.create(PixabayService.class);
        //2
        call = pixabayService.searchImages(ServiceGenerator.KEY, q);
        callback = new Callback<ImageSearchResault>() {
            @Override
            public void onResponse(Call<ImageSearchResault> call, final Response<ImageSearchResault> response) {
                ImageSearchResault data = response.body();
                layoutManager = new GridLayoutManager(WebImagesGridActivity.this,3);
                rv.setLayoutManager(layoutManager);
                adapter = new SearchPhotosAdapter(data.getHits(), WebImagesGridActivity.this);
                rv.setAdapter(adapter);






            }

            @Override
            public void onFailure(Call<ImageSearchResault> call, Throwable t) {
             new AlertDialog.Builder(WebImagesGridActivity.this).setMessage("shit koosemec").show();
            }
        };
        call.enqueue(callback);
    }

    public void saveImages(View view) {
        String urls = adapter.saveImages();
        level=SharedPrefs.getPrefs(this).getInt(ChooseLevelActivity.LEVEL,0);
        if (urls!=null){SharedPrefs.setImages(this,urls);}else{new AlertDialog.Builder(this).setMessage("you have to choose exactly "+level+" images");}
    startActivity(new Intent(this,GameActivity.class));
    }
}