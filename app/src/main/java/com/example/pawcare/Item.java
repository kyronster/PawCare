package com.example.pawcare;

public class Item {
    private String name;
    private String location;
    private int imageResId;

    public Item(String name, String location, int imageResId) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getImageResId() {
        return imageResId;
    }
}