package com.example.e_commerce.fetchdata;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.roomdatabase.DatabaseClient;
import com.example.e_commerce.roomdatabase.Products;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    List<String> qtyList;
    Context context;
    ArrayAdapter<String> adapter;
    int counter;
    double price;
    Products product1;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,null,false);
    ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.name.setText(product.getProductName());
        holder.imgurl.setText(product.getProductImage());
     //   holder.tprice.setText("\u20B9"+product.getTprice());
        holder.price.setText(product.getPrice().get(0).getPrice());

        Picasso.get().load(product.getProductImage()).into(holder.img);

        qtyList = new ArrayList<>();
      //  PriceList = new ArrayList<>();

        int size= Integer.parseInt(product.getGetSize());
        for (int i=0;i<size;i++) {
                qtyList.add(String.valueOf(product.getPrice().get(i).getQty()));
            //    PriceList.add(String.valueOf(product.getPrice().get(i).getPrice()));

                adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, qtyList);
                holder.qty.setAdapter(adapter);
        }

//       holder.qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//           @Override
//           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//               int pos= (holder.qty.getSelectedItemPosition());
//               String newprice= String.valueOf(PriceList.get(pos));
////
////               holder.price.setText(PriceList.set(pos,newprice));
//               Toast.makeText(context, ""+newprice, Toast.LENGTH_SHORT).show();


//           }
//
//           @Override
//           public void onNothingSelected(AdapterView<?> adapterView) {
//
//           }
//       });

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter=0;
            //    counter= Integer.parseInt(holder.count.getText().toString());
           //     if(holder.count.getText().toString().equals("0")){
                    holder.addbtn.setVisibility(View.GONE);
                    counter++;
                    holder.count.setText(""+counter);
            //    }
                price=Integer.parseInt(holder.price.getText().toString())*counter;
                holder.tprice.setText(String.valueOf(price));
                holder.total.setVisibility(View.VISIBLE);

                product1 = new Products();
                product1.setName(holder.name.getText().toString());
                product1.setImg(holder.imgurl.getText().toString());
                product1.setQty(holder.qty.getSelectedItem().toString());
                product1.setPrice(holder.price.getText().toString());
                product1.setTprice(String.valueOf(price));
                product1.setUnits(String.valueOf(counter));

                addProduct();
                ((MainActivity) context).getPrice();
                ((MainActivity) context).getQty();
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter= Integer.parseInt(holder.count.getText().toString());
                counter++;
                holder.count.setText(""+counter);

                price=Integer.parseInt(holder.price.getText().toString())*counter;
                holder.tprice.setText(String.valueOf(price));
                holder.total.setVisibility(View.VISIBLE);

                product1 = new Products();
                product1.setName(holder.name.getText().toString());
                product1.setImg(holder.imgurl.getText().toString());
                product1.setQty(holder.qty.getSelectedItem().toString());
                product1.setPrice(holder.price.getText().toString());
                product1.setTprice(String.valueOf(price));
                product1.setUnits(String.valueOf(counter));

                deleteProduct(product1.getName());
                addProduct();
                ((MainActivity) context).getPrice();
                ((MainActivity) context).getQty();
            }
        });

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.parseInt(holder.count.getText().toString());
                counter--;
                holder.count.setText("" + counter);

                price=Integer.parseInt(holder.price.getText().toString())*counter;
                holder.tprice.setText(String.valueOf(price));
                holder.total.setVisibility(View.VISIBLE);

                product1 = new Products();
                product1.setName(holder.name.getText().toString());
                product1.setImg(holder.imgurl.getText().toString());
                product1.setQty(holder.qty.getSelectedItem().toString());
                product1.setPrice(holder.price.getText().toString());
                product1.setTprice(String.valueOf(price));
                product1.setUnits(String.valueOf(counter));

                if(counter==0){
                    holder.addbtn.setVisibility(View.VISIBLE);
                    holder.total.setVisibility(View.INVISIBLE);
                    deleteProduct(product1.getName());
                    ((MainActivity) context).getPrice();
                 //   ((MainActivity) context).getQty();
                } else {
                    holder.total.setVisibility(View.VISIBLE);
                    deleteProduct(product1.getName());
                    addProduct();
                    ((MainActivity) context).getPrice();
                    ((MainActivity) context).getQty();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name,price,add,sub,count,imgurl,tprice;
        Spinner qty;
        Button addbtn;
        LinearLayout total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        img = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            addbtn = itemView.findViewById(R.id.addbtn);
            add = itemView.findViewById(R.id.add);
            sub = itemView.findViewById(R.id.sub);
            count = itemView.findViewById(R.id.count);
            imgurl = itemView.findViewById(R.id.imgurl);
            tprice = itemView.findViewById(R.id.tprice);
            total = itemView.findViewById(R.id.total);

     //      itemView.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View view) {
//            Product product = productList.get(getAdapterPosition());
//
//            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
//            intent.putExtra("task", task);
//            mCtx.startActivity(intent);
//        }

    }

    private void addProduct() {

        class AddProduct extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                ////adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .insert(product1);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        AddProduct st = new AddProduct();
        st.execute();
    }

//    private void updateProduct() {
//
//        class UpdateTask extends AsyncTask<Void, Void, Void> {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                DatabaseClient.getInstance(context).getAppDatabase()
//                        .taskDao()
//                        .update(product1);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();
////                finish();
////                startActivity(new Intent(UpdateTaskActivity.this, MainScreen.class));
//            }
//        }
//
//        UpdateTask ut = new UpdateTask();
//        ut.execute();
//    }

    private void deleteProduct(final String task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getAppDatabase()
                        .taskDao()
                        .deleteByName(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
