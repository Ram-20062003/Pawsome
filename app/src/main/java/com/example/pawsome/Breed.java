package com.example.pawsome;

import android.media.Image;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

public class Breed {
    String bred_for;
    String breed_group;
    Height height;
    @SerializedName("image")
    Image_Class image;
    String imperial;
    String life_span;
    String name;
    String origin;
    String temperament;
    Weight weight;
    String description;
    int id;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getOrigin() {
        return origin;
    }

    public String getImperial() {
        return imperial;
    }

    public String getBred_for() {
        return bred_for;
    }

    public String getBreed_group() {
        return breed_group;
    }

    public Height getHeight() {
        return height;
    }

    public String getLife_span() {
        return life_span;
    }

    public String getName() {
        return name;
    }

    public String getTemperament() {
        return temperament;
    }

    public Image_Class getImage() {
        return image;
    }

    public Weight getWeight() {
        return weight;
    }
}