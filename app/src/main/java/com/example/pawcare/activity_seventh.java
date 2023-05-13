package com.example.pawcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_seventh extends AppCompatActivity {
    private TextView itemNameTextView;
    private TextView itemLocationTextView;
    private TextView itemTextView;
    private ImageView itemImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);

        itemNameTextView = findViewById(R.id.name);
        itemLocationTextView = findViewById(R.id.loc);
        itemImageView = findViewById(R.id.imageView);
        itemTextView = findViewById(R.id.text);

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("item_name");
        String itemLocation = intent.getStringExtra("item_location");
        int itemImageResource = intent.getIntExtra("item_image", 0);
        String itemText = intent.getStringExtra("item_text");

        itemNameTextView.setText(itemName);
        itemLocationTextView.setText(itemLocation);
        itemImageView.setImageResource(itemImageResource);
        itemTextView.setText(itemText);

        Button imageButton= findViewById(R.id.button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_seventh.this, activity_eight.class);
                startActivity(intent);
            }
        });
    }
    }

