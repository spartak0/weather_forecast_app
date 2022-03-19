package com.example.weather.ui.set_configuration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.example.weather.databinding.FragmentSetLocationNameBinding;
import com.example.weather.ui.maps.MapsActivity;
import com.example.weather.utils.Constant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class SetConfigurationFragment extends Fragment {

    private FragmentSetLocationNameBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentSetLocationNameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().setTitle(R.string.settings);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.btnOk.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tvText= binding.etName.getText().toString();
                        switch (validTest(tvText)){
                            case 0:
                                Intent intent = new Intent(binding.getRoot().getContext().getApplicationContext(), MapsActivity.class);
                                intent.putExtra(Constant.LOCATION_NAME, tvText);
                                intent.putExtra(Constant.IS_FAVORITE, binding.checkBoxIsFavorite.isChecked());
                                intent.putExtra(Constant.SECOND_DAY_FORECAST, binding.checkBoxSecondForecast.isChecked());
                                startActivity(intent);
                                break;
                            case 1:
                                binding.tvError.setText("Введите не пустое значение!!!");
                                Completable.timer(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        binding.tvError.setText("");
                                    }
                                });
                                break;
                        }

                    }
                }
        );
    }

    //0- succes, other- error code
    int validTest(String text){
        if (text.isEmpty()) return 1;
        return 0;
    }



}