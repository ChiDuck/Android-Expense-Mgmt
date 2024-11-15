package com.example.quanlychitieu.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quanlychitieu.dao.CategoryDAO;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.util.AppDatabase;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryDAO catDAO;
    private final LiveData<List<Category>> allCats;

    public CategoryViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        catDAO = db.catDAO();
        allCats = catDAO.getALLCats();
    }

    public LiveData<List<Category>> getAllCats() {
        return allCats;
    }

    public void insert(Category category) {
        new Thread(() -> catDAO.insertCat(category)).start();
    }

    public void delete(Category category) {
        new Thread(() -> catDAO.deleteCat(category)).start();
    }

    public void update(Category category) {
        new Thread(() -> catDAO.updateCat(category)).start();
    }
}
