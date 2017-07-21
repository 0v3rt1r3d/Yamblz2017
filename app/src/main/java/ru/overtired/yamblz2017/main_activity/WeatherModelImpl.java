package ru.overtired.yamblz2017.main_activity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import ru.overtired.yamblz2017.App;
import ru.overtired.yamblz2017.data.ResponseProcesser;
import ru.overtired.yamblz2017.data.Weather;
import ru.overtired.yamblz2017.data.database.Dao;

/**
 * Created by overtired on 18.07.17.
 */

public class WeatherModelImpl implements WeatherModel {
    private WeatherPresenter presenter;

    private Context context;
    AsyncFetcher fetcher;

    public WeatherModelImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void setPresenter(WeatherPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public WeatherPresenter getPresenter() {
        return null;
    }

    @Override
    public void addWeather(Weather weather) {
        Dao.get(context).addWeather(weather);
    }

    @Override
    public Weather getLastWeather() {
        String lang = ((App)context.getApplicationContext()).getLanguage();
        return Dao.get(context).getLastWeather(lang);
    }

    @Override
    public void loadNewWeather() {
        if (fetcher != null) {
            fetcher.cancel(true);
        }

        App app = (App)context.getApplicationContext();

        fetcher = new AsyncFetcher();
        fetcher.execute(app.getLanguage());//TODO: params
    }

    class AsyncFetcher extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {
            try {
                Weather weather = ResponseProcesser.requestWeather(params[0],"Moscow");
                addWeather(weather);

                return weather;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather == null) {
                presenter.onWeatherLoadingError();
            } else {
                presenter.onWeatherLoaded();
            }
        }
    }
}
