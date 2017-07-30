package ru.overtired.yamblz2017.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AutoCompleteFetcher {
    @GET("/aq")
    Call<AutoComplete> getCitiesList(@Query("query") String query, @Query("c") String country);
}
