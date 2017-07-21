package ru.overtired.yamblz2017.main_activity;

import ru.overtired.yamblz2017.data.Weather;

/**
 * Created by overtired on 18.07.17.
 */

public interface WeatherModel {
    void setPresenter(WeatherPresenter presenter);
    WeatherPresenter getPresenter();
    void addWeather(Weather weather);
    Weather getLastWeather();
    void loadNewWeather();
}
