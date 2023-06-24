package com.app.borgapplication.database.impl;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(value);
        return cal;
    }
    @TypeConverter
    public static long dateToTimestamp(Calendar cal) {
        return cal.getTimeInMillis();
    }

}
