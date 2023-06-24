package com.app.borgapplication.ui.schedule.editSchedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.ui.employee.EmployeeListAdapter;
import com.app.borgapplication.ui.employee.EmployeeViewModel;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class EditScheduleFragment extends Fragment {

    private ScheduleViewModel mScheduleViewModel;
    private View view;
    private String dateString;
    public static long date;
    public static Context contextOf;
    public Calendar calDate;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_schedule, container, false);
        mScheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        contextOf = getContext();
        AvailableOnDateFragment availableOnDateFragment = new AvailableOnDateFragment();

        getParentFragmentManager().beginTransaction()
                .remove(availableOnDateFragment)
                .replace(R.id.availableToday, availableOnDateFragment)
                .commit();

        date = getArguments().getLong("date");
//        view.findViewById(R.id.date_string).setText(Converters.fromTimestamp(date).YEAR);
        TextView date_string = view.findViewById(R.id.date_string);
        Calendar day = Converters.fromTimestamp(date);
        int year = day.get(day.YEAR);
        int dayOfWeek = day.get(day.DAY_OF_WEEK);
        int dayOfMonth = day.get(day.DAY_OF_MONTH);
        int month = day.get(day.MONTH);

        date_string.setText("DATE:  " + year + '-' + (month +1) + '-' + dayOfMonth +
                "          DAY OF WEEK: " + getDay(dayOfWeek));

        return view;
    }

    public void toSchedule (ShiftTable toSchedule) {
        mScheduleViewModel.setToSchedule(toSchedule);
    }



    public static Context getContextOfApplication(){
        return contextOf;
    }

    public String getDay(int day_of_week) {
        String dayStr;
        switch (day_of_week) {
            case 1:
                dayStr = "Sunday";
                return dayStr;
            case 2:
                dayStr = "Monday";
                return dayStr;
            case 3:
                dayStr = "Tuesday";
                return dayStr;
            case 4:
                dayStr = "Wednesday";
                return dayStr;
            case 5:
                dayStr = "Thursday";
                return dayStr;
            case 6:
                dayStr = "Friday";
                return dayStr;
            case 7:
                dayStr = "Saturday";
                return dayStr;
        }
        return null;
    }
}
