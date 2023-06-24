package com.app.borgapplication.database.impl;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import static androidx.room.OnConflictStrategy.*;

@Dao
public interface EmployeeDao {

    @Insert(onConflict = IGNORE)
    void insert(EmployeeTable employee);

    @Query("DELETE FROM employee_roster")
    void deleteAll();

    @Update
    void updateEmployee(EmployeeTable employee);

    @Delete
    void deleteEmployee(EmployeeTable employee);

    @Query("SELECT * FROM employee_roster\n" +
            "ORDER BY last_name ASC")
    LiveData<List<EmployeeTable>> getAlphabetizedLastNames();

    @Query("SELECT * from employee_roster where employee_id =:primaryKey")
    LiveData<List<EmployeeTable>> findEmployeeById(int primaryKey);

    @Query("SELECT * FROM employee_roster WHERE email =:email")
    LiveData<List<EmployeeTable>> getEmployeeByEmail(String email);

    @Query("SELECT employee_id FROM employee_roster where email=:eMail")
    int getEmployeeId(String eMail);

   @Query("SELECT e.employee_id, e.first_name, e.last_name, e.availability, monthNumber, e.training_status," +
           "dateString, yearNumber, date_table.dayNumber, date_table.date_id \n" +
           "FROM  (" +
           "SELECT employee_info.employee_id, first_name, last_name, availability," +
                    "dayNumber, training_status \n" +
           "FROM (" +
           "           SELECT employee_roster.employee_id, employee_training.training_status," +
           "           first_name, last_name, email\n" +
           "           FROM employee_roster JOIN employee_training\n" +
           "           WHERE employee_roster.employee_id = employee_training.employee_id) as employee_info\n" +
           "JOIN employee_availability as a\n" +
           "WHERE employee_info.employee_id = a.employee_id AND a.dayNumber=:dayOfWeek) as e\n" +
          " JOIN date_table\n" +
           "WHERE monthNumber=:month AND yearNumber=:yearNumber AND date_table.dayNumber=:checkDay AND e.availability != \"Off\" AND " +
           "NOT EXISTS(SELECT 1 FROM Shift_Table WHERE e.employee_id == Shift_table.employee_id AND date_table.date_id == Shift_Table.date_id) " +
           "ORDER BY last_name ASC" )
   LiveData<List<JoinDailyAvailable>> dailyAvailability(int dayOfWeek, int checkDay, int month, int yearNumber);


// e.dayNumber=:checkDay = date_table.dayOfWeek
   // Should return 7 objects, one for each day of week
    @Query("SELECT employee_roster.employee_id, first_name, last_name, " +
            "group_concat(availability, ', ') AS availability, " +
            "group_concat(dayNumber, ', ') AS dayNumber, " +
            "employee_roster.email " +
            "FROM employee_roster\n" +
            "INNER JOIN employee_availability\n " +
            "WHERE employee_roster.employee_id = employee_availability.employee_id\n" +
            "GROUP BY employee_roster.employee_id\n" +
            "ORDER BY last_name ASC")
    LiveData<List<JoinEmployeeInformation>> getEmployeeAvailability ();

    @Query("SELECT employee_info.employee_id, employee_info.training_status,\n" +
            "first_name, last_name, " +
            "group_concat(availability, ', ') AS availability, " +
            "group_concat(dayNumber, ', ') AS dayNumber, " +
            "employee_info.email " +
            "FROM " +
            "(SELECT employee_roster.employee_id, employee_training.training_status," +
            "first_name, last_name, email\n" +
            "FROM employee_roster JOIN employee_training\n" +
            "WHERE employee_roster.employee_id = employee_training.employee_id) as employee_info\n" +
            "INNER JOIN employee_availability\n " +
            "WHERE employee_info.employee_id = employee_availability.employee_id\n"  +
            "GROUP BY employee_info.employee_id\n" +
            "ORDER BY last_name ASC")
    LiveData<List<JoinEmployeeInformation>> getEmployeeInformation ();

    // List of who is available on what day and their training status
//    @Query("")

//    @Query("SELECT employee_roster.employee_id, first_name, last_name, availability, dayNumber FROM employee_roster\n" +
//            "INNER JOIN employee_availability WHERE employee_roster.employee_id = employee_availability.employee_id\n" +
//            "ORDER BY last_name ASC")
}