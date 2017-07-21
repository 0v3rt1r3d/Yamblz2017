package ru.overtired.yamblz2017.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by overtired on 15.07.17.
 */

public class ResponseProcesser {
    private static final String API_WEATHER = "7e79982c03e614a8";

    @Nullable
    public static Weather requestWeather(@NonNull String lang, @NonNull String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.wunderground.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        WeatherFetcher fetcher = retrofit.create(WeatherFetcher.class);

        String jsonResponse;
        try {
             jsonResponse = fetcher.getWheather(API_WEATHER, lang, city)
                    .execute()
                    .body();
        }catch (IOException e){
            return null;
        }

        JsonParser parser = new JsonParser();

        JsonElement mainPart = parser.parse(jsonResponse)
                .getAsJsonObject()
                .get("current_observation");

        Weather weather = new Gson().fromJson(mainPart, Weather.class);

        weather.city = mainPart.getAsJsonObject()
                .get("display_location")
                .getAsJsonObject()
                .get("city")
                .getAsString();

        String dateText = mainPart.getAsJsonObject()
                .get("local_time_rfc822")
                .getAsString();

        try {
            weather.date = new SimpleDateFormat(Weather.DATE_FORMAT, Locale.ENGLISH)
                    .parse(dateText);
        }catch (ParseException e){
            return null;
        }

        return weather;
    }
}
