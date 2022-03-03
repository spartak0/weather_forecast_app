package com.example.weather.ui.setLocationName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.databinding.FragmentSetLocationNameBinding;
import com.example.weather.ui.main.MainActivity;
import com.example.weather.ui.maps.MapsActivity;
import com.example.weather.utils.Constant;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class SetLocationNameFragment extends Fragment {

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
                                intent.putExtra(Constant.locationName, tvText);
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