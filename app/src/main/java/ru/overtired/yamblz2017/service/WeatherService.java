package ru.overtired.yamblz2017.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import ru.overtired.yamblz2017.App;
import ru.overtired.yamblz2017.data.ResponseProcesser;
import ru.overtired.yamblz2017.data.Weather;
import ru.overtired.yamblz2017.data.database.Dao;

/**
 * Created by overtired on 16.07.17.
 */

public class WeatherService extends IntentService {
    public static final String SERVICE_NAME = "ru.overtired.yamblz2017.weather_service";

    public static final String ACTION_UPDATE_WEATHER = "ru.overtired.yamblz2017.weather.update_weather";

    public WeatherService(){
        super(SERVICE_NAME);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, WeatherService.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        App app = (App)getApplicationContext();
        try {
            Weather weather = ResponseProcesser.requestWeather(app.getLanguage(), "Moscow");
            Dao.get(getApplicationContext()).addWeather(weather);

            sendBroadcast(new Intent(ACTION_UPDATE_WEATHER));
        }catch (Exception e) {
            //May I do nothing, if the request failed?
        }
    }
}
