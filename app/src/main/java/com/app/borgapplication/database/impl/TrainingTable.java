package com.app.borgapplication.database.impl;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee_training",
        foreignKeys = @ForeignKey(entity = EmployeeTable.class,
                parentColumns = "employee_id",
                childColumns = "employee_id",
                onDelete = ForeignKey.CASCADE))
public class TrainingTable {
    @PrimaryKey
    public int employee_id;
    @ColumnInfo(name="training_status", defaultValue = "None")
    public String training;

    public TrainingTable(int employee_id, String training) {
        this.employee_id = employee_id;
        this.training = training;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }
}
