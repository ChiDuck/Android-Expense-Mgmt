package com.example.quanlychitieu.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlychitieu.dao.BudgetDAO;
import com.example.quanlychitieu.model.Budget;
import com.example.quanlychitieu.util.AppDatabase;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private final BudgetDAO budgetDAO;
    private final LiveData<List<Budget>> allBudgets;

    public BudgetViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        budgetDAO = db.budgetDAO();
        allBudgets = budgetDAO.getALLBudgets();
    }

    public LiveData<List<Budget>> getAllBuds() {
        return allBudgets;
    }

    public LiveData<Budget> getBudgetById(int id) {
        return budgetDAO.getBudgetByCategoryId(id);
    }

    public void insert(Budget budget) {
        new Thread(() -> budgetDAO.insertBudget(budget)).start();
    }

    public void delete(Budget budget) {
        new Thread(() -> budgetDAO.deleteBudget(budget)).start();
    }

    public void deleteById(int id) throws InterruptedException {
        Thread thread = new Thread(() -> budgetDAO.deleteById(id));
        thread.start();
        thread.join();
    }

    public void updateBalance(int id, int newbalance) { new Thread(() -> budgetDAO.updateBalance(id, newbalance)).start(); }
}
