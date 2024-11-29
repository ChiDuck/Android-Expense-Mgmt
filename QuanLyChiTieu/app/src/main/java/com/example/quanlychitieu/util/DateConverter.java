package com.example.quanlychitieu.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    static SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

    @NonNull
    public static String formatDate(Date date)
    {
        return sdfDate.format(date);
    }

    public static String formatDateTime(Date date)
    {
        return sdfDateTime.format(date);
    }

    @TypeConverter
    public static Date toDate (Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp (Date date) {
        return date == null ? null : date.getTime();
    }

    public static void dateProcessor(TextView date, @NonNull Calendar calendar, Activity context) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, day);
                date.setText(DateConverter.formatDate(calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }
}
