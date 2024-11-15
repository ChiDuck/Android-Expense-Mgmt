package com.example.quanlychitieu.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlychitieu.R;
import com.example.quanlychitieu.model.User;
import com.example.quanlychitieu.util.PasswordEncryption;
import com.example.quanlychitieu.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    EditText txtName,txtEmail, txtPasswd;
    Button btnSignup, btnBack;
    UserViewModel userVM;
    Observer<User> observer;
    User user;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        signupObserve();
    }

    private void signupObserve() {
        observer = u -> {
            if (u == null) {
                userVM.insert(user);
                flag = false;
                finish();
            } else if (flag) Toast.makeText(this, "Email đã có tài khoản!", Toast.LENGTH_SHORT).show();
        };
    }

    private void addEvents() {
        btnSignup.setOnClickListener(view -> {
    //        btnSignup.setEnabled(false);
    //        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                btnSignup.setEnabled(true);
    //            }
    //        }, 2000);
            String name = txtName.getText().toString();
            String email = txtEmail.getText().toString();
            String pass = PasswordEncryption.hashPassword(txtPasswd.getText().toString());
            Date create = Calendar.getInstance().getTime();

            if (!email.isEmpty() && !name.isEmpty() && !pass.isEmpty()) {
                user = new User(name, email, pass, create, null);
                userVM.signupCheck(email).removeObserver(observer);
                userVM.signupCheck(email).observe(this, observer);
            } else Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack);
        userVM = new ViewModelProvider(this).get(UserViewModel.class);
        user = new User();
    }
}