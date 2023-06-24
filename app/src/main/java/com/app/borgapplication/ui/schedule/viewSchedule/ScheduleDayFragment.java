package com.app.borgapplication.ui.schedule.viewSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.borgapplication.R;
import org.jetbrains.annotations.NotNull;

public class ScheduleDayFragment extends Fragment {

    private View view;

    private TextView label;
    private TextView dateText;

    private int year;
    private int month;
    private int dayOfMonth;

    public ScheduleDayFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_nav_day, container, false);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dateText = getView().findViewById(R.id.employee_name);

        setYear(getArguments().getInt("year"));
        setMonth(getArguments().getInt("month"));
        setDayOfMonth(getArguments().getInt("dayOfMonth"));

        updateLabel();
    }

    public void updateLabel() {
        dateText.setText(getYear() + "-" + getMonth() + "-" + getDayOfMonth());
    }



    /**
     * Getters
     */

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Setters
     */

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    private void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
