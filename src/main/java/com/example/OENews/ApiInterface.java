package com.example.OENews;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("auth/register")
    @FormUrlEncoded
    Call<User> register(@Field("email") String email, @Field("password") String password);



    @GET("top-headlines")
    Call<com.example.OENews.Model.Headlines> getHeadlines(
        @Query("country") String country,
                @Query("apiKey") String apiKey
    );
}
