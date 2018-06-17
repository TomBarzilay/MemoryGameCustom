package com.example.tom.memorygamecustom.helpers;

import com.example.tom.memorygamecustom.dataholders.ImageSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TomBarzilay on 30/11/2017.
 */
//1
public interface PixabayService {
    @GET("api/")Call<ImageSearchResult> searchImages(@Query("key") String key, @Query("q") String q);
}
