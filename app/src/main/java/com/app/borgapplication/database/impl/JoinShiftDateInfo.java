package com.app.borgapplication.database.impl;

import java.util.Calendar;

public class JoinShiftDateInfo {

    int date_id;
    int employee_id;
    int shiftId;
    int dayNumber;
    int monthNumber;
    int yearNumber;
    String training_status;
    String first_name;
    String last_name;
    String shift_type;
    long dateObj;

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getTraining_status() {
        return training_status;
    }

    public void setTraining_status(String training_status) {
        this.training_status = training_status;
    }

    public long getDateObj() {
        return dateObj;
    }

    public void setDateObj(long dateObj) {
        this.dateObj = dateObj;
    }

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }
}
