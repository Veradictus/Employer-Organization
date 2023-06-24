package com.app.borgapplication;

import android.os.Build;
import android.os.Bundle;

import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.returnCalander.Schedule;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;
import com.app.borgapplication.ui.schedule.editSchedule.EditScheduleFragment;
import com.app.borgapplication.ui.schedule.editSchedule.ScheduleDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ScheduleDialog.ScheduleDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    private EditScheduleFragment mEditScheduleFragment;
    ScheduleViewModel scheduleViewModel;
    private ArrayList<String> toSchedule;

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ScheduleDialog();
        dialog.show(getSupportFragmentManager(), "ScheduleDialog");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Bundle bundle) {
        // User touched the dialog's positive button
        System.out.println(bundle);
        String shift_type = new String();
        int employee_id = bundle.getIntegerArrayList("employee_info").get(0);
        int date_id = bundle.getIntegerArrayList("employee_info").get(1);
        int hours;

        if (bundle.getStringArrayList("toSchedule").size() != 0) {
            if (bundle.getStringArrayList("toSchedule").size() > 1) {
                shift_type = "All Day";
                hours = 12;
            } else {
                shift_type = bundle.getStringArrayList("toSchedule").get(0);
                hours = 6;
            }
            ShiftTable newShift = new ShiftTable(employee_id, date_id, shift_type, hours);
            scheduleViewModel.setToSchedule(newShift);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        dialog.notify();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the main_activity page up
        setContentView(R.layout.activity_main);
        scheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_schedule)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}