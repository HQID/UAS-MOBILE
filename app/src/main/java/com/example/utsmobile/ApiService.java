package com.example.utsmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("recipes/random")
    Call<ApiResponse> getRandomRecipes(
            @Query("number") int number,
            @Query("include-tags") String tags,
            @Query("apiKey") String apiKey
    );
}

