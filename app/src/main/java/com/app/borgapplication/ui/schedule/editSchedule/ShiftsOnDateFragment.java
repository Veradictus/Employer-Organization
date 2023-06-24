package com.app.borgapplication.ui.schedule.editSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;

import java.util.Calendar;
import java.util.List;

public class ShiftsOnDateFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShiftsOnDateAdapter mShiftsOnDateAdapter;
    private ScheduleViewModel mScheduleViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private View view;

    public ShiftsOnDateFragment() {
        //req'd public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shifts_on_day, container, false);
        setHasOptionsMenu(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.availableview);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mShiftsOnDateAdapter = new ShiftsOnDateAdapter(this.getActivity());
        recyclerView.setAdapter(mShiftsOnDateAdapter);

        mShiftsOnDateAdapter.setmShiftsOnDateFragment(this);

        mScheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        // retrieve relevant date
        long date = EditScheduleFragment.date;
        Calendar day = Converters.fromTimestamp(date);
        int year = day.get(day.YEAR);
        int dayOfWeek = day.get(day.DAY_OF_WEEK) - 1;
        int dayOfMonth = day.get(day.DAY_OF_MONTH);
        int month = day.get(day.MONTH) + 1;
        System.out.println("shiftDATE: " + year + '-' + month + '-' + dayOfMonth + (day==day));

        //Update recyclerview with current Database information:

        mScheduleViewModel.getShiftOnDate(day).observe(getActivity(), new Observer<List<JoinShiftDateInfo>>() {
            @Override
            public void onChanged(@Nullable final List<JoinShiftDateInfo> availableOnDay) {
                mShiftsOnDateAdapter.setShiftsOnDay(availableOnDay);
                System.out.println("Shifts on date frag: " + availableOnDay.size());
            }
        });

    }
    public void removeFromSchedule (ShiftTable removeFromSchedule) {
        // Pass ShiftTable to remove here
        mScheduleViewModel.removeShift(removeFromSchedule);
    }



}
