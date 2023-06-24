package com.app.borgapplication.database.impl;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "employee_availability",
        foreignKeys = @ForeignKey(entity = EmployeeTable.class,
        parentColumns = "employee_id",
        childColumns = "employee_id",
        onDelete = ForeignKey.CASCADE))

public class AvailabilityTable {


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name= "employee_id")
    public int employee_id;

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    @NonNull
    @ColumnInfo(name= "dayNumber")
    private int dayNumber;

    @NonNull
    @ColumnInfo(name= "availability", defaultValue = "Off")
    private String availability;

    public AvailabilityTable(int employee_id, @NonNull int dayNumber) {
        this.employee_id = employee_id;
        this.dayNumber = dayNumber;
        this.availability = randomAvailability();
    }

    @Ignore
    public AvailabilityTable(int employee_id, @NonNull int dayNumber, String availability) {
        this.employee_id = employee_id;
        this.dayNumber = dayNumber;
        this.availability = availability;
    }

    @Ignore
    public AvailabilityTable(int id, int employee_id, @NonNull int dayNumber, @NonNull String availability) {
        this.id=id;
        this.employee_id = employee_id;
        this.dayNumber = dayNumber;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayName(@NonNull int dayNumber) {
        this.dayNumber = dayNumber;
    }

    @NonNull
    public String getAvailability() {
        return availability;
    }

    public void setAvailability(@NonNull String availability) {
        this.availability = availability;
    }

    private String randomAvailability() {
        String[] randomStringArray = new String[] {"Off", "Both", "Evening", "Day"};
        String randomString;
        Random random = new Random();
        randomString = randomStringArray[(random.nextInt(3))];
        return randomString;
    }

}

