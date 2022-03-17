package com.example.e_commerce.fetchdata;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyAPICall {

    @GET("Productarray")
    Call<List<Product>> getProducts();


}
