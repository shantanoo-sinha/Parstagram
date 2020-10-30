package com.shantanoo.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;
import com.shantanoo.parstagram.activity.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null)
            navigateToMainActivity();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Login button");
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            loginUser(username, password);
        });

        btnSignUp.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Sign Up");
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            signUpUser(username, password);
        });
    }

    private void signUpUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(e -> {
            if (e != null) {
                Log.e(TAG, "Sign Up Exception: ", e);
                Toast.makeText(LoginActivity.this, "Failed to Sign Up.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Sign Up passed. Navigate to main activity
            navigateToMainActivity();
            Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loginUser(String username, String password) {
        Log.d(TAG, "loginUser: ");
        ParseUser.logInInBackground(username, password, (user, e) -> {
            if (e != null) {
                Log.e(TAG, "Login Exception: ", e);
                Toast.makeText(LoginActivity.this, "Failed to login.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Login passed. Navigate to main activity
            navigateToMainActivity();
            Toast.makeText(LoginActivity.this, String.format("Welcome %s!", username), Toast.LENGTH_SHORT).show();
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}