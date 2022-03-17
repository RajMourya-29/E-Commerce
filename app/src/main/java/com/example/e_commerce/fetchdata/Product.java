package com.example.e_commerce.fetchdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("ProductName")
    @Expose
    private String productName;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;
    @SerializedName("Price")
    @Expose
    private List<QtyPrice> price = null;
    @SerializedName("GetSize")
    @Expose
    private String getSize;

    private String imgurl;
    private String tprice;

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

    public List<QtyPrice> getPrice() {
        return price;
    }

    public void setPrice(List<QtyPrice> price) {
        this.price = price;
    }

    public String getGetSize() {
        return getSize;
    }

    public void setGetSize(String getSize) {
        this.getSize = getSize;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }
}
