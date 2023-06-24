package com.app.borgapplication.database.impl;

import androidx.room.Entity;


public class JoinDailyAvailable {

    private String last_name;
    private String dayOfWeek;
    private String availability;
    private String training_status;
    private String dateString;
    private int date_id;
    private int monthNumber;
    private int dayNumber;
    private int yearNumber;
    private int employee_id;
    private String first_name;

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public String getTraining_status() {
        return training_status;
    }

    public void setTraining_status(String training_status) {
        this.training_status = training_status;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employeeId) {
        this.employee_id = employeeId;
    }

}
