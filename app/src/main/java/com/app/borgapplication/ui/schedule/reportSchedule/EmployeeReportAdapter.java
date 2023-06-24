package com.app.borgapplication.ui.schedule.reportSchedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinEmployeesLeftBehind;

import java.util.List;

public class EmployeeReportAdapter extends RecyclerView.Adapter<EmployeeReportAdapter.EmployeeReportViewHolder> {

    /*  Get all employees who are not in the Shift Table from start of month to end of
        month at least once per week.
     */
    private final LayoutInflater mInflater;
    private List<JoinEmployeesLeftBehind> mEmployeesNotScheduled;

    EmployeeReportAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EmployeeReportAdapter.EmployeeReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  mInflater.inflate(R.layout.employee_report_list_item, parent, false);
        return new EmployeeReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeReportViewHolder holder, int position) {
        if(mEmployeesNotScheduled!=null){

            JoinEmployeesLeftBehind currentEmployee = mEmployeesNotScheduled.get(position);
            holder.employeeName.setText(currentEmployee.getFirst_name() + ' '
                    + currentEmployee.getLast_name());
        }


    }
    public void setEmployeesLeftBehind(List<JoinEmployeesLeftBehind> employees){
        mEmployeesNotScheduled = employees;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mEmployeesNotScheduled!= null)
            return mEmployeesNotScheduled.size();
        else return 0;
    }

    public class EmployeeReportViewHolder extends RecyclerView.ViewHolder {
            private final TextView employeeName;
        public EmployeeReportViewHolder (@NonNull View itemView){
            super(itemView);
            employeeName = itemView.findViewById(R.id.tvReportEmployeeName);
        }

    }


}
