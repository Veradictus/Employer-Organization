package com.app.borgapplication.ui.schedule.editSchedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.ui.schedule.ScheduleViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ShiftsOnDateAdapter extends RecyclerView.Adapter<ShiftsOnDateAdapter.ShiftsOnDateViewHolder> {
    private int mExpandedPosition = -1;
    private ShiftsOnDateFragment mShiftsOnDateFragment;
    private List<JoinShiftDateInfo> mShiftsOnDay; //cached copy of Joined Daily available employees
    private final LayoutInflater mInflater;

    ShiftsOnDateAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ShiftsOnDateAdapter.ShiftsOnDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.shifts_on_day_list_item, parent, false);
        return new ShiftsOnDateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftsOnDateViewHolder holder, int position) {
        // POPULATE SHIFTLIST >>>>>>>>>>>>>>>>>>>>>
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandToButton.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            mExpandedPosition = position;

        // Expandable List view
        holder.shiftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(mExpandedPosition);
                notifyItemChanged(position);

                // Fun animation effect does not work on correct view TODO: Find out how to make it :)
                // toggleLayout(isExpanded, v.getRootView().findViewById(R.id.viewMoreBtn));
            }
        });

        if (mShiftsOnDay != null) {
            JoinShiftDateInfo currentEmployee = mShiftsOnDay.get(position);
            holder.shiftType.setText(currentEmployee.getShift_type());
            holder.employeeItemView.setText(currentEmployee.getFirst_name() + ' ' + currentEmployee.getLast_name());
            holder.employeeTrainingView.setText("\nTraining: " + currentEmployee.getTraining_status());


            holder.removeEmployeeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Remove employee from schedule
                    int shift_id = currentEmployee.getShiftId();
                    System.out.println("UIJFIUOAF");
                    ShiftTable shift = new ShiftTable(shift_id);

                    if (mShiftsOnDateFragment != null) {
                        mShiftsOnDateFragment.removeFromSchedule(shift);

                        Snackbar mySnackbar = Snackbar.make(v, currentEmployee.getFirst_name()
                                +' '+ currentEmployee.getLast_name() + " Removed from schedule",
                                Snackbar.LENGTH_SHORT);
                        mySnackbar.show();
                    }
                }
            });
        }


    }

    public void setmShiftsOnDateFragment(ShiftsOnDateFragment mShiftsOnDateFragment) {
        this.mShiftsOnDateFragment = mShiftsOnDateFragment;
    }

    public void setShiftsOnDay(List<JoinShiftDateInfo> shiftsOnDay) {
        mShiftsOnDay = shiftsOnDay;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // IF RETURN NULL HANDLE HERE
        if (mShiftsOnDay != null)
            return mShiftsOnDay.size();
        else return 0;
    }


    public class ShiftsOnDateViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeItemView;
        private final TextView shiftType;
        private final TextView employeeTrainingView;
        private final Button removeEmployeeButton;
        private final ConstraintLayout expandToButton;
        private final CardView shiftView;

        public ShiftsOnDateViewHolder(@NonNull View itemView) {
            super(itemView);
            shiftView = itemView.findViewById(R.id.shiftDay);
            shiftType = itemView.findViewById(R.id.text_view_shift);
            removeEmployeeButton = itemView.findViewById(R.id.remove_schedule_employee);
            employeeItemView = itemView.findViewById(R.id.shift_employee_name);
            employeeTrainingView = itemView.findViewById(R.id.shift_training_status);
            expandToButton = itemView.findViewById(R.id.expanded_shift);

        }

    }
}
