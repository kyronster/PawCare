package com.example.pawcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_sixth extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }



        listView = findViewById(R.id.listview);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Margherita", "Obando, Bulacan", R.drawable.cat1));
        itemList.add(new Item("Margherita", "Obando, Bulacan", R.drawable.cat1));
        itemList.add(new Item("Margherita", "Obando, Bulacan", R.drawable.cat1));
        itemList.add(new Item("Margherita", "Obando, Bulacan", R.drawable.cat1));
        itemList.add(new Item("Margherita", "Obando, Bulacan", R.drawable.cat1));


        ItemAdapter adapter = new ItemAdapter(this, itemList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = (Item) parent.getItemAtPosition(position);
                Toast.makeText(activity_sixth.this, selectedItem.getName() + " clicked!", Toast.LENGTH_SHORT).show();

               // Intent intent = new Intent(activity_sixth.this, Foodd.class);
               // startActivity(intent);
            }
        });
    }
}