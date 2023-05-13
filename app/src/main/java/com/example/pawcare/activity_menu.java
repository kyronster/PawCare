package com.example.pawcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.security.Policy;

public class activity_menu extends AppCompatActivity {

private TextView Home, Shop, ProperCare, Policy, FAQS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Home = findViewById(R.id.home);

        // Set an OnClickListener on the TextView
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the new activity
                Intent intent = new Intent(activity_menu.this, activity_sixth.class);

                // Start the new activity
                startActivity(intent);
            }
        });


        Shop = findViewById(R.id.shop);
        Shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.petexpress.com.ph/"));
                startActivity(intent);
            }
        });




        Policy = findViewById(R.id.policy);

        // Set an OnClickListener on the TextView
        Policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the new activity
                Intent intent = new Intent(activity_menu.this, activity_policy.class);

                // Start the new activity
                startActivity(intent);
            }
        });


        FAQS = findViewById(R.id.FAQS);

        // Set an OnClickListener on the TextView
        FAQS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the new activity
                Intent intent = new Intent(activity_menu.this, activity_faqs.class);

                // Start the new activity
                startActivity(intent);
            }
        });

        ProperCare = findViewById(R.id.care);

        // Set an OnClickListener on the TextView
        ProperCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to launch the new activity
                Intent intent = new Intent(activity_menu.this, activity_care.class);

                // Start the new activity
                startActivity(intent);
            }
        });



        TextView buttonLogout = findViewById(R.id.logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_menu.this);
                builder.setMessage("Are you sure you want to delete your saved login?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the saved login
                                FirebaseAuth.getInstance().signOut();
                                SharedPreferences preferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("username");
                                editor.remove("password");
                                editor.apply();

                                // Check that SharedPreferences values have been removed before starting ThirdActivity
                                if (!preferences.contains("username") && !preferences.contains("password")) {
                                    finish();
                                    Intent intent = new Intent(activity_menu.this, activity_third.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(activity_menu.this, "Error logging out. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss the dialog
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}