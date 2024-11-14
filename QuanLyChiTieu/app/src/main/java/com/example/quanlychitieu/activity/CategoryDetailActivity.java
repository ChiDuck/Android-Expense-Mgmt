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
    Category cat;
    boolean add;

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
        getIntentData();
        addEvents();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("oldcat")) {
            cat = (Category) intent.getSerializableExtra("oldcat");
            txtTitle.setText("Sửa danh mục");
            txtName.setText(cat.getName());
            if (cat.isType()) radEarn.setChecked(true);
            else radSpend.setChecked(true);
            radEarn.setEnabled(false);
            radSpend.setEnabled(false);
            add = false;
        }
    }

    private void addEvents() {

        btnSave.setOnClickListener(view -> {
                String name = txtName.getText().toString();
                boolean type = radEarn.isChecked() ? true : false;

                Intent intent = getIntent();
                if (!add) cat.setName(name);
                else {
                    int id = intent.getIntExtra("user_id", 0);
                    cat = new Category(name,type,id);
                }
                intent.putExtra("cat", cat);
                setResult(2, intent);
                finish();
        });

        ibtnBack.setOnClickListener(view -> {
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
        cat = new Category();
        add = true;
    }
}