package ru.overtired.yamblz2017.service;

import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import ru.overtired.yamblz2017.service.WeatherService;

/**
 * Created by overtired on 16.07.17.
 */

public class WeatherRequestJob extends Job {
    public static final String TAG = "ru.overtired.yamblz2017.weather_request_job";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        getContext().startService(WeatherService.newIntent(getContext()));
        return Result.SUCCESS;
    }

    public static void scheduleJob(long period){
        new JobRequest.Builder(TAG)
                .setPeriodic(period)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    public static void unscheduleJob(){
        JobManager.instance().cancelAllForTag(TAG);
    }
}
