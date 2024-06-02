package com.example.wenewsapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wenewsapp.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username, password;
    private TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_btn);
        signupText = findViewById(R.id.signupText);

        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Jika pengguna telah login sebelumnya, langsung arahkan ke MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        loginButton.setOnClickListener(v -> {
            String enteredEmail = username.getText().toString();
            String enteredPassword = password.getText().toString();

            if (enteredEmail.isEmpty()) {
                username.setError("Please fill this field");
            } else if (enteredPassword.isEmpty()) {
                password.setError("Please fill this field");
            } else if (enteredEmail.equals(savedPassword) && enteredPassword.equals(savedPassword)) {
                // Jika email dan password yang dimasukkan cocok dengan yang disimpan, maka login berhasil
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Jika email atau password tidak cocok, tampilkan pesan kesalahan
                Toast.makeText(LoginActivity.this, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect ke halaman pendaftaran jika pengguna belum memiliki akun
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
