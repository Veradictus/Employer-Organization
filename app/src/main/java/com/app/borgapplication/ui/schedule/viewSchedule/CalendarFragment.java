package com.app.borgapplication.ui.schedule.viewSchedule;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    private View scheduleView;
    private ScheduleViewModel scheduleViewModel;
    private CalendarView calendarView;
    private long date;

    private int selectedYear = -1;
    private int selectedMonth = -1;
    private int selectedDay = -1;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        scheduleView = inflater.inflate(R.layout.fragment_nav_schedule, container, false);
        calendarView = (CalendarView) scheduleView.findViewById(R.id.calendarView);
        scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        date = calendarView.getDate();
        scheduleViewModel.setCurrentDate(date);

        //gets bundle from schedule view and sets highlight on selected day
        if (getArguments()!=null){
            long selectedDate = getArguments().getLong("selectedDate");
            calendarView.setDate(selectedDate);
            scheduleViewModel.setCurrentDate(selectedDate);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar dateClick = Calendar.getInstance();
                dateClick.set(year, month, dayOfMonth);

                System.out.println(dateClick.getTimeInMillis());

                scheduleViewModel.setCurrentDate(dateClick.getTimeInMillis());

                System.out.println("Clicked on date: " + year + " month: " + month + " day: " + dayOfMonth);

                setSelectedDay(dayOfMonth);
                setSelectedMonth(month);
                setSelectedYear(year);
            }
        });

        return scheduleView;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public boolean dateSelected() {
        return getSelectedDay() != -1 && getSelectedMonth() != -1 && getSelectedYear() != -1;
    }
}
