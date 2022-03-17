package com.example.e_commerce.viewcart;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.MainActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.fetchdata.Product;
import com.example.e_commerce.roomdatabase.DatabaseClient;
import com.example.e_commerce.roomdatabase.Products;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Products> cartList;
    Context context;
    int counter;
    double price;
    Products product1;

    public CartAdapter(List<Products> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,null,false);
    ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Products cartItem = cartList.get(position);

        holder.name.setText(cartItem.getName());
        holder.price.setText(cartItem.getPrice());
        holder.tprice.setText("\u20B9"+cartItem.getTprice());
        holder.qty.setText(cartItem.getQty());
        holder.count.setText(cartItem.getUnits());

        Picasso.get().load(cartItem.getImg()).into(holder.img);

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
                product1.setImg(holder.name.getText().toString());
                product1.setQty(holder.qty.getText().toString());
                product1.setPrice(holder.price.getText().toString());
                product1.setTprice(String.valueOf(price));
                product1.setUnits(String.valueOf(counter));

                deleteProduct(product1.getName());
                addProduct();
                ((ViewCartActivity) context).getPrice();
                ((ViewCartActivity) context).getQty();
            }
        });

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter= Integer.parseInt(holder.count.getText().toString());
                counter--;
                holder.count.setText("" + counter);

                price=Integer.parseInt(holder.price.getText().toString())*counter;
                holder.tprice.setText(String.valueOf(price));
                holder.total.setVisibility(View.VISIBLE);

                product1 = new Products();
                product1.setName(holder.name.getText().toString());
                product1.setImg(holder.name.getText().toString());
                product1.setQty(holder.qty.getText().toString());
                product1.setPrice(holder.price.getText().toString());
                product1.setTprice(String.valueOf(price));
                product1.setUnits(String.valueOf(counter));

                if(counter==0){
                    deleteProduct(product1.getName());
                    cartList.remove(cartItem);
                    notifyItemRemoved(position);
               //     notifyDataSetChanged();
                    ((ViewCartActivity) context).getPrice();
                    ((ViewCartActivity) context).getQty();
                } else {

                    deleteProduct(product1.getName());
                    addProduct();
                    ((ViewCartActivity) context).getPrice();
                    ((ViewCartActivity) context).getQty();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name,price,add,sub,count,qty,tprice;
        LinearLayout total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            tprice = itemView.findViewById(R.id.tprice);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            add = itemView.findViewById(R.id.add);
            sub = itemView.findViewById(R.id.sub);
            count = itemView.findViewById(R.id.count);
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
