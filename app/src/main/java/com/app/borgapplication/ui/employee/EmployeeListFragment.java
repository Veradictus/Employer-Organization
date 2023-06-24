package com.app.borgapplication.ui.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.borgapplication.R;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.impl.JoinEmployeeInformation;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class EmployeeListFragment extends Fragment {

    private RecyclerView recyclerView;
    private EmployeeListAdapter mEmployeeListAdapter;
    private EmployeeViewModel mEmployeeViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private View view;


    public EmployeeListFragment() {
        //Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        setHasOptionsMenu(true);
        
        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_employee:
                NavController navController = Navigation.findNavController(this.view);
                navController.navigate(R.id.nav_add_employee);                        //Change to add_employee

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //rename asset to something better ...
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        mEmployeeListAdapter = new EmployeeListAdapter(this.getActivity());
        recyclerView.setAdapter(mEmployeeListAdapter);

        mEmployeeViewModel = new ViewModelProvider(getActivity()).get(EmployeeViewModel.class);

        mEmployeeListAdapter.setEmployeeListFragment(this);

        //Update recyclerview with current Database information ViewModel -> Repository -> Dao
        mEmployeeViewModel.getEmployeeInformation().observe(getActivity(), new Observer<List<JoinEmployeeInformation>>() {
            @Override
            public void onChanged(@Nullable final List<JoinEmployeeInformation> employees) {
                mEmployeeListAdapter.setEmployees(employees);
            }
        });

    }

    public void delete(int employeeId) {
        mEmployeeViewModel.findEmployeeById(employeeId).observe(getActivity(), new Observer<List<EmployeeTable>>() {
            @Override
            public void onChanged(List<EmployeeTable> employeeTables) {
                for (EmployeeTable employeeTable : employeeTables)
                    if (employeeTable.getId() == employeeId)
                        mEmployeeViewModel.delete(employeeTable);
            }
        });
    }
}
