package com.example.e_commerce.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM Products")
    List<Products> getAll();

    @Query("SELECT SUM(TPRICE) FROM Products")
    String getPrice();

    @Query("SELECT SUM(units) FROM Products")
    String getQty();

    @Insert
    void insert(Products task);

    @Delete
    void delete(Products task);

    @Query("DELETE FROM Products WHERE name = :name")
    void deleteByName(String name);

    @Update
    void update(Products task);

    @Query("Update Products set name = :name WHERE name = :name")
    void updateByName(String name);

}
