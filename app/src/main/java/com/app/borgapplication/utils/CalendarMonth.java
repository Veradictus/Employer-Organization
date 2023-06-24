package com.app.borgapplication.utils;

import com.app.borgapplication.database.impl.JoinMonthlyShiftInfo;

import java.util.ArrayList;
import java.util.List;

public class CalendarMonth {
    public int numOfDay;
    public String month;
    public int year;
    public int dayOfFirst;
    public List<JoinMonthlyShiftInfo> monthsShifts;


    public CalendarMonth(int nod, String m, int y , int dof, List<JoinMonthlyShiftInfo> data) {


        this.year= y;
        this.month = m;
        this.numOfDay = nod;
        this.dayOfFirst = dof;
        this.monthsShifts = data;
    }

//    public void addDay() {
//        monthsShifts.add(new DayInfo());
//
//    }

//    public void addShift(int dayIndex, String employeeName, String shift){
//        monthsShifts.get(dayIndex).fillShift(employeeName, shift);
//    }
    public String getMonth() {return this.month;}
    public int getYear() {return this.year;}

    public int getNumOfDay() {return this.numOfDay;}

    public int getDayOfFirst() {return this.dayOfFirst;}
}
