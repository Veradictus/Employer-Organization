package com.app.borgapplication.ui.employee;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinEmployeeInformation;
import com.app.borgapplication.ui.Animation;
import com.app.borgapplication.database.impl.AvailabilityTable;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder> {
    private int mExpandedPosition = -1;
    private EmployeeListFragment employeeListFragment;
    private List<JoinEmployeeInformation> mEmployees; //cached copy of Employees
    List<AvailabilityTable> currentAvail;

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeItemView;
        private final ImageButton viewMoreBtn;
        private final Button editEmployeeButton;
        private final Button deleteEmployeeButton;
        private final TextView employeeEmailView;
        private final TextView employeeAvailabilityView;
        private final TextView employeeTrainingView;
        private final ConstraintLayout expandEmployeeInfo;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            editEmployeeButton = itemView.findViewById(R.id.edit_employee);
            deleteEmployeeButton = itemView.findViewById(R.id.delete_employee);
            expandEmployeeInfo = itemView.findViewById(R.id.expanded_info);
            employeeItemView = itemView.findViewById(R.id.tvEmployeeName);
            employeeEmailView = itemView.findViewById(R.id.tvEmployeeContact);
            employeeAvailabilityView = itemView.findViewById(R.id.replace_with_availability);
            employeeTrainingView = itemView.findViewById(R.id.training_status);
            viewMoreBtn = itemView.findViewById(R.id.viewMoreBtn);
        }
    }

    private final LayoutInflater mInflater;

    EmployeeListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public EmployeeListAdapter.EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.employee_list_item, parent, false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandEmployeeInfo.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            mExpandedPosition = position;

        // Expandable List view
        holder.employeeItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(mExpandedPosition);
                notifyItemChanged(position);

                // Fun animation effect does not work on correct view TODO: Find out how to make it :)
                // toggleLayout(isExpanded, v.getRootView().findViewById(R.id.viewMoreBtn));
            }
        });

        //Edit Employee button goes to EditEmployeeFragment
        holder.editEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int employeeId = mEmployees.get(position).getEmployee_id();
                Bundle bundle = new Bundle();
                bundle.putInt("employee_id", employeeId);
                bundle.putString("training_status", mEmployees.get(position).getTraining_status());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.nav_edit_employee, bundle);
            }
        });

        // Remove Employee from Roster
        holder.deleteEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int employeeId = mEmployees.get(position).getEmployee_id();

                if (employeeListFragment != null)
                    employeeListFragment.delete(employeeId);

                Snackbar mySnackbar = Snackbar.make(v, "Employee deleted", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });

        if (mEmployees != null) {
            try {
                JoinEmployeeInformation currentEmployee = mEmployees.get(position);

                holder.employeeItemView.setText(currentEmployee.getFirst_name() + ' ' + currentEmployee.getLast_name());
                holder.employeeEmailView.setText(currentEmployee.getEmail());
                holder.employeeTrainingView.setText("Training Status:\n" + currentEmployee.getTraining_status());

                String[] daysOfWeek = currentEmployee.getDayNumber().split("[ ,]+");
                String[] availabilityStatus = currentEmployee.getAvailability().split(",");

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < daysOfWeek.length; i++) {
                    stringBuilder.append(getDay(Integer.parseInt(daysOfWeek[i])));
                    stringBuilder.append(": ");
                    stringBuilder.append(availabilityStatus[i]);

                    if (i != daysOfWeek.length - 1)
                        stringBuilder.append('\n');
                }

                holder.employeeAvailabilityView.setText(stringBuilder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Covers the case of data not being ready
            holder.employeeItemView.setText("No word");
        }
    }

    private boolean toggleLayout(boolean isExpanded, View v) {
        Animation.toggleArrow(v, isExpanded);
        return isExpanded;
    }

    public void setEmployees(List<JoinEmployeeInformation> employees) {
        mEmployees = employees;
        notifyDataSetChanged();
    }

    public void setEmployeeListFragment(EmployeeListFragment employeeListFragment) {
        this.employeeListFragment = employeeListFragment;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mEmployees != null)
            return mEmployees.size();
        else return 0;
    }
    public String getDay(int day_of_week) {
        String dayStr;
        switch (day_of_week + 1) {
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


