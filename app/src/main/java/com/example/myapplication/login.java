package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    private boolean isLoggedIn = false; // Example boolean to simulate login state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        TextView loginOrSignUpText = findViewById(R.id.loginOrSignUpText);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText reenterPasswordEditText = findViewById(R.id.reenter_password);
        Button loginButton = findViewById(R.id.login_button);
        Button signUpButton = findViewById(R.id.sign_up_button);
        DataStudent ds = new DataStudent();

        // Example toggle text based on login state
        if (isLoggedIn) {
            loginOrSignUpText.setText("Sign up");
        } else {
            loginOrSignUpText.setText("Sign in");
        }

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String reenteredPassword = reenterPasswordEditText.getText().toString().trim();

            // Validate if username and password fields are filled
            if (username.isEmpty() || password.isEmpty() || reenteredPassword.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals(reenteredPassword)) {
                // Passwords match, proceed with login
                Student stu = new Student(username, password);
                ds.add(stu).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    loginUser(username); // Pass only username to login function
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                // Passwords don't match, show error message
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        });

        signUpButton.setOnClickListener(v -> {
            // Navigate to ActivityFirst
            Intent intent = new Intent(login.this, first.class);
            startActivity(intent);
        });
    }

    private void loginUser(String username) {
        // Implement your login logic here
        // For demonstration, assume successful login
        isLoggedIn = true;

        // Navigate to the next page, e.g., FormActivity
        Intent intent = new Intent(login.this, form.class);
        startActivity(intent);
        finish(); // Finish current activity so back button doesn't return here
    }
}
