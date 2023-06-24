package com.app.borgapplication.ui.employee;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.borgapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class AddAvailabilityFragment extends Fragment {

    private String shift = "Off";
    String[] shiftArray = new String[] { "Off","Off", "Off", //this array holds shifts(strings)
            "Off", "Off", "Off","Off"}; // Default is off

    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_availability, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AvailabilityViewModel availabilityViewModel =
                new ViewModelProvider(requireActivity()).get(AvailabilityViewModel.class);
        //setting all the buttons and onClicks ---------------------------------

        view.findViewById(R.id.button_sun).setOnClickListener(view1 -> {
            setButtonColor(view1.findViewById(R.id.button_sun)); // changes button color
            shiftArray[0] = shift; //updates array list
        });
        view.findViewById(R.id.button_mon).setOnClickListener(view12 -> {
            setButtonColor(view12.findViewById(R.id.button_mon));
            shiftArray[1] = shift;
        });
        view.findViewById(R.id.button_tues).setOnClickListener(view13 -> {
            setButtonColor(view13.findViewById(R.id.button_tues));
            shiftArray[2] = shift;
        });

        view.findViewById(R.id.button_wed).setOnClickListener(view14 -> {
            setButtonColor(view14.findViewById(R.id.button_wed));
            shiftArray[3] = shift;
        });

        view.findViewById(R.id.button_thurs).setOnClickListener(view15 -> {
            setButtonColor(view15.findViewById(R.id.button_thurs));
            shiftArray[4] = shift;
        });

        view.findViewById(R.id.button_fri).setOnClickListener(view16 -> {
            setButtonColor(view16.findViewById(R.id.button_fri));
            shiftArray[5] = shift;
        });

        view.findViewById(R.id.button_sat).setOnClickListener(view17 -> {
            setButtonColor(view17.findViewById(R.id.button_sat));
            shiftArray[6] = shift;
        });

        view.findViewById(R.id.button_day).setOnClickListener(v -> shift = "Day");

        view.findViewById(R.id.button_evening).setOnClickListener(v -> shift = "Evening");

        view.findViewById(R.id.button_both).setOnClickListener(v -> shift = "Both");

        view.findViewById(R.id.button_off).setOnClickListener(v -> shift = "Off");

        view.findViewById(R.id.save_availability).setOnClickListener(v -> {
            Snackbar mySnackbar = Snackbar.make(view, "Availability Saved", Snackbar.LENGTH_SHORT);
            mySnackbar.show();

            NavController navController = Navigation.findNavController(view);

            ArrayList<String> shiftArrayList = new ArrayList<String>(Arrays.asList(shiftArray));
            availabilityViewModel.setmShiftArray(shiftArrayList);

            navController.navigateUp();

        });

    }

    /**
     * this method changes the color of button
     * @param btn the button passed changes color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setButtonColor(Button btn) {
        switch (shift) {
            case "Day":
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorLightBlue));
                break;
            case "Evening":
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorOrange));
                break;
            case "Both":
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorDarkBlue));
                break;
            default:
                btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorDarkGrey));
                break;
        }
    }

}
