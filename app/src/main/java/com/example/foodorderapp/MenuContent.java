package com.example.foodorderapp;

public class MenuContent {
    String id;
    String description;
    String image;
    int key;
    String name;
    int price;

    public MenuContent() {
    }

    public MenuContent(String id, String description, String image, int key, String name, int price) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.key = key;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
