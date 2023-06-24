package com.app.borgapplication.database.impl;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShiftDao {
    @Insert
    public void insert(ShiftTable shift);

    @Delete
    public void delete(ShiftTable shift);


    @Query(" SELECT * " +
            "FROM date_table " +
            "WHERE date_table.date_id NOT IN" +
                "(SELECT date_table.date_id " +
                "FROM date_table " +
                "JOIN shift_table " +
                "WHERE date_table.date_id = shift_table.date_id)" +
            "AND dateEpoch BETWEEN :startDate AND :endDate " +
            "ORDER BY dateEpoch ASC")
    LiveData<List<DateTable>> getDaysWithNoShifts(long startDate, long endDate);

    @Query("SELECT yearNumber, dayNumber, Month_Name, first_name || ' ' || last_name as employeeName, shift_type  "  +
            "FROM ( " +
            "SELECT e.shiftId, e.date_id, yearNumber, monthNumber, dayNumber, shift_type," +
            "e.training_status, dateString, Month_Name, first_name, last_name " +
            "FROM  " +
            "( SELECT shiftId, first_name, last_name, a.employee_id, training_status, shift_type, date_id " +
            "FROM (" +
            "SELECT first_name, last_name, employee_roster.employee_id, training_status " +
            "FROM  employee_roster " +
            "JOIN employee_training " +
            "WHERE employee_roster.employee_id = employee_training.employee_id ) as a\n" +
            "JOIN shift_table " +
            "WHERE a.employee_id = shift_table.employee_id ) as e " +
            "JOIN date_table " +
            "WHERE e.date_id = date_table.date_id AND dateEpoch BETWEEN :startDate AND :endDate " +
            "ORDER BY dateEpoch ASC) ")
    LiveData<List<JoinMonthlyShiftInfo>> getMonthlySchedule(long startDate, long endDate);


    @Query("SELECT e.employee_id, e.first_name, e.last_name " +
            "FROM (" +
            " SELECT employee_roster.employee_id, first_name, last_name, " +
            "group_concat(availability, ', ') AS availability " +
            "FROM employee_roster " +
            "INNER JOIN employee_availability " +
            "WHERE employee_roster.employee_id = employee_availability.employee_id " +
            "GROUP BY employee_roster.employee_id) as e " +
            "WHERE e.employee_id NOT IN(" +
                "SELECT employee_id " +
                "FROM ( " +
                    "SELECT b.shiftId, b.date_id, b.employee_id, yearNumber, monthNumber, dayNumber, shift_type, training_status " +
                    "FROM "+
                    "( SELECT shiftId, first_name, last_name, a.employee_id, training_status, shift_type, date_id " +
                    "FROM ( " +
                        "SELECT first_name, last_name, employee_roster.employee_id, training_status " +
                        "FROM  employee_roster "+
                        "JOIN employee_training "+
                        "WHERE employee_roster.employee_id = employee_training.employee_id ) as a "+
                        "JOIN shift_table "+
                        "WHERE a.employee_id = shift_table.employee_id) as b " +
            "JOIN date_table " +
            "WHERE b.date_id = date_table.date_id AND dateEpoch BETWEEN :startDate AND :endDate))"
            )
    LiveData<List<JoinEmployeesLeftBehind>> getEmployeesNotScheduled(long startDate, long endDate);

    @Query("SELECT date_id, COUNT(shiftID) as no_of_shifts, group_concat(training_status, \", \") as trained, " +
            "dayNumber, monthNumber, yearNumber, dateString "  +
            "FROM ( " +
            "SELECT e.shiftId, e.date_id, yearNumber, monthNumber, dayNumber, shift_type," +
            "e.training_status, dateString " +
            "FROM  " +
            "( SELECT shiftId, first_name, last_name, a.employee_id, training_status, shift_type, date_id " +
            "FROM (" +
            "SELECT first_name, last_name, employee_roster.employee_id, training_status " +
            "FROM  employee_roster " +
            "JOIN employee_training " +
            "WHERE employee_roster.employee_id = employee_training.employee_id ) as a\n" +
            "JOIN shift_table " +
            "WHERE a.employee_id = shift_table.employee_id and shift_type=:shiftType ) as e " +
            "JOIN date_table " +
            "WHERE e.date_id = date_table.date_id AND dateEpoch BETWEEN :startDate AND :endDate ) " +
            "GROUP BY date_id ")
    LiveData<List<JoinShiftReport>>getShiftReport(long startDate, long endDate, String shiftType);

    @Query("SELECT e.shiftId, e.date_id, e.employee_id, yearNumber, monthNumber, dayNumber, shift_type, first_name, " +
            "last_name, dateObj, e.training_status " +
            "FROM  " +
            "( SELECT shiftId, first_name, last_name, a.employee_id, training_status, shift_type, date_id\n" +
            "FROM (" +
            "SELECT first_name, last_name, employee_roster.employee_id, training_status\n" +
            "FROM  employee_roster " +
            "JOIN employee_training " +
            "WHERE employee_roster.employee_id = employee_training.employee_id ) as a\n" +
            "JOIN shift_table " +
            "WHERE a.employee_id = shift_table.employee_id ) as e \n" +
            "JOIN date_table " +
            "WHERE e.date_id = date_table.date_id AND yearNumber=:year AND monthNumber=:month AND dayNumber=:day\n" +
            "ORDER BY shift_type DESC")
    LiveData<List<JoinShiftDateInfo>>getShiftOnDate(int year, int month, int day);
}
