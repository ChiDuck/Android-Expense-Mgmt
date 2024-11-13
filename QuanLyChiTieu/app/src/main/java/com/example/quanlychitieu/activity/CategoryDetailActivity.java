package com.example.quanlychitieu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.Category;
import com.example.quanlychitieu.viewmodel.UserViewModel;

public class CategoryDetailActivity extends AppCompatActivity {

    ImageButton ibtnBack;
    Button btnSave;
    TextView txtTitle;
    EditText txtName;
    RadioButton radEarn, radSpend;

    UserViewModel userVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(view -> {
            String name = txtName.getText().toString();
            boolean type = radEarn.isChecked() ? true : false;
            Category cat = new Category();
            cat.setName(name);
            cat.setType(type);
            Intent intent = getIntent();
            intent.putExtra("cat",cat);
            setResult(2,intent);
            finish();

        });
    }

    private void addControls() {
        ibtnBack = findViewById(R.id.ibtnBack);
        btnSave = findViewById(R.id.btnSave);
        txtTitle = findViewById(R.id.txtTitle);
        txtName = findViewById(R.id.txtName);
        radEarn = findViewById(R.id.radEarn);
        radSpend = findViewById(R.id.radSpend);
    }
}