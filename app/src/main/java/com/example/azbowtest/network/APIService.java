package com.example.azbowtest.network;

import com.example.azbowtest.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") Integer page,
                                @Query("per_page") Integer perPage,
                                @Query("order_by") String orderBy);
}
