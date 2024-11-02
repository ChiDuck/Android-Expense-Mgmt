package com.example.quanlychitieu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlychitieu.model.Budget;

import java.util.List;

@Dao
public interface BudgetDAO {
    @Query("SELECT * FROM budget WHERE budget_id = :id")
    Budget getBudgetById(int id);
    @Query("SELECT * FROM budget")
    List<Budget> getALLBudget();
    @Insert
    void insertBudget(Budget... budgets);
    @Delete
    void deleteBudget(Budget budget);
    @Update
    void updateBudget(Budget... budgets);
}
