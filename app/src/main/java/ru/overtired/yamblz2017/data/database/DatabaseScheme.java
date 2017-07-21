package ru.overtired.yamblz2017.data.database;

/**
 * Created by overtired on 15.07.17.
 */

public class DatabaseScheme {
    public static final String DATABASE_NAME = "weather.db";

    public static final class WeatherTable{
        public static final String NAME = "weather_table";

        public static final class Cols{
            public static final String CITY = "city";
            public static final String LANG = "lang";
            public static final String WEATHER = "weather";
            public static final String TEMP_C = "temp_c";
            public static final String TEMP_F = "temp_f";
            public static final String FEELS_C = "feels_c";
            public static final String FEELS_F = "feels_f";
            public static final String HUMIDITY = "humidity";
            public static final String WIND_KPH = "wind_kph";
            public static final String WIND_MPH = "wind_mph";
            public static final String DEW_POINT_C = "dew_point_c";
            public static final String DEW_POINT_F = "dew_point_f";
            public static final String ICON_URL = "icon_url";
            public static final String DATE = "date";
        }
    }
}
