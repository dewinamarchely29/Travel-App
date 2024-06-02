package com.example.wenewsapp.utils;

import com.example.wenewsapp.utils.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("https://content.guardianapis.com/search")
    Call<NewsResponse> getNews(
            @Query("api-key") String apiKey,
            @Query("order-by") String orderBy,
            @Query("page-size") String pageSize,
            @Query("from-date") String fromDate,
            @Query("show-fields") String showFields,
            @Query("show-tags") String showTags
    );
}