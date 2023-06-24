package com.app.borgapplication.ui.employee;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.borgapplication.database.ScheduleRepository;
import com.app.borgapplication.database.impl.AvailabilityTable;

import java.util.ArrayList;
import java.util.List;

// Viewmodel to deal with Employee Availability table in the Schedule Database
public class AvailabilityViewModel extends AndroidViewModel {

    public ArrayList<String> mShiftArray;
    private ScheduleRepository mRepository;
    private LiveData<List<AvailabilityTable>> mAllAvailabilities;

    public AvailabilityViewModel(Application application) {
        super(application);
        mRepository = new ScheduleRepository(application);
        mAllAvailabilities = mRepository.getAllAvailabilities();
    }
    public void updateAvailability(AvailabilityTable availability) {
        mRepository.updateAvailability(availability);
    }

    LiveData<List<AvailabilityTable>> getAvailabilityByEmployeeId(int employee_id) {
        return mRepository.getEmployeeAvailability(employee_id);
    }

    public void setmShiftArray(ArrayList<String> mShiftArray) {
        this.mShiftArray = mShiftArray;
    }

    ArrayList<String> getmShiftArray(){
        return this.mShiftArray;
    }

    public void insert(AvailabilityTable employee_availability) { mRepository.insertAvailability(employee_availability);}

}
