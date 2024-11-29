package com.example.quanlychitieu.activity;

import android.content.Intent;
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

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPasswd;
    Button btnLogin, btnSignup;
    UserViewModel userVM;
    Observer<User> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        loginObserve();
    }

    private void loginObserve() {
        observer = user -> {
            if (user != null) {
                // user.setLast_login(Calendar.getInstance().getTime());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user_id", user.getId());
                intent.putExtra("user_name", user.getName());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void addEvents() {
        btnLogin.setOnClickListener(view -> {
            String email = txtEmail.getText().toString();
            String pass = PasswordEncryption.hashPassword(txtPasswd.getText().toString());
            userVM.login(email,pass).removeObserver(observer);
            userVM.login(email,pass).observe(this,observer);
        });
        btnSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void addControls() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        userVM = new ViewModelProvider(this).get(UserViewModel.class);
       // userVM.deleteAll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        btnLogin.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                btnLogin.setEnabled(true);
            }
        }, 1000);
    }
}