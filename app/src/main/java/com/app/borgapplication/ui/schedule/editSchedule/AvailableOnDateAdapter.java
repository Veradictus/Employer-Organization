package com.app.borgapplication.ui.schedule.editSchedule;

import android.app.Dialog;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinEmployeeInformation;
import com.app.borgapplication.ui.employee.EmployeeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AvailableOnDateAdapter extends RecyclerView.Adapter<AvailableOnDateAdapter.AvailableViewHolder> {
    private int mExpandedPosition = -1;
    private EditScheduleFragment mEditScheduleFragment;
    private List<JoinDailyAvailable> mAvailableOnDay; //cached copy of Joined Daily available employees
    private final LayoutInflater mInflater;
    private Fragment ctx;
    final Dialog dialog = new Dialog(EditScheduleFragment.getContextOfApplication());

    AvailableOnDateAdapter(Context context, Fragment mEditSchedule) {
        mInflater = LayoutInflater.from(context);
        this.ctx = mEditSchedule;
    }

    @NonNull
    @Override
    public AvailableOnDateAdapter.AvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.available_list_item, parent, false);
        return new AvailableViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableViewHolder holder, int position) {
        // POPULATE AVAILABLE LIST >>>>>>>>>>>>>>>>>>>>>
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandToButton.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            mExpandedPosition = position;

        // Expandable List view
        holder.availListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyItemChanged(mExpandedPosition);
                notifyItemChanged(position);

                // Fun animation effect does not work on correct view TODO: Find out how to make it :)
                // toggleLayout(isExpanded, v.getRootView().findViewById(R.id.viewMoreBtn));
            }
        });

        if(mAvailableOnDay != null) {
            JoinDailyAvailable currentEmployee = mAvailableOnDay.get(position);

            holder.employeeItemView.setText(currentEmployee.getFirst_name() + ' ' + currentEmployee.getLast_name());
            holder.employeeTrainingView.setText("\n\nTraining: " + currentEmployee.getTraining_status() + "   " +
                        "AVAILABLE: " + currentEmployee.getAvailability());

            // Insert to Schedule onClick -----------------------------------------
            holder.scheduleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create an instance of the dialog fragment and show it
                    ScheduleDialog dialog = new ScheduleDialog();
                    // pass dialogue info...
                    ArrayList<Integer> empInfo = new ArrayList<Integer>();
                    empInfo.add(currentEmployee.getEmployee_id());
                    empInfo.add(currentEmployee.getDate_id());

                    Bundle bundle = new Bundle();
                    //----Insert Info to bundle---///
                    bundle.putIntegerArrayList("employee_info",empInfo);
                    bundle.putString("availability", currentEmployee.getAvailability());
                    dialog.setArguments(bundle);
                    dialog.show(ctx.getParentFragmentManager(), "Schedule");
                }
            });

        }
    }

    public void setAvailableEmployees(List<JoinDailyAvailable> available) {
        mAvailableOnDay = available;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // IF RETURN NULL HANDLE HERE
        if (mAvailableOnDay != null)
            return mAvailableOnDay.size();
        else return 0;
    }

    public class AvailableViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeItemView;
        private final CardView availListItem;
        private final TextView employeeTrainingView;
        private final Button scheduleButton;
        private final ConstraintLayout expandToButton;
        public AvailableViewHolder(@NonNull View itemView) {
            super(itemView);
            availListItem = itemView.findViewById(R.id.avail_list_item);
            scheduleButton = itemView.findViewById(R.id.schedule_employee);
            employeeItemView = itemView.findViewById(R.id.employee_schedule_name);
            employeeTrainingView = itemView.findViewById(R.id.training_status);
            expandToButton = itemView.findViewById(R.id.expanded_schedule);

        }
    }
}
