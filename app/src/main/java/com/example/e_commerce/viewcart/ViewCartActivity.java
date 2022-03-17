package com.example.e_commerce.viewcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.roomdatabase.DatabaseClient;
import com.example.e_commerce.roomdatabase.Products;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewCartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView qty,price;
    LinearLayout linear;
    int mCartItemCount;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        price = findViewById(R.id.price);
        qty = findViewById(R.id.qty);
        linear= findViewById(R.id.linear);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getItems();
        getPrice();
        getQty();
        addToExcel();


    }

    private void addToExcel() {



        List<Products> scannedOutputList1 = new ArrayList<>();

        scannedOutputList1 = DatabaseClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .taskDao()
                .getAll();
// Log.e("data",scannedOutputList1.toString());
        //   db.deleteInputRecords();
        //   scancount.setText("SCAN BARCODE : "+db.getInputCount());
        File pathToExternalStorage = Environment.getExternalStorageDirectory();
        File appDirectory = new File(pathToExternalStorage.getAbsolutePath() + "/QrScanner/Output" + "/");
        if (!appDirectory.exists()) {
            boolean mkdir = appDirectory.mkdirs();
            if (mkdir){

            }
        }

        try {

            File file= new File(appDirectory, "Output "+new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())+".txt");
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("");
            FileWriter writer = new FileWriter(file,true);
            for(int i=0;i<scannedOutputList1.size();i++){
                writer.write(scannedOutputList1.toString());
            }
            writer.flush();
            writer.close();
            Log.e("Log: ", "Success");
            MediaScannerConnection.scanFile(ViewCartActivity.this, new String[]{file.toString()}, null, null);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Log: ", "Failed");
        }
    }

    private void getItems() {
        class GetItem extends AsyncTask<Void, Void, List<Products>> {

            @Override
            protected List<Products> doInBackground(Void... voids) {
                List<Products> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Products> tasks) {
                super.onPostExecute(tasks);
                adapter = new CartAdapter(tasks,ViewCartActivity.this);
                recyclerView.setAdapter(adapter);
            }
        }

        GetItem gi = new GetItem();
        gi.execute();

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

                if(price.getText().toString().equals("")) {
                    linear.setVisibility(View.INVISIBLE);
                    finish();
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
                    qty.setText(String.valueOf(mCartItemCount)+" Item");
                }catch (Exception e){}
            }
        }

        GetUnit gu = new GetUnit();
        gu.execute();

    }
}