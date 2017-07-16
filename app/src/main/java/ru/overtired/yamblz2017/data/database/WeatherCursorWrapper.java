package ru.overtired.yamblz2017.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.text.SimpleDateFormat;

import ru.overtired.yamblz2017.data.Weather;

/**
 * Created by overtired on 15.07.17.
 */

public class WeatherCursorWrapper extends CursorWrapper {
    public WeatherCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Weather getWeather(){
        Weather weather = new Weather();

        weather.city = getString(getColumnIndex(DatabaseScheme.WeatherTable.Cols.CITY));
        weather.weather = getString(getColumnIndex(DatabaseScheme.WeatherTable.Cols.WEATHER));

        weather.tempCelsius = getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.TEMP_C));
        weather.tempFarengate = getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.TEMP_F));

        weather.feelsLikeCelsius= getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.FEELS_C));
        weather.feelsLikeFarengate= getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.TEMP_F));

        weather.humidity = getString(getColumnIndex(DatabaseScheme.WeatherTable.Cols.HUMIDITY));

        weather.windSpeedKph = getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.WIND_KPH));
        weather.windSpeedMph = getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.WIND_MPH));

        weather.dewPointCelsius = getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.DEW_POINT_C));
        weather.dewPointFarengate= getDouble(getColumnIndex(DatabaseScheme.WeatherTable.Cols.DEW_POINT_F));

        weather.imageUrl = getString(getColumnIndex(DatabaseScheme.WeatherTable.Cols.ICON_URL));

        String dateText = getString(getColumnIndex(DatabaseScheme.WeatherTable.Cols.DATE));

        try {
            weather.date = new SimpleDateFormat(Weather.DATE_FORMAT).parse(dateText);
        }catch (Exception e){
            //TODO: Exception
        }
        return weather;
    }
}
