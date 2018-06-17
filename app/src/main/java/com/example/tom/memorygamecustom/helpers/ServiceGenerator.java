package com.example.tom.memorygamecustom.helpers;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TomBarzilay on 30/11/2017.
 */
//2
public class ServiceGenerator {
    public final static String BASE_URL="https://pixabay.com/";
    public final static String KEY ="7208154-8c9d64cc94504e37a6d4980aa";
    public static Retrofit.Builder retroFitBuilder = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static  Retrofit retrofit = retroFitBuilder.client(httpClient.build()).build();

}
