package com.example.e_commerce.viewcart;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.roomdatabase.DatabaseClient;
import com.example.e_commerce.roomdatabase.Products;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
    List<Products> outputData;

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
       // addToText();
        addToExcel();
    }

    private void addToExcel() {

        outputData = new ArrayList<>();
        outputData = DatabaseClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .taskDao()
                .getAll();

        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet
            int rownum = 0;
            for (Products products : outputData)
            {
                Row row = sheet.createRow(rownum++);
                createList(products, row);
            }

            File pathToExternalStorage = Environment.getExternalStorageDirectory();
            File appDirectory = new File(pathToExternalStorage.getAbsolutePath() + "/ECommerce/" + "/");

            if (!appDirectory.exists()) {
                boolean mkdir = appDirectory.mkdir();
                if (!mkdir){
                    Toast.makeText(getApplicationContext(), "File Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }
            File file= new File(appDirectory, "Output "+new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())+".xls");
            FileOutputStream os = null;

            try {
                os = new FileOutputStream(file);
                workbook.write(os);
                Log.w("FileUtils", "Writing file" + file);
            } catch (IOException e) {
                Log.w("FileUtils", "Error writing " + file, e);
            } catch (Exception e) {
                Log.w("FileUtils", "Failed to save file", e);
            } finally {
                try {
                    if (null != os)
                        os.close();
                } catch (Exception ex) {
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static void createList(Products products, Row row) // creating cells for each row
    {
        Cell cell = row.createCell(0);
        cell.setCellValue(products.getName());

        cell = row.createCell(1);
        cell.setCellValue(products.getPrice());

        cell = row.createCell(2);
        cell.setCellValue(products.getQty());

        cell = row.createCell(3);
        cell.setCellValue(products.getTprice());

        cell = row.createCell(4);
        cell.setCellValue(products.getUnits());

        cell = row.createCell(5);
        cell.setCellValue(products.getImg());

    }

    private void addToText() {

        List<Products> scannedOutputList1 = new ArrayList<>();

        scannedOutputList1 = DatabaseClient
                .getInstance(getApplicationContext())
                .getAppDatabase()
                .taskDao()
                .getAll();

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
                Products product1 = new Products();
                product1.setName(scannedOutputList1.get(i).getName());
                product1.setImg(scannedOutputList1.get(i).getName());
                product1.setQty(scannedOutputList1.get(i).getQty());
                product1.setPrice(scannedOutputList1.get(i).getPrice());
                product1.setTprice(scannedOutputList1.get(i).getTprice());
                product1.setUnits(scannedOutputList1.get(i).getUnits());
                writer.write(String.valueOf(product1));
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