package ru.overtired.yamblz2017.main_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by overtired on 19.07.17.
 */

public class HolderFragment<T extends WeatherPresenter> extends Fragment {
    public static final String TAG = "HOLDER_TAG";

    private T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public @Nullable T getPresenter(){
        return presenter;
    }

    public void setPresenter(T presenter){
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.onDestroy();
        }
    }
}
