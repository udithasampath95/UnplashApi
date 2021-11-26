package com.example.azbowtest.network;

import com.example.azbowtest.constant.ApplicationConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;

    private RetrofitInstance(){

    }

    public static Retrofit getRetroClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomeInterceptor(ApplicationConstant.ACCESS_KEY))
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
