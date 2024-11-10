package com.example.quanlychitieu.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quanlychitieu.dao.BudgetDAO;
import com.example.quanlychitieu.dao.CategoryDAO;
import com.example.quanlychitieu.dao.NotificationDAO;
import com.example.quanlychitieu.dao.TransactionDAO;
import com.example.quanlychitieu.dao.UserDAO;
import com.example.quanlychitieu.model.Budget;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.model.Notification;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.model.User;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Category.class, Budget.class, Transaction.class, Notification.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static final String DATABASE_NAME = "quanlychitieu_db";

    public abstract UserDAO userDAO();
    public abstract CategoryDAO catDAO();
    public abstract BudgetDAO budgetDAO();
    public abstract TransactionDAO transDAO();
    public abstract NotificationDAO notifDAO();
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }
}