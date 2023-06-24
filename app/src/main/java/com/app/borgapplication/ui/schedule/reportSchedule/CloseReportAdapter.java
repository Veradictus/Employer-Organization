package com.app.borgapplication.ui.schedule.reportSchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinShiftReport;

import java.util.List;

public class CloseReportAdapter extends RecyclerView.Adapter<CloseReportAdapter.CloseReportViewHolder> {

    /* Get all shifts from start date to end date, that do not have one trained
     *  employee on Open and Close */
    private final LayoutInflater mInflater;
    private List<JoinShiftReport> mCloseShiftReport;

    CloseReportAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CloseReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.closeshift_report_list_item, parent, false);
        return new CloseReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CloseReportViewHolder holder, int position) {

        if (mCloseShiftReport != null) {

            JoinShiftReport currentShift = mCloseShiftReport.get(position);

            holder.curDate.setText(currentShift.getDate_String());

        }
    }

    public void setOpenShiftReport(List<JoinShiftReport> closeShiftReport) {
        mCloseShiftReport = closeShiftReport;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCloseShiftReport != null) {
            return mCloseShiftReport.size();
        } else
            return 0;
    }


    public class CloseReportViewHolder extends RecyclerView.ViewHolder {

        private final TextView curDate;

        public CloseReportViewHolder(@NonNull View itemView) {

            super(itemView);
            curDate = itemView.findViewById(R.id.tvCloseDate);
        }

    }

}