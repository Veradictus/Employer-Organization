package com.app.borgapplication.database.impl;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Calendar;
@Entity(tableName = "date_table")
public class DateTable {

    @PrimaryKey(autoGenerate = true)
    public int date_id;
    @ColumnInfo(name="dateObj")
    public Calendar date;
    @ColumnInfo(name="dateEpoch")
    public long dateEpoch;
    @ColumnInfo(name="dateString")
    public String dateString;
    @ColumnInfo(name="yearNumber")
    public int year;
    @ColumnInfo(name="monthNumber")
    public int month;
    @ColumnInfo(name="dayNumber")
    public int day;
    @ColumnInfo(name="dayOfWeek")
    public int dayOfWeek;
    @ColumnInfo(name="Month_Name")
    public String monthName;
    @ColumnInfo(name="Day_Name")
    public String dayName;

    // ----------- Constructor

    public DateTable(Calendar date, long dateEpoch, String dateString, int year, int month,
                     int day, int dayOfWeek, String monthName, String dayName) {
        this.date = date; //Calendar object
        this.dateEpoch = dateEpoch;
        this.dateString = dateString;
        this.year = year;
        this.month = month;
        this.day = day;
        this.monthName = monthName;
        this.dayName = dayName;
        this.dayOfWeek = dayOfWeek;
    }

    // ---------------- Getters & Setters


    public long getDateEpoch() {
        return dateEpoch;
    }

    public void setDateEpoch(long dateEpoch) {
        this.dateEpoch = dateEpoch;
    }

    public int getDateId() {
        return date_id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getYear() {
        return year;
    }
    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }


    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
