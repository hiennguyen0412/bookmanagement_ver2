package com.example.hiennv.bookmanagement.model;

/**
 * Created by hiennv on 22/03/2018.
 */

public class Book {
    private int id;
    private String name;
    private String author;
    private String type;
    private double price;
    private byte[] image;

    public Book() {
    }

    public Book(int id, String name, String author, String type, double price, byte[] image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.type = type;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
