package com.example.wenewsapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wenewsapp.R;

public class SignUpActivity extends AppCompatActivity {

    private Button signupButton;
    private EditText username2, password2, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username2 = findViewById(R.id.signup_username);
        password2 = findViewById(R.id.signup_password);
        confirmPassword = findViewById(R.id.signupconfirm_password);
        signupButton = findViewById(R.id.signup_button);

        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        signupButton.setOnClickListener(v -> {
            String enteredEmail = username2.getText().toString();
            String enteredPassword = password2.getText().toString();
            String enteredConfirmPassword = confirmPassword.getText().toString();

            if (enteredEmail.isEmpty()) {
                username2.setError("Please fill this field");
            } else if (enteredPassword.isEmpty()) {
                password2.setError("Please fill this field");
            } else if (enteredConfirmPassword.isEmpty()) {
                confirmPassword.setError("Please fill this field");
            } else if (!enteredPassword.equals(enteredConfirmPassword)) {
                confirmPassword.setError("Passwords do not match");
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", enteredEmail);
                editor.putString("password", enteredPassword);
                editor.putBoolean("isLoggedIn", false); // User is not logged in yet
                editor.apply();

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(SignUpActivity.this, "Sign Up Successfully.Please Login.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

