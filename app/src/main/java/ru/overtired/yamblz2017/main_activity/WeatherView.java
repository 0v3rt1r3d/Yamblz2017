package ru.overtired.yamblz2017.main_activity;

import ru.overtired.yamblz2017.data.Weather;

/**
 * Created by overtired on 18.07.17.
 */

public interface WeatherView {
    enum MESSAGE{INTERNET_ERROR}

    void setWeather(Weather weather);
    void hideProgress();
    void showMessage(MESSAGE message);
}
