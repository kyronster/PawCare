package com.example.pawcare;

public class Item {
    private String name;
    private String location;
    private int imageResId;
    private String type;

    private String text;


    public Item(String name, String location, int imageResId, String type, String text) {
        this.name = name;
        this.location = location;
        this.imageResId = imageResId;
        this.type = type;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}