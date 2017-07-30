package ru.overtired.yamblz2017;

import org.junit.Before;
import org.junit.Test;

import ru.overtired.yamblz2017.data.Weather;
import ru.overtired.yamblz2017.main_activity.WeatherFragment;
import ru.overtired.yamblz2017.main_activity.WeatherModelImpl;
import ru.overtired.yamblz2017.main_activity.WeatherPresenterImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WeatherPresenterImplTest {

    private WeatherPresenterImpl weatherPresenterImpl;
    private WeatherFragment weatherFragment;
    private WeatherModelImpl weatherModel;
    private Weather weather;

    @Before
    public void setUp() {
        weatherModel = mock(WeatherModelImpl.class);
        weatherFragment = WeatherFragment.newInstance();
        weatherPresenterImpl = new WeatherPresenterImpl(weatherFragment, weatherModel);
        weather = mock(Weather.class);
    }

    @Test
    public void onRefreshWeatherTest() {
        weatherPresenterImpl.onRefreshWeather();
        verify(weatherModel).loadNewWeather();
    }

}
