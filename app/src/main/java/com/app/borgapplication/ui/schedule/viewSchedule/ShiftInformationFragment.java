package com.app.borgapplication.ui.schedule.viewSchedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;
import com.app.borgapplication.ui.schedule.viewSchedule.ShiftInformationAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ShiftInformationFragment extends Fragment {
    private CalendarView calendarView;

    private Button btnEdit;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ScheduleViewModel mScheduleViewModel;
    private ShiftInformationAdapter adapter;
    private long date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_shift_information, container, false);
        mScheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        //assign variables
        recyclerView = v.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ShiftInformationAdapter(this.getActivity());
        recyclerView.setAdapter(adapter);

        mScheduleViewModel.getCurrentDate().observe(getViewLifecycleOwner(), date -> {
            date = mScheduleViewModel.getCurrentDate().getValue();
            Calendar day = Converters.fromTimestamp(date);
            mScheduleViewModel.getShiftOnDate(day).observe(getActivity(), new Observer<List<JoinShiftDateInfo>>() {
                @Override
                public void onChanged(List<JoinShiftDateInfo> joinShiftDateInfo) {
                    adapter.setShiftsOnDay(joinShiftDateInfo);
                }
            });

        });

        return v;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
