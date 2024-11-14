package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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

public class CategoryActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_category);
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
        dataObserve();
    }

    private void dataObserve() {
        catVM.getAllCats().observe(this, cats -> {
            if (cats != null) {
                adapter.setCats(cats);
            }
        });
    }

    private void addEvents() {
        Intent preIntent = getIntent();
        int user_id = preIntent.getIntExtra("user_id", 0);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,CategoryDetailActivity.class);
            intent.putExtra("user_id",user_id);
            startActivityForResult(intent, 1);
        });
    }

    private void addControls() {
        btnAdd = findViewById(R.id.btnAdd);
        listCats = findViewById(R.id.listCats);
        fabAddTran = findViewById(R.id.fabAddTran);
        array = new ArrayList<>();
        catVM = new ViewModelProvider(this).get(CategoryViewModel.class); //initialize before adapter
        adapter = new CategoryAdapter(this,R.layout.cats_item,array,catVM);
        listCats.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2) {
            Category cat = (Category) data.getSerializableExtra("cat");
            catVM.insert(cat);
        }
        if (requestCode == 3 && resultCode == 2) {
            Category cat = (Category) data.getSerializableExtra("cat");
            catVM.update(cat);
        }
    }
}