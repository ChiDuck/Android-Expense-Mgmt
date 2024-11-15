package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.quanlychitieu.viewmodel.BudgetViewModel;
import com.example.quanlychitieu.viewmodel.CategoryViewModel;
import com.example.quanlychitieu.viewmodel.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    ImageButton ibtnBack;
    ListView listCats;
    FloatingActionButton fabAddCat;
    ArrayList<Category> array;
    CategoryAdapter adapter;
    CategoryViewModel catVM;
    TransactionViewModel tranVM;
    BudgetViewModel budVM;

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
        addControls();
        dataObserve();
        addEvents();
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
        fabAddCat.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this,CategoryDetailActivity.class);
            intent.putExtra("user_id",user_id);
            startActivityForResult(intent, 1);
        });

        ibtnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void addControls() {
        ibtnBack = findViewById(R.id.ibtnBack);
        listCats = findViewById(R.id.listCats);
        fabAddCat = findViewById(R.id.fabAddCat);
        array = new ArrayList<>();
        catVM = new ViewModelProvider(this).get(CategoryViewModel.class); //initialize before adapter
        tranVM = new ViewModelProvider(this).get(TransactionViewModel.class); //initialize before adapter
        budVM = new ViewModelProvider(this).get(BudgetViewModel.class); //initialize before adapter
        adapter = new CategoryAdapter(this,R.layout.cats_item,array,catVM,tranVM,budVM);
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