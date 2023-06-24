package com.app.borgapplication.ui.schedule.reportSchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.DateTable;

import java.util.List;

public class DaysNotScheduledAdapter extends RecyclerView.Adapter<DaysNotScheduledAdapter.DaysNotScheduledViewHolder> {
    private List<DateTable> mNotScheduled;
    private final LayoutInflater mInflater;

    public DaysNotScheduledAdapter.DaysNotScheduledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.days_not_scheduled_list_item, parent, false);
        return new DaysNotScheduledViewHolder(itemView);
    }

    DaysNotScheduledAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysNotScheduledViewHolder holder, int position) {

        if(mNotScheduled!=null) {

            DateTable currentDate = mNotScheduled.get(position);
            holder.curDate.setText(currentDate.dateString);


        }

    }

    public void setDaysNoShift(List<DateTable> daysNoShift) {
        mNotScheduled = daysNoShift;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotScheduled != null)
            return mNotScheduled.size();
        else return 0;
    }

    public class DaysNotScheduledViewHolder extends RecyclerView.ViewHolder {

        private final TextView curDate;

        public DaysNotScheduledViewHolder(@NonNull View itemView){

            super(itemView);
            curDate = itemView.findViewById(R.id.tvNotDATE);
        }

    }



}
