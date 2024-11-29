package com.example.quanlychitieu.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlychitieu.dao.TransactionDAO;
import com.example.quanlychitieu.model.Transaction;
import com.example.quanlychitieu.util.AppDatabase;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private final TransactionDAO tranDAO;
    private final LiveData<List<Transaction>> allTrans;

    public TransactionViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        tranDAO = db.transDAO();
        allTrans = tranDAO.getALLTrans();
    }

    public LiveData<List<Transaction>> getAllTrans() {
        return allTrans;
    }

    public LiveData<List<Transaction>> getAllTransByCat(int id) {
        return tranDAO.getALLTransByCat(id);
    }

    public LiveData<List<Transaction>> getAllTransFromUser(int id) {
        return tranDAO.getALLTransFromUser(id);
    }

    public void insert(Transaction tran) throws InterruptedException {
        Thread thread = new Thread(() -> tranDAO.insertTran(tran));
        thread.start();
        thread.join();
    }

    public void delete(Transaction tran) {
        new Thread(() -> tranDAO.deleteTran(tran)).start();
    }

    public void deleteTransById(int id) throws InterruptedException {
        Thread thread = new Thread(() -> tranDAO.deleteTransById(id));
        thread.start();
        thread.join();
    }
}
