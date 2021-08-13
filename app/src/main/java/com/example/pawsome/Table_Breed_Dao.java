package com.example.pawsome;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface Table_Breed_Dao {

    @Insert
    void insert_fav(Table_Breed table_breed);

    @Query("SELECT*FROM table_fav")
    List<Table_Breed> get_fav_dog();

    @Query("SELECT*FROM table_fav WHERE dog_name LIKE :fav")
    Table_Breed find_the_dog(String fav);

    @Delete
    void delete_fav(Table_Breed table_breed);

}
