package com.example.pawsome;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Table_Breed.class},version = 1)
public abstract class TableRoomDatabase extends RoomDatabase {

    public abstract Table_Breed_Dao table_breed_dao();
    private static volatile TableRoomDatabase INSTANCE;
    static TableRoomDatabase getInstance(Context context)
    {
        if(INSTANCE==null)
        {
            synchronized (TableRoomDatabase.class){
                if(INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TableRoomDatabase.class,"Table_Database").build();

                }

            }
        }
        return INSTANCE;
    }

}
