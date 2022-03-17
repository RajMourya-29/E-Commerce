package com.example.e_commerce.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Products.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO taskDao();
}