package com.example.e_commerce.roomdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

@Entity
public class Products implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "img")
    private String img;

    @ColumnInfo(name = "qty")
    private String qty;

    @ColumnInfo(name = "units")
    private String units;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "tprice")
    private String tprice;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    @Override
    public String toString() {
        return  name   + "\t" +
                img    + "\t" +
                qty    + "\t" +
                units  + "\t" +
                price  + "\t" +
                tprice + System.lineSeparator() ;
    }

}
