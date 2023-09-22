package com.example.communityapplication;

import com.example.communityapplication.WebService.Endpoints;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
    private static final String url = "http://192.168.0.162:8080/";
    public static Retrofit getRetrofitInstance(){
        if (retrofit==null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set the connection timeout
                    .readTimeout(30, TimeUnit.SECONDS)    // Set the read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)   // Set the write timeout
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Endpoints getEndpoints(){
        return getRetrofitInstance().create(Endpoints.class);
    }
}
