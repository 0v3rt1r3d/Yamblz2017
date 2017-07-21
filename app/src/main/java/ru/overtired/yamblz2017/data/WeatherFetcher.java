package ru.overtired.yamblz2017.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by overtired on 14.07.17.
 */

public interface WeatherFetcher {

    @GET("{key}/conditions/lang:{lang}/q/RU/{city}.json")
    Call<String> getWheather(@Path("key") String key, @Path("lang") String lang ,@Path("city") String city);
}
