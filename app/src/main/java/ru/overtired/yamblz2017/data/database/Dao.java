package ru.overtired.yamblz2017.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.overtired.yamblz2017.App;
import ru.overtired.yamblz2017.data.Weather;

/**
 * Created by overtired on 15.07.17.
 */

public class Dao {
    private Context context;

    @NonNull
    public static Dao get(@NonNull Context context) {

        App app = (App) context.getApplicationContext();

        if (app.getDao() == null) {
            Dao dao = new Dao(context.getApplicationContext());
            app.setDao(dao);
        }

        return app.getDao();
    }


    private Dao(@NonNull Context context) {
        this.context = context;
    }

    public synchronized void addWeather(@NonNull Weather weather) {
        SQLiteDatabase database = new DatabaseHelper(context).getWritableDatabase();

        ContentValues contentValues = getWeatherContentValues(weather);

        database.insert(DatabaseScheme.WeatherTable.NAME, null, contentValues);
    }

    @Nullable
    public synchronized Weather getLastWeather(String lang) {
        SQLiteDatabase database = new DatabaseHelper(context).getReadableDatabase();

        Cursor cursor = database.query(DatabaseScheme.WeatherTable.NAME,
                null,
                DatabaseScheme.WeatherTable.Cols.LANG+"=?",
                new String[]{lang},
                null,
                null,
                DatabaseScheme.WeatherTable.Cols.DATE
        );
        WeatherCursorWrapper cursorWrapper = new WeatherCursorWrapper(cursor);


        if (cursorWrapper.getCount() == 0) {
            cursorWrapper.close();
            return null;
        } else {
            cursorWrapper.moveToLast();
            Weather weather = cursorWrapper.getWeather();
            return weather;
        }
    }

    @NonNull
    private ContentValues getWeatherContentValues(@NonNull Weather weather) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseScheme.WeatherTable.Cols.CITY, weather.city);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.TEMP_C, weather.tempCelsius);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.TEMP_F, weather.tempFarengate);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.FEELS_C, weather.feelsLikeCelsius);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.FEELS_F, weather.feelsLikeFarengate);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.HUMIDITY, weather.humidity);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.WIND_KPH, weather.windSpeedKph);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.WIND_MPH, weather.windSpeedMph);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.DEW_POINT_C, weather.dewPointCelsius);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.DEW_POINT_F, weather.dewPointFarengate);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.ICON_URL, weather.imageUrl);
        contentValues.put(DatabaseScheme.WeatherTable.Cols.WEATHER, weather.weather);

        contentValues.put(DatabaseScheme.WeatherTable.Cols.DATE, weather.date.getTime());
        contentValues.put(DatabaseScheme.WeatherTable.Cols.LANG,weather.lang);

        return contentValues;
    }
}
