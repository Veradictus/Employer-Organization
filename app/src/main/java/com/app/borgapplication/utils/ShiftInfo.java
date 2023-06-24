package com.app.borgapplication.utils;

import java.util.ArrayList;

public class ShiftInfo {
    public String employeeName;
    public String shift;

    public ShiftInfo(String en, String s) {
        this.employeeName = en;
        this.shift = s;
    }

    public String getEmployeeName() {return this.employeeName;}
    public String getShift() {return this.shift;}
}
