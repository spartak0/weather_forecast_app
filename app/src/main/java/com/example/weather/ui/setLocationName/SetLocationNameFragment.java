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

import com.example.weather.R;
import com.example.weather.databinding.FragmentSetLocationNameBinding;
import com.example.weather.ui.main.MainActivity;
import com.example.weather.ui.maps.MapsActivity;
import com.example.weather.utils.Constant;

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
                        //Bundle bundle = new Bundle();
                        //bundle.putString(Constant.locationName, binding.etName.getText().toString());
                        //Navigation.findNavController(view).navigate(R.id.action_setLocationNameFragment_to_mapsActivity, bundle);

                        Intent intent = new Intent(binding.getRoot().getContext().getApplicationContext(), MapsActivity.class);
                        intent.putExtra(Constant.locationName, binding.etName.getText().toString());
                        startActivity(intent);
                    }
                }
        );
    }



}