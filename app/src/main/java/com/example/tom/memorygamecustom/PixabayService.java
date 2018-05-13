package com.example.tom.memorygamecustom;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TomBarzilay on 30/11/2017.
 */
//1
public interface PixabayService {
    @GET("api/")Call<ImageSearchResault> searchImages(@Query("key") String key, @Query("q") String q);
}
