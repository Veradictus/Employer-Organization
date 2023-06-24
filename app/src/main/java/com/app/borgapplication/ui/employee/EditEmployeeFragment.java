package com.app.borgapplication.ui.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.impl.TrainingTable;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class EditEmployeeFragment extends Fragment  {
    View view;
    EmployeeTable employee;
    //Set hints in dialogue boxes to corresponding database value
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    Button btnSave;
    Button button;
    Spinner spinnerTraining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_employee, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int employeeId = getArguments().getInt("employee_id");
        String trainingFromDB = getArguments().getString("training_status");
        System.out.println("Training: " + trainingFromDB + '\n');
        etFirstName = getView().findViewById(R.id.edit_first_name);
        etLastName = getView().findViewById(R.id.edit_last_name);
        etEmail = getView().findViewById(R.id.edit_email);
        spinnerTraining = (Spinner) getView().findViewById(R.id.spinner_training);
        btnSave = getView().findViewById(R.id.save_fields);

        ArrayList<EditText> viewList = new ArrayList();
        viewList.add(etFirstName);
        viewList.add(etLastName);
        viewList.add(etEmail);

        EmployeeViewModel employeeViewModel = new ViewModelProvider(requireActivity()).get(EmployeeViewModel.class);

        employeeViewModel.findEmployeeById(employeeId).observe(getViewLifecycleOwner(),
                new Observer<List<EmployeeTable>>() {
                    @Override
                    public void onChanged(List<EmployeeTable> employeeTable) {
                       etFirstName.setText(employeeTable.get(0).getFirstName());
                       etLastName.setText(employeeTable.get(0).getLastName());
                       etEmail.setText(employeeTable.get(0).getEmail());
                    }
                });

       // SPINNER (TRAINING)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.training, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraining.setAdapter(adapter);
        //sets item selected in spinner. Need to grab info from database..
        int spinnerPosition = adapter.getPosition(trainingFromDB);
        spinnerTraining.setSelection(spinnerPosition);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String training = spinnerTraining.getSelectedItem().toString();

                EmployeeTable updateEmployee = new EmployeeTable(employeeId, viewList.get(0).getText().toString(),
                        viewList.get(1).getText().toString(),
                        viewList.get(2).getText().toString());
                TrainingTable updateTraining  = new TrainingTable(employeeId, training);
                    employeeViewModel.update(updateEmployee, updateTraining);

                Snackbar mySnackbar = Snackbar.make(view, "Changes Saved", Snackbar.LENGTH_SHORT);
                mySnackbar.show();

                NavController navController = Navigation.findNavController(view);
                navController.navigateUp();
                }
            });

        button = (Button) getView().findViewById(R.id.button_to_addAvailability);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("employee_id", employeeId);
                bundle.putString("training_status", trainingFromDB);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_edit_availability, bundle);
            }
        });
    }
}

