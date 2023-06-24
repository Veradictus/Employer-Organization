package com.app.borgapplication.ui.employee;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.app.borgapplication.database.impl.AvailabilityTable;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.ScheduleRepository;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinEmployeeInformation;
import com.app.borgapplication.database.impl.TrainingTable;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {

    boolean isExpanded;
    private ScheduleRepository mRepository;
    private LiveData<List<EmployeeTable>> mAllEmployees;
    private LiveData<List<JoinEmployeeInformation>> mEmployeeAvailability;

    public EmployeeViewModel(Application application) {
        super(application);
        mRepository = new ScheduleRepository(application);
        mAllEmployees = mRepository.getAllEmployees();
    }

    public LiveData<List<EmployeeTable>> getAllEmployees() { return mAllEmployees; }

    LiveData<List<JoinEmployeeInformation>> getEmployeeInformation() {
        return mRepository.getEmployeeInformation();
    }

    LiveData<List<EmployeeTable>> getEmployeeByEmail(String email) {
        return mRepository.getEmployeeIdByEmail(email);
    }
    public void insert(EmployeeTable employee, ArrayList<AvailabilityTable> availability, TrainingTable training) {
        mRepository.insertEmployeeInformation(employee, availability, training);
    }

    public void update(EmployeeTable employee, TrainingTable training) { mRepository.update(employee, training); }

    public void delete(EmployeeTable employee){ mRepository.delete(employee); }

    LiveData<List<EmployeeTable>> findEmployeeById(int primaryKey) { return mRepository.findEmployeeById(primaryKey); }

    //--------------------------------------------------- For Expandedviews
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

}
