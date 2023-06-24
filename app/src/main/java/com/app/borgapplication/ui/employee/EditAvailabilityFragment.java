package com.app.borgapplication.ui.employee;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.AvailabilityTable;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class EditAvailabilityFragment extends Fragment {

    private String shift;
    TextView tEmployeeName;
    Button bSaveAvailability;
    String[] shiftArray = new String[] { "Off","Off", "Off", //this array holds shifts(strings)
            "Off", "Off", "Off","Off"}; // Default is off
    ArrayList<Integer> mKeys = new ArrayList<Integer>();
    @Override
    public View onCreateView(

            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_availability, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int employeeId = getArguments().getInt("employee_id");
        tEmployeeName = getView().findViewById(R.id.employee_name);

        // Observe current Employee_Name.
        EmployeeViewModel employeeViewModel =
                new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);
        //Observe current Employee Availability
        AvailabilityViewModel availabilityViewModel =
                new ViewModelProvider(requireActivity()).get(AvailabilityViewModel.class);

        employeeViewModel.findEmployeeById(employeeId).observe(getViewLifecycleOwner(), new Observer<List<EmployeeTable>>() {
            @Override
            public void onChanged(List<EmployeeTable> employeeTables) {
                tEmployeeName.setText(employeeTables.get(0).getFirstName() + ' ' + employeeTables.get(0).getLastName());
            }
        });

        availabilityViewModel.getAvailabilityByEmployeeId(employeeId).observe(getViewLifecycleOwner(), new Observer<List<AvailabilityTable>>() {
            @Override
            public void onChanged(List<AvailabilityTable> availabilityTables) {
                for(int i = 0; i < availabilityTables.size(); i++) {
                    int dayNum = availabilityTables.get(i).getDayNumber();
                    System.out.println(dayNum + "<<\n");
                    mKeys.add(availabilityTables.get(i).getId());
                    switch (dayNum) {
                        //Sunday
                        case 0:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[0] = shift;
                            setButtonColor(view.findViewById(R.id.button_sun));
                            break;
                        // Monday
                        case 1:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[1] = shift;
                            setButtonColor(view.findViewById(R.id.button_mon));
                            break;
                        //Tuesday
                        case 2:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[2] = shift;
                            setButtonColor(view.findViewById(R.id.button_tues));
                            break;
                        //Wednesday
                        case 3:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[3] = shift;
                            setButtonColor(view.findViewById(R.id.button_wed));
                            break;
                        //Thursday
                        case 4:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[4] = shift;
                            setButtonColor(view.findViewById(R.id.button_thurs));
                            break;
                        //Friday
                        case 5:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[5] = shift;
                            setButtonColor(view.findViewById(R.id.button_fri));
                            break;
                        //Saturday
                        case 6:
                            shift = availabilityTables.get(i).getAvailability();
                            shiftArray[6] = shift;
                            setButtonColor(view.findViewById(R.id.button_sat));
                            break;
                        }
                    }
//                System.out.println(mKeys.size() + "<<<<!!!<<<\n");
            }
        });
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

        view.findViewById(R.id.button_day).setOnClickListener(view18 -> shift = "Day");

        view.findViewById(R.id.button_evening).setOnClickListener(view19 -> shift = "Evening");

        view.findViewById(R.id.button_both).setOnClickListener(view110 -> shift = "Both");

        view.findViewById(R.id.button_off).setOnClickListener(view111 -> shift = "Off");

        view.findViewById(R.id.save_availability).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mKeys + "!!!---");
                for(int j=0; j< 7; j++) {
                    AvailabilityTable update = new AvailabilityTable(mKeys.get(j), employeeId, j, shiftArray[j]);
                    availabilityViewModel.updateAvailability(update);
                }

                Snackbar mySnackbar = Snackbar.make(view, "Availability Saved", Snackbar.LENGTH_SHORT);
                mySnackbar.show();

                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
            }
        });

        // ---------------------------------------------------------------
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
