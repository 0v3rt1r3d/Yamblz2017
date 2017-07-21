package ru.overtired.yamblz2017;

import android.app.Application;
import android.util.Log;

import com.evernote.android.job.*;
import com.evernote.android.job.BuildConfig;
import com.facebook.stetho.Stetho;

import java.util.Locale;

import ru.overtired.yamblz2017.data.database.Dao;
import ru.overtired.yamblz2017.service.WeatherJobCreator;

/**
 * Created by overtired on 16.07.17.
 */

public class App extends Application {
    private Dao dao;
    private String language;

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new WeatherJobCreator());

        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }

        language = Locale.getDefault().getLanguage();
        Log.d("Lang", language);
    }

    public String getLanguage(){
        return language;
    }

    public Dao getDao() {
        return dao;
    }
    public void setDao(Dao dao){
        this.dao = dao;
    }
}
