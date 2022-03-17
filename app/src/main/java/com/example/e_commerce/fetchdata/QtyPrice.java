package com.example.e_commerce.fetchdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QtyPrice {

    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("Price")
    @Expose
    private String price;

    public QtyPrice(String qty, String price) {
        this.qty = qty;
        this.price = price;
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

}
