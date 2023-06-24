package com.app.borgapplication.ui.schedule.viewSchedule;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.app.borgapplication.R;
import com.app.borgapplication.returnCalander.Schedule;
import com.app.borgapplication.ui.employee.EmployeeViewModel;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;
import com.app.borgapplication.utils.PDFTester;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;

public class ScheduleViewFragment extends Fragment {
    Button editScheduleBtn;
    private View scheduleView;
    private ScheduleViewModel scheduleViewModel;
    private CalendarFragment calendarFragment;
    private long date;
    private long selectedDate =  Calendar.getInstance().getTimeInMillis();

    public ScheduleViewFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        scheduleView = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);

        scheduleViewModel.getCurrentDate().observe(getViewLifecycleOwner(), date ->{
           date = scheduleViewModel.getCurrentDate().getValue();
        });

        // ----------------------------------------------------- TO EDIT SCHEDULE
        editScheduleBtn = scheduleView.findViewById(R.id.edit_schedule);
        editScheduleBtn.setOnClickListener( view -> {
            scheduleViewModel.getCurrentDate().observe(getViewLifecycleOwner(), date -> {
                date = scheduleViewModel.getCurrentDate().getValue();
                Bundle bundle = new Bundle();
                bundle.putLong("date", date);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.editScheduleFragment, bundle);

                //updates date to selected date
                selectedDate = date;
            });
        });

        calendarFragment = new CalendarFragment();
        //send selected date to calendar fragment
        Bundle dateBundle = new Bundle();
        dateBundle.putLong("selectedDate", selectedDate);
        calendarFragment.setArguments(dateBundle);

        ShiftInformationFragment shiftInformationFragment = new ShiftInformationFragment();

        shiftInformationFragment.setCalendarView(calendarFragment.getCalendarView());

        getParentFragmentManager().beginTransaction()
                .remove(shiftInformationFragment)
                .replace(R.id.container_calendar, calendarFragment)
                .replace(R.id.schedule_list, shiftInformationFragment)
                .commit();

        setHasOptionsMenu(true);

        return scheduleView;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_employee_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_button:
                scheduleViewModel.getCurrentDate().observe(getViewLifecycleOwner(), date -> {
                    Bundle bundle = new Bundle();
                    bundle.putLong("currentDate", date);

                    NavController navController = Navigation.findNavController(this.getView());
                    navController.navigate(R.id.reportFragment, bundle);

                });
                return super.onOptionsItemSelected(item);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
//        Snackbar mySnackbar;

//        switch (item.getItemId()) {
//            case R.id.save_button:
//
//                if (!calendarFragment.dateSelected()) {
//                    mySnackbar = Snackbar.make(this.scheduleView, "Please make changes to the Calendar before saving", Snackbar.LENGTH_SHORT);
//                    mySnackbar.show();
//                    return false;
//                }
//
//                PDFTester pdf = new PDFTester(calendarFragment.getSelectedMonth(), calendarFragment.getSelectedYear());
//                Schedule schedule = new Schedule(pdf.getTestMonth());
//
//                try {
//                    schedule.makeSchedule();
//
//                    mySnackbar = Snackbar.make(this.scheduleView, "Schedule saved to Downloads", Snackbar.LENGTH_SHORT);
//                    mySnackbar.show();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }

