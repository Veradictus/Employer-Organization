package com.app.borgapplication.database.impl;

public class JoinMonthlyShiftInfo {

    int yearNumber;
    int dayNumber;
    String Month_Name;
    String employeeName;
    String shift_type;

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getMonth_Name() {
        return Month_Name;
    }

    public void setMonth_Name(String month_Name) {
        Month_Name = month_Name;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }

}
