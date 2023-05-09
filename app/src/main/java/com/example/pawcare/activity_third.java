package com.example.pawcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_third extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailPhoneField;
    private EditText mPasswordField;
    private TextView mForgotPasswordText;
    private CheckBox mSaveLoginCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mAuth = FirebaseAuth.getInstance();
        mEmailPhoneField = findViewById(R.id.user);
        mPasswordField = findViewById(R.id.pass);
        mForgotPasswordText = findViewById(R.id.forgot);
        mSaveLoginCheckbox = findViewById(R.id.checkBox);

        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailPhone = mEmailPhoneField.getText().toString();
                String password = mPasswordField.getText().toString();
                boolean saveLogin = mSaveLoginCheckbox.isChecked();
                signIn(emailPhone, password, saveLogin);
            }
        });

        TextView registerButton = findViewById(R.id.signin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_third.this, activity_fourth.class);
                startActivity(intent);
            }
        });

        mPasswordField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable drawable = mPasswordField.getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawable != null && event.getRawX() >= (mPasswordField.getRight() - drawable.getBounds().width())) {
                        if (mPasswordField.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            mPasswordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        } else {
                            mPasswordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        mForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the ForgotPasswordActivity
                Intent intent = new Intent(activity_third.this, activity_fifth.class);
                startActivity(intent);
            }
        });

        // Check if there are saved login credentials
        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String savedEmailPhone = prefs.getString("email_phone", null);
        String savedPassword = prefs.getString("password", null);
        boolean saveLogin = prefs.getBoolean("save_login", false);
        if (savedEmailPhone != null && savedPassword != null) {
            signIn(savedEmailPhone, savedPassword, saveLogin);
        }
    }

    private void signIn(String emailPhone, String password, boolean saveLogin) {
        if (TextUtils.isEmpty(emailPhone)) {
            mEmailPhoneField.setError("Email or Phone number is required.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Password is required.");
            return;
        }

        mAuth.signInWithEmailAndPassword(emailPhone, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(activity_third.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            // Save login info if checkbox is checked
                            if (saveLogin) {
                                saveLoginInfo(emailPhone, password, saveLogin);
                            }
                            // Navigate to the next activity
                            Intent intent = new Intent(activity_third.this, activity_fifth.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity_third.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveLoginInfo(String emailPhone, String password, boolean saveLogin) {
// Save login info using SharedPreferences or other data storage mechanism
        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email_phone", emailPhone);
        editor.putString("password", password);
        editor.putBoolean("save_login", saveLogin);
        editor.apply();
    }

    private void loadLoginInfo() {
        // Load saved login info from SharedPreferences or other data storage mechanism
        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String emailPhone = prefs.getString("email_phone", null);
        String password = prefs.getString("password", null);
        boolean saveLogin = prefs.getBoolean("save_login", false);

        // If saved login info exists and the checkbox was checked, attempt to sign in automatically
        if (emailPhone != null && password != null && saveLogin) {
            signIn(emailPhone, password, saveLogin);
        }
    }

    private void signOut() {
        // Sign out of Firebase and clear saved login info
        mAuth.signOut();
        SharedPreferences prefs = getSharedPreferences("login_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            loadLoginInfo();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            signOut();
            Intent intent = new Intent(activity_third.this, activity_fifth.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

