package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asg.ashish.rine.Cart_Activity;
import com.asg.ashish.rine.Products_list;
import com.asg.ashish.rine.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Database.DBHandler;
import Interface.ItemClickListener;
import Model.Cart_list;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder> {

    private List<Cart_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;
    DBHandler dbHandler;





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, countertext, lh, count, Remove;
        public ImageView imgbutton;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bluetext);
            countertext = (TextView)view.findViewById(R.id.counter);
            imgbutton = (ImageView) view.findViewById(R.id.blueberry);
            lh = (TextView) view.findViewById(R.id.linkholder);
            count = (TextView)view.findViewById(R.id.count);
            Remove = (TextView) view.findViewById(R.id.remove);
            dbHandler = new DBHandler(view.getContext(), null, null, 2);
            cardView = (CardView)view.findViewById(R.id.card_view);



        }


        /*@Override
        public void onClick(View v) {

            //Toast.makeText(v.getContext(),countertext.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), blueberry.class);

            i.putExtra("productid", countertext.getText());
            i.putExtra("title", title.getText());
            i.putExtra("link", lh.getText());
            v.getContext().startActivity(i);

        }*/
    }


    public Cart_Adapter(List<Cart_list> title, Context context) {
        this.listItems = title;
        this.context = context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cartproducts, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Cart_list list = listItems.get(position);
        holder.title.setText(list.getTitle());
        holder.countertext.setText(list.getId());
        holder.lh.setText(list.getImg());
        Glide.with(context).load(list.getImg()).override(1000,500).into(holder.imgbutton);
        holder.count.setText(list.getCount());
        Log.v("ID IN ADAPTER IS",list.getId());
        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = holder.countertext.getText().toString();
                Log.v("ID HOLDER IS:", id);
                dbHandler.deleteProduct(id);
                holder.cardView.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

