package com.app.borgapplication.database.impl;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AvailabilityDao {

    @Insert
    void insert(AvailabilityTable employee_availability);

    @Query("DELETE FROM employee_availability")
    void deleteAll();

    @Query("SELECT * FROM employee_availability WHERE employee_id = :id ORDER BY dayNumber ASC")
    LiveData<List<AvailabilityTable>> getAvailabilityById(int id);

    @Update(entity = AvailabilityTable.class)
    void updateAvailability(AvailabilityTable availability);

    //@Query("SELECT * FROM employee_availability WHERE availability LIKE :availability")
    //LiveData<List<AvailabilityTable>> getEmployeeByAvailability(String availability);

    @Query("SELECT * FROM employee_availability")
    LiveData<List<AvailabilityTable>> getAllAvailabilities();

    // List of who is available on what day and their training status
}