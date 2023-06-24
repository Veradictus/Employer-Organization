package com.app.borgapplication.database.impl;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Calendar;
import java.util.List;

@Dao
public interface DateDao {

    @Insert
    void insert(DateTable date);

    //Display start date to end date of table
    @Query("SELECT * FROM date_table ")
    List<DateTable> startToEnd();

}
