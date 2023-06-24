package com.app.borgapplication.database.impl;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Shift_Table",
        foreignKeys = {
        @ForeignKey(entity = EmployeeTable.class,
                parentColumns = "employee_id",
                childColumns = "employee_id",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = DateTable.class,
                parentColumns = "date_id",
                childColumns = "date_id")
                        }
        )
public class ShiftTable {
    @PrimaryKey(autoGenerate = true)
    int shiftId;

    @ColumnInfo(name = "employee_id")
    int employee_id;

    @ColumnInfo(name = "date_id")
    int date_id;

    @ColumnInfo(name = "shift_type")
    String shift_type;

    @ColumnInfo(name = "shift_length")
    int shift_length;

    @Ignore
    public ShiftTable(int shiftId) {
        this.shiftId = shiftId;
    }

    public ShiftTable(int employee_id, int date_id, String shift_type, int shift_length) {
        this.employee_id = employee_id;
        this.date_id = date_id;
        this.shift_type = shift_type;
        this.shift_length = shift_length;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }

    public int getShift_length() {
        return shift_length;
    }

    public void setShift_length(int shift_length) {
        this.shift_length = shift_length;
    }
}
