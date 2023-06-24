package com.app.borgapplication.ui.schedule.viewSchedule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.ui.schedule.editSchedule.ShiftsOnDateAdapter;
import com.app.borgapplication.ui.schedule.editSchedule.ShiftsOnDateFragment;

import java.util.List;

public class ShiftInformationAdapter extends RecyclerView.Adapter<ShiftInformationAdapter.ViewHolder> {
    //initialize variables
    private List<JoinShiftDateInfo> mShiftsOnDay; //cached copy of Joined Daily available employees
    private final LayoutInflater mInflater;
    private Activity context;

    ShiftInformationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

//    public TodayViewHolder(View itemView) {
//        private final TextView availableToday;
//    })

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_information_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mShiftsOnDay != null) {
            JoinShiftDateInfo currentEmployee = mShiftsOnDay.get(position);
            holder.textViewShift.setText(currentEmployee.getShift_type());
            holder.textView.setText(currentEmployee.getFirst_name() + " " + currentEmployee.getLast_name());
        }

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewShift;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewShift = itemView.findViewById(R.id.text_view_shift);
            textView = itemView.findViewById(R.id.text_view);

        }
    }

}
