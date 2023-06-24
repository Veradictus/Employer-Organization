package com.app.borgapplication.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.AvailabilityTable;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.impl.TrainingTable;
import com.app.borgapplication.utils.Utils;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddEmployeeFragment extends Fragment {
    private EmployeeViewModel mEmployeeViewModel;
    private AvailabilityViewModel mAvailabilityViewModel;
    private View view;
    private EmployeeTable employee;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private Spinner spinner;
    private Button addEmployeeButton;
    private Button addAvailabilityButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);
        mEmployeeViewModel = new ViewModelProvider(getActivity()).get(EmployeeViewModel.class);
        mAvailabilityViewModel = new ViewModelProvider(getActivity()).get(AvailabilityViewModel.class);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        EmployeeViewModel employeeViewModel = new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);
        Snackbar mySnackbar;

        switch (item.getItemId()) {
            case R.id.save_button:

                if (!isValid())
                    return false;

                EmployeeTable addEmployee = new EmployeeTable(firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString());

                ArrayList<AvailabilityTable> availabilityTables = new ArrayList<AvailabilityTable>();

                ArrayList<String> shiftArray = mAvailabilityViewModel.getmShiftArray();
                String training = spinner.getSelectedItem().toString();

                if(shiftArray != null) {
                    for (int j = 0; j<shiftArray.size(); j++) {
                        AvailabilityTable daysOfWeek =
                                new AvailabilityTable(addEmployee.getId(), j, shiftArray.get(j));
                        availabilityTables.add(daysOfWeek);
                    }
                    TrainingTable trainingTable = new TrainingTable(addEmployee.getId(), training);
                    mEmployeeViewModel.insert(addEmployee, availabilityTables, trainingTable);


                    mySnackbar = Snackbar.make(this.view, "Employee saved", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();

                    NavController navController = Navigation.findNavController(this.view);
                    return navController.navigateUp();
                }
                else {
                    mySnackbar = Snackbar.make(this.view, "Please enter Availability", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_employee_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firstName = getView().findViewById(R.id.add_first_name);
        lastName = getView().findViewById(R.id.add_last_name);
        email = getView().findViewById(R.id.add_email);
        spinner = getView().findViewById(R.id.add_training_spinner);
        addEmployeeButton = getView().findViewById(R.id.add_employee_button);

        ArrayList<EditText> viewList = new ArrayList();

        viewList.add(firstName);
        viewList.add(lastName);
        viewList.add(email);
        loadSpinner();

        addAvailabilityButton = getView().findViewById(R.id.btn_add_availability);
        addAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AddEmpFrag", "onClick: add availability");

                Bundle bundle = new Bundle();
//                bundle.putInt("employee_id", employeeId);

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_add_availability);
            }
        });
    }

    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.training, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setSelection(adapter.getCount() - 1);
    }

    private void displaySnackbar(String message) {
        Snackbar snackbar = Snackbar.make(this.view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    //Ensures that all fields have an entry input in them to avoid null values
    private boolean isValid() {
        if (firstName.getText().toString().length() < 2) {
            displaySnackbar("Please enter a valid first name.");
            return false;
        }

        if (lastName.getText().toString().length() < 2) {
            displaySnackbar("Please enter a valid last name.");
            return false;
        }

        if (!Utils.validEmail(email.getText().toString())) {
            displaySnackbar("The email you have entered is invalid.");
            return false;
        }

        return true;
    }
}
