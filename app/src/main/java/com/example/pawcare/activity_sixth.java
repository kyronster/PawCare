package com.example.pawcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_sixth extends AppCompatActivity {
    private ArrayList<Item> itemList;
    private ListView listView;
    private Button catButton, dogButton, allButton;
    private ItemAdapter itemAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        // Find the buttons and list view by their IDs
        catButton = findViewById(R.id.catbutton);
        dogButton = findViewById(R.id.dogbutton);
        allButton = findViewById(R.id.allbutton);
        listView = findViewById(R.id.listview);
        ImageButton imageButton = findViewById(R.id.bmenu);


        // Create the list of items
        itemList = new ArrayList<>();
        itemList.add(new Item("Kyron", "Obando, Bulacan", R.drawable.cat6, "cat", "Hi there! My name is Kyron, but you can call me Kai! and I'm a lovable tabby cat looking for my forever home. I'm a bit shy at first, but once I get to know you, I'll be your loyal companion for life. I love playing with toys, snuggling up in cozy blankets, and watching birds out the window."));
        itemList.add(new Item("Telle", "Maysilo, Malabon", R.drawable.dog6, "dog", "Hi there, my name is Telle and I'm a Shih Tzu! I may be small, but don't let my size fool you - I have a big personality! I love to play and cuddle, and I always want to be near my human friends. My fur is soft and fluffy, and my big eyes will melt your heart. I'm a quick learner and I love to show off my tricks, especially when there's a treat involved! I may be a lap dog, but I have a lot of energy and I love to run around and explore. I'm always up for a good game of fetch or tug-of-war. Most of all, I just want to be loved and cared for by my humans. I'll be your loyal companion for life if you give me a chance!"));
        itemList.add(new Item("Shian", "M.Naval, Navotas", R.drawable.dog9, "dog", "AW AW AW!, Hi there, I'm Shian, a Husky with a love for adventure and a sharp mind to match. I'm known for my athleticism and endurance, and I'm always up for a run or a challenging hike. But don't let my physical abilities fool you - I'm also a smart and clever dog who loves a good puzzle or game. I'm always looking for ways to challenge myself, and I never back down from a mental or physical challenge."));
        itemList.add(new Item("Sari", "Maysilo, Malabon", R.drawable.cat2, "cat", "Meow there, I'm Sari! I'm a cat with a lot of personality. I'm a confident and curious kitty who loves to explore new places and meet new people. I'm also very affectionate and love to snuggle up with my humans. I'm looking for a family who will cherish my independent spirit and make me a part of their daily lives."));
        itemList.add(new Item("Bantay", "Tondo, Manila", R.drawable.dog1, "dog", "Hi there, I'm Bantay! I may not have a fancy pedigree or a designer label, but I've got plenty of personality and heart. I'm a proud Aspin (asong Pinoy) from the streets of Manila, and I've learned how to survive on my wits and charm. I'm a friendly and loyal dog who loves to be around people. I'm looking for a family who will give me the love and care I deserve, and in return, I promise to be their faithful companion for life."));
        itemList.add(new Item("Simba", "Ayala Avenue, Makati City", R.drawable.cat3, "cat", "Hello, my name is Simba! I'm a playful and friendly cat who loves to chase toys and curl up on laps. I have a sleek and shiny coat, with beautiful markings that make me stand out. I'm looking for a family who will give me plenty of attention and affection, and I promise to be a loyal and loving companion in return."));
        itemList.add(new Item("Maya", "Quiapo, Manila", R.drawable.dog2, "dog", "Woof woof, my name is Maya, and I'm a sweet and energetic Aspin looking for a new family. I'm a small dog with a caramel-colored coat and a big personality. I love playing with toys, going for runs, and learning new things. I'm hoping to find a family who will give me the exercise and mental stimulation I need to stay happy and healthy, and who will love me unconditionally."));
        itemList.add(new Item("Margherita", "Rizal Avenue, Puerto Princesa City", R.drawable.cat4, "cat", "Lagay"));
        itemList.add(new Item("Balong", "Quezon Avenue, Quezon City", R.drawable.dog3, "dog", "Aw aw aw, aw aw aw aw aw! Aw aw, aw aw aw aw, aw aw aw aw aw. Aw aw aw, aw aw aw aw aw aw aw aw, aw aw aw aw aw. Aw aw, aw aw aw aw aw, aw aw aw aw aw aw aw. Aw aw aw, aw aw aw aw aw, aw aw aw aw aw aw aw aw aw."));
        itemList.add(new Item("Rusty", "JP Rizal Street, Calamba City", R.drawable.dog4, "dog", "Hi there, my name is Rusty, and I'm a stray dog looking for a loving home. I may not have a pedigree, but I've got plenty of love to give. I'm a friendly and loyal companion who will stick by your side through thick and thin. I love to play, go for walks, and snuggle up with my humans. Will you give me a chance to be a part of your family?"));
        itemList.add(new Item("Koda", "P. Burgos Street, Batangas City", R.drawable.dog5, "dog", "Hi, I'm Koda! I'm a playful and energetic dog with a lot of love to give. I may not have a fancy pedigree or a designer label, but what I lack in status, I make up for in loyalty and affection. I'm looking for a family who will appreciate my fun-loving spirit and give me a forever home where I can run, play, and cuddle to my heart's content."));
        itemList.add(new Item("Boots", "Magsaysay Avenue, Naga City", R.drawable.cat5, "cat", "Meow meow! My name is Boots, and I'm a friendly tabby cat with a playful personality. I love to chase after toys and explore my surroundings. I also enjoy spending time with my humans, and I'll often curl up on laps for a nap. I'm looking for a loving family who will make me a part of their daily lives."));
        itemList.add(new Item("Cooper", "Taft Avenue, Manila", R.drawable.dog7, "dog", "Hi there, I'm Cooper! I'm a friendly and affectionate dog who's always eager to make new friends. I love nothing more than cuddling up with my humans, going for long walks, and playing with toys. I may not be the most athletic or agile pup, but I've got plenty of heart and soul. I'm looking for a family who will appreciate my gentle and loving nature and give me the love and care I deserve."));
        itemList.add(new Item("Mimi", "N. Domingo Street, San Juan City", R.drawable.cat10, "cat", "Hello, my name is Mimi! I'm a gorgeous Persian cat with long and fluffy fur. I have a sweet and gentle personality, and I love to be pampered and petted. I'm not very active, but I do enjoy playing with toys and lounging in comfortable spots. I'm looking for a quiet and loving home where I can be spoiled and cherished."));
        itemList.add(new Item("Dope", "Santa Rosa, Laguna ", R.drawable.cat7, "cat", "Hi, I'm Midnight, a sleek and sophisticated black cat with a heart of gold. I'm a bit of a homebody and prefer quiet evenings lounging on the couch with my humans. But don't let my laid-back attitude fool you - I'm also a skilled hunter and love to show off my stalking and pouncing skills with toy mice."));
        itemList.add(new Item("Ginger", "Tagaytay City, Cavite", R.drawable.cat8, "cat", "Meowdy! I'm Ginger, a spunky and energetic orange tabby cat. I'm always up for a good game of tag or hide-and-seek, and I love to climb and explore high places. When I'm not on the move, I enjoy snuggling up with my humans and being petted under my chin."));
        itemList.add(new Item("Bailey", "Roxas Boulevard, Pasay City", R.drawable.dog8, "dog", "My name is Bailey, and I'm a happy-go-lucky dog who loves to make people smile. I'm always wagging my tail, ready to greet new friends and show off my playful personality. I may not have a fancy pedigree or a celebrity owner, but I've got plenty of charm and charisma. I'm looking for a family who will embrace my joyful spirit and give me a loving and fun-filled home where I can be the life of the party."));
        itemList.add(new Item("Lucky", "Burgos Street, Bacolod City", R.drawable.dog10, "dog", "Woof woof, my name is Lucky, and I'm a stray dog who's had a bit of a rough start in life. But I haven't let that get me down - I'm a survivor! I'm a smart and resourceful dog who knows how to take care of myself, but I'd love nothing more than to have a warm bed to sleep in and a loving family to call my own. Will you be my lucky break?"));
        itemList.add(new Item("Mocha", "Davao City, Davao del Sur", R.drawable.cat9, "cat", "Hi, my name is Mocha and I'm a beautiful tortoiseshell cat with an independent streak. I'm not one for cuddling, but I enjoy being in the same room as my humans and watching the world go by from a comfortable perch. I'm also a talented escape artist and enjoy exploring the great outdoors, so a safe and secure home is a must for me."));
        itemList.add(new Item("Cleo", "Pasig City, Metro Manila", R.drawable.cat1, "cat", "Meow there, I'm Cleo! I'm a regal-looking cat with a lot of personality. I love to play and explore, but I also have a soft spot for curling up on laps and getting lots of love. I'm looking for a family who will appreciate my independent spirit and give me lots of attention."));


        // Create an instance of the ItemAdapter class and set it to the itemAdapter variable
        itemAdapter = new ItemAdapter(this, itemList);
        listView.setAdapter(itemAdapter);


        // Set click listeners to the filter buttons


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_sixth.this, activity_menu.class);
                startActivity(intent);
            }
        });

        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterItems("cat");
            }
        });

        dogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterItems("dog");
            }
        });

        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the list view to show all items
                itemAdapter = new ItemAdapter(activity_sixth.this, itemList);
                listView.setAdapter(itemAdapter);
            }
        });


    }

    private void filterItems(String category) {
        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item : itemList) {
            if (item.getType().equals(category)) {
                filteredItems.add(item);
            }
        }
        itemAdapter = new ItemAdapter(this, filteredItems);
        listView.setAdapter(itemAdapter);


        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


        TextView name = findViewById(R.id.name);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            mDatabaseRef.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Retrieve the user data from the snapshot

                    Users user = snapshot.getValue(Users.class);
                    String names = user.getName();

                    // Set the text views to the user data
                    name.setText("Hi " + names + "!");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

    }
}

