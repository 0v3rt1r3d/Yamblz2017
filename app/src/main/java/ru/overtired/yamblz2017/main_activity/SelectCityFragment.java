package ru.overtired.yamblz2017.main_activity;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.overtired.yamblz2017.R;
import ru.overtired.yamblz2017.data.AutoComplete;
import ru.overtired.yamblz2017.data.Result;
import ru.overtired.yamblz2017.data.ResponseProcesser;

public class SelectCityFragment extends DialogFragment {

    @BindView(R.id.enter_city_edittext)
    EditText cityEditText;

    @BindView(R.id.city_suggestion_list)
    ListView suggestionsListView;

    private Dialog dialog;

    private SharedPreferences sharedPreferences;

    private ArrayAdapter<String> listAdapter;

    public SelectCityFragment() {
        // Required empty public constructor
    }

    public static SelectCityFragment newInstance() {
        return new SelectCityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_city, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        cityEditText.setText(sharedPreferences.getString(getString(R.string.pref_key_select_city), sharedPreferences.getString(getString(R.string.moscow), "")));

        Observable<String> cityObservable = RxTextView.textChanges(cityEditText)
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(text -> text.length() > 0)
                .map(CharSequence::toString)
                .distinctUntilChanged();

        cityObservable.subscribe(s -> {
            ResponseProcesser.requestAutoComplete().getCitiesList(s, "RU").enqueue(new Callback<AutoComplete>() {
                @Override
                public void onResponse(Call<AutoComplete> call, Response<AutoComplete> response) {
                    listAdapter.clear();
                    for(Result result : response.body().getRESULTS()) {
                        listAdapter.add(result.getName());
                    }
                    suggestionsListView.setAdapter(listAdapter);
                }

                @Override
                public void onFailure(Call<AutoComplete> call, Throwable t) {
                    Toast.makeText(getActivity(), "Невозможно получить список городов", Toast.LENGTH_SHORT).show();
                }
            });
        });

        suggestionsListView.setOnItemClickListener((parent, view, position, id) -> {
            final String location = ((TextView) view).getText().toString().split(",")[0];
            sharedPreferences.edit()
                    .putString(getString(R.string.pref_key_select_city), location)
                    .apply();
            cityEditText.setText(location);
            dialog.dismiss();
        });
    }
}