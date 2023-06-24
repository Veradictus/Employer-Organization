package com.app.borgapplication.ui.schedule;

import android.app.Application;
import android.graphics.LightingColorFilter;
import android.icu.text.SymbolTable;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.borgapplication.database.ScheduleRepository;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.DateTable;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinEmployeesLeftBehind;
import com.app.borgapplication.database.impl.JoinMonthlyShiftInfo;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.database.impl.JoinShiftReport;
import com.app.borgapplication.database.impl.ShiftTable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

    private ScheduleRepository mRepository;
    public LiveData<List<JoinMonthlyShiftInfo>> mMonthlyShiftInfo;
    public LiveData<List<DateTable>> mDaysWithNoShifts;
    public LiveData<List<JoinEmployeesLeftBehind>> mEmployeesNotScheduled;
    public LiveData<List<JoinShiftReport>> mShiftReport;
    public LiveData<List<JoinDailyAvailable>> mDailyAvailable;
    private final MutableLiveData<Long> currentDate = new MutableLiveData<Long>();
    public LiveData<List<Integer>> clickedDate;

    public void removeShift(ShiftTable shift) {
        mRepository.deleteShift(shift);
    }


    public void setToSchedule(ShiftTable toSchedule) {
        System.out.println(toSchedule);
        mRepository.insertShift(toSchedule);
    }

    public LiveData<List<JoinMonthlyShiftInfo>> getMonthlyShiftInfo(long start, long end) {
        return mRepository.getMonthlySchedule(start, end);
    }

    public LiveData<List<DateTable>> getDaysWithNoShifts (long start, long end) {
        return mRepository.getShiftsNotScheduled(start, end);
    }

    public LiveData<List<JoinShiftReport>> getOpenReport (long start, long end, String shiftType){
        return mRepository.getShiftReport(start, end, shiftType);
    }

    public LiveData<List<JoinShiftReport>> getCloseReport (long start, long end, String shiftType){
        return mRepository.getShiftReport(start, end, shiftType);
    }

    public LiveData<List<JoinEmployeesLeftBehind>> getmEmployeesNotScheduled(long start, long end) {
        return mRepository.getEmployeesLeftBehind(start, end);
    }

    public ScheduleViewModel(Application application) {
        super(application);
        mRepository = new ScheduleRepository(application);
    }

    public void setCurrentDate(long clickedDate) {
        currentDate.setValue(clickedDate);
    }

    public LiveData<Long> getCurrentDate() {
        return this.currentDate;
    }

    public LiveData<List<JoinDailyAvailable>> dailyAvailable(int dayOfWeek, int dayOfMonth, int month, int year) {
        return mRepository.getDailyAvailability(dayOfWeek, dayOfMonth, month, year);
    }

    public LiveData<List<JoinShiftDateInfo>>getShiftOnDate(Calendar date) {
        return mRepository.getShiftOnDate(date);
    }
}

