package ru.overtired.yamblz2017.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by overtired on 14.07.17.
 */

public class Weather {
    public static final String DATE_FORMAT = "EEE, d MMM yyyy hh:mm:ss Z";

    public String city;
    public Date date;

    @SerializedName("weather")
    public String weather;

    @SerializedName("temp_c")
    public double tempCelsius;
    @SerializedName("temp_f")
    public double tempFarengate;

    @SerializedName("feelslike_c")
    public double feelsLikeCelsius;
    @SerializedName("feelslike_f")
    public double feelsLikeFarengate;

    @SerializedName("relative_humidity")
    public String humidity;

    @SerializedName("wind_kph")
    public double windSpeedKph;
    @SerializedName("wind_mph")
    public double windSpeedMph;

    @SerializedName("dewpoint_c")
    public double dewPointCelsius;
    @SerializedName("dewpoint_f")
    public double dewPointFarengate;

    @SerializedName("icon_url")
    public String imageUrl;
}