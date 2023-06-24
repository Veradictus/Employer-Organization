package com.app.borgapplication.utils;

import java.util.ArrayList;

public class DayInfo {
    public ArrayList<ShiftInfo> dayShifts;

    public DayInfo() {
        dayShifts = new ArrayList<>();
    }

    public void fillShift(String employeeName, String shift) {
        this.dayShifts.add(new ShiftInfo(employeeName, shift));
    }
}

