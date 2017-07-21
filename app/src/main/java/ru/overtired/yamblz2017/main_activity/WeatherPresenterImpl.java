package ru.overtired.yamblz2017.main_activity;

import ru.overtired.yamblz2017.data.Weather;

/**
 * Created by overtired on 18.07.17.
 */

public class WeatherPresenterImpl implements WeatherPresenter {
    private WeatherView view;
    private WeatherModel model;

    public WeatherPresenterImpl(WeatherView view, WeatherModel model){
        this.view = view;
        this.model = model;
        model.setPresenter(this);
    }

    @Override
    public void onRefreshWeather() {
        model.loadNewWeather();
    }

    @Override
    public void onUpdateWeather() {
        Weather weather = model.getLastWeather();
        if(weather == null){
            onRefreshWeather();
        }else {
            view.setWeather(weather);
        }
    }

    @Override
    public void onResume() {
        onUpdateWeather();
    }

    @Override
    public void setView(WeatherView view){
        this.view = view;
    }

    @Override
    public void onWeatherLoaded() {

        view.setWeather(model.getLastWeather());
        view.hideProgress();
    }

    @Override
    public void onWeatherLoadingError() {
        view.showMessage(WeatherView.MESSAGE.INTERNET_ERROR);
        view.hideProgress();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
