package com.shantanoo.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shantanoo.parstagram.activity.RecyclerActivity;

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

        if(ParseUser.getCurrentUser() != null)
            navigateToRecyclerActivity();

        etUsername = findViewById(R.id.etUsername);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sign Up");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(username, password);
            }
        });
    }

    private void signUpUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Sign Up Exception: ", e);
                    Toast.makeText(LoginActivity.this, "Failed to Sign Up.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Sign Up passed. Navigate to main activity
                navigateToRecyclerActivity();
                Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.d(TAG, "loginUser: ");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Login Exception: ", e);
                    Toast.makeText(LoginActivity.this, "Failed to login.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Login passed. Navigate to main activity
                navigateToRecyclerActivity();
                Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToRecyclerActivity() {
        Intent intent = new Intent(LoginActivity.this, RecyclerActivity.class);
        startActivity(intent);
        finish();
    }
}