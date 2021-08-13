package com.example.pawsome;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_fav")
public class Table_Breed {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name ="table_uid")
    private int uid;

    @ColumnInfo(name = "dog_name")
    private String dog_name;

    @ColumnInfo(name="dog_image")
    private String dog_image_url;

    public Table_Breed(String dog_name, String dog_image_url) {
        this.dog_name = dog_name;
        this.dog_image_url = dog_image_url;
    }

    @Override
    public String toString() {
        return "Table_Breed{" +
                "uid=" + uid +
                ", dog_name='" + dog_name + '\'' +
                ", dog_image_url='" + dog_image_url + '\'' +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDog_name() {
        return dog_name;
    }

    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }

    public String getDog_image_url() {
        return dog_image_url;
    }

    public void setDog_image_url(String dog_image_url) {
        this.dog_image_url = dog_image_url;
    }
}
