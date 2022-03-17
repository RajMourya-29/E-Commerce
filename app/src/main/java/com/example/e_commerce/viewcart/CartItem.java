package com.example.e_commerce.viewcart;

import com.example.e_commerce.fetchdata.QtyPrice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartItem {

    String productName;
    String productImage;
    String price;
    String qty;
    String units;

    public CartItem(String productName, String productImage, String price, String qty, String units) {
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.qty = qty;
        this.units = units;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
