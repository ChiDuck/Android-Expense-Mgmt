package com.example.quanlychitieu.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.adapter.CategoryAdapter;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    Button btnAdd;
    ListView listCats;
    FloatingActionButton fabAddTran;
    ArrayList<Category> array;
    CategoryAdapter adapter;
    CategoryViewModel catVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(view -> {

        });
    }

    private void addControls() {
        btnAdd = findViewById(R.id.btnCat);
        listCats = findViewById(R.id.listCats);
        fabAddTran = findViewById(R.id.fabAddTran);
        array = new ArrayList<>();
        adapter = new CategoryAdapter(this,R.layout.cats_item,array);
        listCats.setAdapter(adapter);
        catVM = new ViewModelProvider(this).get(CategoryViewModel.class);
    }
}