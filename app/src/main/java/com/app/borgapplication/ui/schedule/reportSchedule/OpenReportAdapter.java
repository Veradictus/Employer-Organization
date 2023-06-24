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

public class OpenReportAdapter extends RecyclerView.Adapter<OpenReportAdapter.OpenReportViewHolder> {

    /* Get all shifts from start date to end date, that do not have one trained
     *  employee on Open and Close */
    private final LayoutInflater mInflater;
    private List<JoinShiftReport> mOpenShiftReport;

    OpenReportAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public OpenReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.openshift_report_list_item, parent, false);
        return new OpenReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OpenReportViewHolder holder, int position) {

        if (mOpenShiftReport != null) {
            JoinShiftReport currentShift = mOpenShiftReport.get(position);

            holder.curDate.setText(currentShift.getDate_String());


        }
    }

    public void setOpenShiftReport(List<JoinShiftReport> openShiftReport) {
        mOpenShiftReport = openShiftReport;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mOpenShiftReport != null) {
            return mOpenShiftReport.size();
        } else
            return 0;
    }


    public class OpenReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView curDate;

        public OpenReportViewHolder(@NonNull View itemView) {
            super(itemView);
            curDate = itemView.findViewById(R.id.tvOpenDate);
        }

    }

}
