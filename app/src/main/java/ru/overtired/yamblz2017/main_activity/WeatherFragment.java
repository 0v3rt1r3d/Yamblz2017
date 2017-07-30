package ru.overtired.yamblz2017.main_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.overtired.yamblz2017.R;
import ru.overtired.yamblz2017.data.Weather;
import ru.overtired.yamblz2017.service.WeatherService;

/**
 * Created by overtired on 09.07.17.
 */

public class WeatherFragment extends Fragment implements  WeatherView{
    public static final String TAG = "WeatherFragment";

    WeatherPresenter presenter;

    private Unbinder unbinder;

    private SharedPreferences sharedPreferences;

    @BindView(R.id.card_time)
    TextView cardTime;
    @BindView(R.id.card_image)
    ImageView cardImage;
    @BindView(R.id.card_temp)
    TextView cardTemp;
    @BindView(R.id.card_feelslike_temp)
    TextView cardFeelsTemp;
    @BindView(R.id.card_humidity)
    TextView cardHumidity;
    @BindView(R.id.card_windspeed)
    TextView cardWindSpeed;
    @BindView(R.id.card_weather)
    TextView cardWeather;
    @BindView(R.id.card_city)
    TextView cardCity;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.onUpdateWeather();
        }
    };

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HolderFragment<WeatherPresenter> holder = (HolderFragment<WeatherPresenter>)
                getFragmentManager()
                .findFragmentByTag(HolderFragment.TAG);
        if(holder ==null){
            holder = new HolderFragment<>();
            getFragmentManager().beginTransaction()
                    .add(holder,HolderFragment.TAG)
                    .commit();
        }

        presenter = holder.getPresenter();
        if(presenter==null){
            presenter = new WeatherPresenterImpl(this,
                    new WeatherModelImpl(getActivity().getApplicationContext()));
            holder.setPresenter(presenter);
        }else{
            presenter.setView(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        IntentFilter filter = new IntentFilter(WeatherService.ACTION_UPDATE_WEATHER);
        getActivity().registerReceiver(updateReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(updateReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_activity_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefreshWeather();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setWeather(Weather weather) {
        Picasso.with(getActivity().getApplicationContext())
                .load(weather.imageUrl).into(cardImage);
        cardTemp.setText(Double.toString(weather.tempCelsius)+"°C ");
        cardFeelsTemp.setText(Double.toString(weather.feelsLikeCelsius)+"°C ");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        cardTime.setText(format.format(weather.date));
        cardHumidity.setText(weather.humidity);
        cardWindSpeed.setText(weather.windSpeedKph+" "+getString(R.string.kph));
        cardWeather.setText(weather.weather);
        cardCity.setText(
                sharedPreferences.getString(getString(R.string.pref_key_select_city),
                getString(R.string.moscow)));
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(MESSAGE message) {
        String text = "";
        switch (message){
            case INTERNET_ERROR: text = getString(R.string.no_internet_error);
        }
        Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
    }
}
