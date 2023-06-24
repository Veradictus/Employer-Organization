package com.app.borgapplication.ui.schedule.reportSchedule;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.DateTable;
import com.app.borgapplication.database.impl.JoinEmployeesLeftBehind;
import com.app.borgapplication.database.impl.JoinMonthlyShiftInfo;
import com.app.borgapplication.database.impl.JoinShiftReport;
import com.app.borgapplication.returnCalander.Schedule;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;
import com.app.borgapplication.utils.CalendarMonth;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReportFragment extends Fragment {

    // Receives the month to be scheduled and Reviews
    // The Manager's currently selected Schedule. Any issues
    // will prevent exporting the schedule

    private RecyclerView employeeReportRecycler;
    private RecyclerView dayNoShiftRecycler;
    private List<JoinShiftReport> mOpenReport;
    private List<JoinShiftReport> mCloseReport;
    private RecyclerView openReportRecycler;
    private RecyclerView closeReportRecycler;
    private DaysNotScheduledAdapter mDayNoShiftAdapter;
    private EmployeeReportAdapter mEmployeeReportAdapter;
    private OpenReportAdapter mOpenReportAdapter;
    private CloseReportAdapter mCloseReportAdapter;
    private RecyclerView.LayoutManager closeReportLayoutManger;
    private RecyclerView.LayoutManager employeeReportLayoutManager;
    private RecyclerView.LayoutManager openReportLayoutManager;
    private RecyclerView.LayoutManager dayNoShiftLayoutManager;
    private View view;
    private ScheduleViewModel mScheduleViewModel;


    public ReportFragment(){
        //Req'd public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        setHasOptionsMenu(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set recyclerviews
        employeeReportRecycler = view.findViewById(R.id.employee_error_recycler);
        openReportRecycler = view.findViewById(R.id.open_error_recycler);
        closeReportRecycler = view.findViewById(R.id.close_error_recycler);
        dayNoShiftRecycler = view.findViewById(R.id.day_no_shift_recycler);


        employeeReportLayoutManager = new LinearLayoutManager(this.getActivity());
        openReportLayoutManager  = new LinearLayoutManager(this.getActivity());
        closeReportLayoutManger = new LinearLayoutManager(this.getActivity());
        dayNoShiftLayoutManager = new LinearLayoutManager(this.getActivity());

        employeeReportRecycler.setLayoutManager(employeeReportLayoutManager);
        openReportRecycler.setLayoutManager(openReportLayoutManager);
        closeReportRecycler.setLayoutManager(closeReportLayoutManger);
        dayNoShiftRecycler.setLayoutManager(dayNoShiftLayoutManager);

        mEmployeeReportAdapter = new EmployeeReportAdapter(this.getActivity());
        employeeReportRecycler.setAdapter(mEmployeeReportAdapter);
        mOpenReportAdapter = new OpenReportAdapter(this.getActivity());
        openReportRecycler.setAdapter(mOpenReportAdapter);
        mCloseReportAdapter = new CloseReportAdapter(this.getActivity());
        closeReportRecycler.setAdapter(mCloseReportAdapter);
        mDayNoShiftAdapter = new DaysNotScheduledAdapter(this.getActivity());
        dayNoShiftRecycler.setAdapter(mDayNoShiftAdapter);

        mScheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);


        Button export = getView().findViewById(R.id.generate_schedule);



        // Set up schedule view model observers to populate RecyclerViews-----------------
        // Get the current month/year (from bundle).....
        long curDate = getArguments().getLong("currentDate");

        //Manipulate to find relevant data:
        Calendar day = Converters.fromTimestamp(curDate);
        int dayOfMonth = 1;
        int month = day.get(day.MONTH);
        int year = day.get(day.YEAR);
        Calendar maniDay = Calendar.getInstance();
        maniDay.set(year, month ,dayOfMonth);
        long startDate = Converters.dateToTimestamp(maniDay);
        int lastDay = maniDay.getActualMaximum(maniDay.DATE);
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(year,month,lastDay);
        long endDate = Converters.dateToTimestamp(lastDate);

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScheduleViewModel.getMonthlyShiftInfo(startDate - 86400000, endDate).observe(getActivity(), new Observer<List<JoinMonthlyShiftInfo>>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onChanged(List<JoinMonthlyShiftInfo> joinMonthlyShiftInfos) {

                        Snackbar mySnackbar;

                        System.out.println("------> " + joinMonthlyShiftInfos.size());

                        if (joinMonthlyShiftInfos.size() != 0) {
                            mySnackbar = Snackbar.make(v, "It may take a moment please be patient", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();

                            int dayOfWeek;
                            Calendar first;
                            first = Converters.fromTimestamp(startDate);

                            //get dayofweek
                            dayOfWeek = first.get(first.DAY_OF_WEEK);

                            // value between 0-6
                            CalendarMonth exportCalendar = new CalendarMonth(lastDay, joinMonthlyShiftInfos.get(0).getMonth_Name(), year, dayOfWeek, joinMonthlyShiftInfos);

                            Schedule schedule = new Schedule(exportCalendar);

                            try {
                                schedule.makeSchedule();

                                mySnackbar = Snackbar.make(v, "Schedule saved to Downloads", Snackbar.LENGTH_SHORT);
                                mySnackbar.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                            mySnackbar = Snackbar.make(v, "Ensure that you fill out the schedule", Snackbar.LENGTH_SHORT);
                            mySnackbar.show();
                        }

                    }
                });



                System.out.println("Export Pressed");





            }
        });

        mScheduleViewModel.getOpenReport(startDate, endDate, "Open").observe(getActivity(),
                new Observer<List<JoinShiftReport>>() {
                    @Override
                    public void onChanged(List<JoinShiftReport> joinShiftReports) {

                        int size = joinShiftReports.size();

                        mOpenReport = new ArrayList<JoinShiftReport>(joinShiftReports);
                        for(int i = 0; i< size; i++) {

                            if (joinShiftReports.get(i).getTrained().contains("Both") ||
                                    joinShiftReports.get(i).getTrained().contains("Open")){
                                mOpenReport.remove(joinShiftReports.get(i));
                            }

                        }
                        mOpenReportAdapter.setOpenShiftReport(mOpenReport);
                    }
                });

        mScheduleViewModel.getCloseReport(startDate, endDate, "Close").observe(getActivity(),
                new Observer<List<JoinShiftReport>>() {
                    @Override
                    public void onChanged(List<JoinShiftReport> joinShiftReports) {

                        int size = joinShiftReports.size();
                        mCloseReport = new ArrayList<JoinShiftReport>(joinShiftReports);

                        for(int i = 0; i< size; i++) {

                            if (joinShiftReports.get(i).getTrained().contains("Both") ||
                                    joinShiftReports.get(i).getTrained().contains("Close")){
                                mCloseReport.remove(joinShiftReports.get(i));
                            }
                        }
                        mCloseReportAdapter.setOpenShiftReport(mCloseReport);
                    }
                });

        mScheduleViewModel.getmEmployeesNotScheduled(startDate, endDate).observe(getActivity(),
                new Observer<List<JoinEmployeesLeftBehind>>() {
                    @Override
                    public void onChanged(List<JoinEmployeesLeftBehind> joinEmployeesLeftBehind) {
                        mEmployeeReportAdapter.setEmployeesLeftBehind(joinEmployeesLeftBehind);
                    }
                });

        mScheduleViewModel.getDaysWithNoShifts(startDate, endDate).observe(getActivity(),
                new Observer<List<DateTable>>() {
                    @Override
                    public void onChanged(List<DateTable> daysNoShifts) {
                        System.out.println(daysNoShifts.size());
                        mDayNoShiftAdapter.setDaysNoShift(daysNoShifts);
                    }
                });



        // Set up schedule view model observers to populate RecyclerViews-----------------



        // FOR BOTH LISTS:
        // Query dates which contains all weeks of year within month (allows for overlap/month)
        // ----Set initial date to be 1st week of month, then find 1st day of that week_of_year
        // ----Set End date to be last day of the last week of month(in context of WEEK_OF_YEAR)

        // EMPLOYEE REPORT
        // --- Join Shift Table
        // --- Compare with Employee_roster (concat employee_id. Group by employee_id)
        // --- If len(query) < Employee_roster
        // --- return employee_id that NOT EXISTS in query as list of Employees left behind.
        // --- will need first_name and last_name.

        // SHIFT REPORT
        // --- Join with Shift Table
        // --- Join with Employee information.
        // --- Verify each shift_type has one Employee with training_status that matches
        // --- Return shifts that do not have a trained employee scheduled.
        // --- Will need the full date information of Shift.

        // If both lists length are 0, then Allow Export Button to be pressed
        // Pass date information to PDF builder ------------------>


    }
}
