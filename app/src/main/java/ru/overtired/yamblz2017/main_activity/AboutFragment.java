package ru.overtired.yamblz2017.main_activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.overtired.yamblz2017.R;

/**
 * Created by overtired on 14.07.17.
 */

public class AboutFragment extends DialogFragment {
    public static final String TAG = "AboutFragment";

    @NonNull
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_about,container,false);
        return view;
    }
}
