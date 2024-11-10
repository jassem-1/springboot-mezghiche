package com.workshop.workshop.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Article {

    @Id
    private String id;

    @NotBlank(message = "Label is mandatory")
    @Column(name = "label")
    private String label;

    @Column(name = "price")
    private float price;

    @Column(name = "picture")
    private String picture;

    // Store providerId as a string (MongoDB id)
    @Column(name = "provider_id")
    private String providerId;

    // Constructors
    public Article() {}

    public Article(String label, float price, String picture, String providerId) {
        this.label = label;
        this.price = price;
        this.picture = picture;
        this.providerId = providerId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
