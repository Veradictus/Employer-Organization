package com.app.borgapplication.database.impl;

public class JoinShiftReport {

    int date_id;
    int no_of_shifts;
    int dayNumber;
    int monthNumber;
    int yearNumber;
    String trained;
    String dateString;


    public String getDate_String() {
        return dateString;
    }

    public void setDate_String(String date_String) {
        this.dateString = date_String;
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

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public int getNo_of_shifts() {
        return no_of_shifts;
    }

    public void setNo_of_shifts(int no_of_shifts) {
        this.no_of_shifts = no_of_shifts;
    }

    public String getTrained() {
        return trained;
    }

    public void setTrained(String trained) {
        this.trained = trained;
    }
}
