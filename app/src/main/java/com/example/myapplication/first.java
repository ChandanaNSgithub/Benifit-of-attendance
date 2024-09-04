package com.example.myapplication;

        import androidx.annotation.NonNull;
        import

                androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.text.method.HideReturnsTransformationMethod;
        import android.text.method.PasswordTransformationMethod;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.Objects;

public class first extends AppCompatActivity {
    FloatingActionButton fab;
    EditText email, password;
    boolean passwordVisible;
    TextView signUpRedirectText;
    Button btnToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnToast=findViewById(R.id.loginbtn);
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate login logic (replace this with your actual login implementation)
               /* boolean loginSuccessful = performLogin();

                // Display toast message based on login result
                if (loginSuccessful) {
                    Toast.makeText(getApplicationContext(), "Signup Successful", Toast.LENGTH_LONG).show();
                    // Start the next activity after successful login
                    startActivity(new Intent(first.this, form.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Signup Failed. Please try again.", Toast.LENGTH_LONG).show();
                }

                */
                if(!validateUsername()|!validatePassword()){

                }else{
                    checkUser();
                }
            }
        });


    email = findViewById(R.id.Username);
        password = findViewById(R.id.password);
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();

                        if (passwordVisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;

                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_on_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void btnLoginClicked(View v) {
        //Toast.makeText(this, "Hello Android!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, form.class);
        startActivity(i);
    }


    private boolean performLogin() {
        // In a real application, you would check the entered credentials against a backend or some authentication mechanism.
        // For this example, let's simulate a successful login if the username and password are both "admin".
        String enteredUsername = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        return enteredUsername.equals("User") && enteredPassword.equals("cse");

    }
    public Boolean validateUsername(){
        String val=email.getText().toString();
        if(val.isEmpty()){
            email.setError("Username can't be empty");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }

    }
    public Boolean validatePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Password can't be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    public void checkUser() {
        String useremail = email.getText().toString().trim();
        String userpassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");
        Query checkUserQuery = reference.orderByChild("username").equalTo(useremail);

        checkUserQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String passwordFromDB = snapshot.child("password").getValue(String.class);
                        if (passwordFromDB != null && passwordFromDB.equals(userpassword)) {
                            // Passwords match, login successful
                            email.setError(null);
                            Intent intent = new Intent(first.this, form.class);
                            startActivity(intent);
                            finish(); // Close current activity
                            return;
                        } else {
                            // Password doesn't match
                            password.setError("Invalid Credentials");
                            password.requestFocus();
                        }
                    }
                } else {
                    // User doesn't exist
                    email.setError("User does not exist");
                    email.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database errors
                Toast.makeText(first.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

    // ... (other methods)
