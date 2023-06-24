package com.app.borgapplication.database.impl;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface TrainingDao {

    @Insert
    void insert(TrainingTable training);

    @Update
    void update(TrainingTable training);

}
