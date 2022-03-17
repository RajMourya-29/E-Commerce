package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.fetchdata.MyAPICall;
import com.example.e_commerce.fetchdata.Product;
import com.example.e_commerce.fetchdata.ProductAdapter;
import com.example.e_commerce.roomdatabase.DatabaseClient;
import com.example.e_commerce.viewcart.ViewCartActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    TextView qty,price;
    Toolbar toolbar;
    LinearLayout linear;

    TextView textCartItemCount;
    int mCartItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec);
        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        toolbar= findViewById(R.id.toolbar);
        linear= findViewById(R.id.linear);
        textCartItemCount = toolbar.findViewById(R.id.cart_badge);

        getSupportActionBar().hide();

        productArrayList = new ArrayList<>();
        getPrice();
        getQty();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.171/demo/WebService1.asmx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPICall myAPICall = retrofit.create(MyAPICall .class);
        Call<List<Product>> call = myAPICall.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.code() != 200){
                    //     text.setText("Check Connection");
                    return;
                }

                List<Product> products = response.body();

                for (Product movie : products){
                    productArrayList.add(movie);
                }
                productAdapter=new ProductAdapter(productArrayList,MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(productAdapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
        
    linear.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), ViewCartActivity.class));
        }
    });

    }

    public void getPrice() {
        class GetTotal extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                String taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao().getPrice();

                return taskList;
            }

            @Override
            protected void onPostExecute(String total) {
                super.onPostExecute(total);
                 price.setText(total);
                // price.setText("\u20B9"+total);

                if(price.getText().toString().equals("")) {
                    textCartItemCount.setVisibility(View.GONE);
                    linear.setVisibility(View.INVISIBLE);
                }else {
                    linear.setVisibility(View.VISIBLE);
                }
            }
        }

        GetTotal gt = new GetTotal();
        gt.execute();

    }

    public void getQty() {
        class GetUnit extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                String qtyList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao().getQty();

                return qtyList;
            }

            @Override
            protected void onPostExecute(String total) {
                super.onPostExecute(total);
                try {
                    mCartItemCount = Integer.parseInt(total);
                    textCartItemCount.setText(String.valueOf(mCartItemCount));
                    qty.setText(String.valueOf(mCartItemCount)+" Item");
                    if(mCartItemCount==0){
                        textCartItemCount.setVisibility(View.GONE);
                    }else {
                        textCartItemCount.setVisibility(View.VISIBLE);
                    }

                }catch (Exception e){}
            }
        }

        GetUnit gu = new GetUnit();
        gu.execute();

    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
        // do some stuff here
    }

}