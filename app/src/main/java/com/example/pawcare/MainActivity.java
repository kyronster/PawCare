package com.example.pawcare;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create a Handler object to delay the intent
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create the intent to start the next activity
                Intent intent = new Intent(MainActivity.this, second.class);
                startActivity(intent);

                // Finish the current activity to prevent going back to it on back button press
                finish();
            }
        }, 3000); // 3-second delay
    }
}