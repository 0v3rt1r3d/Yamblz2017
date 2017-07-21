package ru.overtired.yamblz2017.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by overtired on 15.07.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DatabaseScheme.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DatabaseScheme.WeatherTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                DatabaseScheme.WeatherTable.Cols.CITY + "," +
                DatabaseScheme.WeatherTable.Cols.LANG + ","+
                DatabaseScheme.WeatherTable.Cols.WEATHER + "," +
                DatabaseScheme.WeatherTable.Cols.TEMP_C + "," +
                DatabaseScheme.WeatherTable.Cols.TEMP_F + "," +
                DatabaseScheme.WeatherTable.Cols.FEELS_C + "," +
                DatabaseScheme.WeatherTable.Cols.FEELS_F + "," +
                DatabaseScheme.WeatherTable.Cols.HUMIDITY + "," +
                DatabaseScheme.WeatherTable.Cols.WIND_KPH + "," +
                DatabaseScheme.WeatherTable.Cols.WIND_MPH + "," +
                DatabaseScheme.WeatherTable.Cols.DEW_POINT_C + "," +
                DatabaseScheme.WeatherTable.Cols.DEW_POINT_F + "," +
                DatabaseScheme.WeatherTable.Cols.ICON_URL + "," +
                DatabaseScheme.WeatherTable.Cols.DATE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
