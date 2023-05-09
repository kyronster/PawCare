package com.example.pawcare;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class activity_fourth extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, emailEditText, passEditText, confirmPassEditText;
    private CheckBox checkBox;
    private Button registerButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        FirebaseAnalytics.getInstance(this);


        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.number);
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.pass);
        checkBox = findViewById(R.id.checkBox);
        registerButton = findViewById(R.id.button);
        confirmPassEditText = findViewById(R.id.cpass);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });

        confirmPassEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawable = confirmPassEditText.getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawable != null && event.getRawX() >= (confirmPassEditText.getRight() - drawable.getBounds().width())) {
                        if (confirmPassEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            confirmPassEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            confirmPassEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        passEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawable = passEditText.getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawable != null && event.getRawX() >= (passEditText.getRight() - drawable.getBounds().width())) {
                        if (passEditText.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            passEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            passEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }




    private void registerNewUser() {
        final String name = nameEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String pass = passEditText.getText().toString().trim();
        final String confirmPass = confirmPassEditText.getText().toString().trim();
        final boolean isChecked = checkBox.isChecked();

        // Validate name
        if (name.isEmpty()) {
            nameEditText.setError("Please enter your name.");
            nameEditText.requestFocus();
            return;
        }

        // Validate phone number
        if (phone.isEmpty()) {
            phoneEditText.setError("Please enter your phone number.");
            phoneEditText.requestFocus();
            return;
        }
        if (phone.length() != 10) {
            phoneEditText.setError("Please enter a valid phone number.");
            phoneEditText.requestFocus();
            return;
        }

        // Validate email
        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email.");
            emailEditText.requestFocus();
            return;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError("Please enter a valid email.");
            emailEditText.requestFocus();
            return;
        }

        // Validate password
        if (pass.isEmpty()) {
            passEditText.setError("Please enter a password.");
            passEditText.requestFocus();
            return;
        }
        if (!isValidPassword(pass)) {
            passEditText.setError("Password must contain at least one capital letter, one small letter, one number, and one symbol.");
            passEditText.requestFocus();
            return;
        }

        // Validate password confirmation
        if (confirmPass.isEmpty()) {
            confirmPassEditText.setError("Please retype your password.");
            confirmPassEditText.requestFocus();
            return;
        }
        if (!confirmPass.equals(pass)) {
            confirmPassEditText.setError("Passwords do not match.");
            confirmPassEditText.requestFocus();
            return;
        }


        // Create new user in Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity_fourth.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save user data to Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference userRef = mDatabase.child("users").child(userId);
                            Users user = new Users(name, phone, email, pass, confirmPass, isChecked);
                            userRef.setValue(user);
                            FirebaseAnalytics.getInstance(activity_fourth.this).logEvent("login", null);

                            // Update user's display name
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();



                            firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    }
                                }
                            });

                            // Display success message
                            Toast.makeText(activity_fourth.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                            // Redirect to login activity
                            finish();
                        } else {
                            // Display error message if registration fails
                            Toast.makeText(activity_fourth.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    // Method to validate email address
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate password
    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        return password.matches(pattern);
    }
}


