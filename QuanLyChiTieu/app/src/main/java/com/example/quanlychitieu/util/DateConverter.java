package com.example.quanlychitieu.util;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    static SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDate(Date date)
    {
        return sdfDate.format(date);
    }

    @TypeConverter
    public static Date toDate (Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp (Date date) {
        return date == null ? null : date.getTime();
    }


}
