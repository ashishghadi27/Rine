package com.asg.ashish.rine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Interface.ItemClickListener;

public class Products_adapter extends RecyclerView.Adapter<Products_adapter.MyViewHolder>  {

    private List<Products_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;





    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, countertext, lh;
        public ImageView imgbutton;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bluetext);
            countertext = (TextView)view.findViewById(R.id.counter);
            imgbutton = (ImageView) view.findViewById(R.id.blueberry);
            lh = (TextView) view.findViewById(R.id.linkholder);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            //Toast.makeText(v.getContext(),countertext.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), blueberry.class);

            i.putExtra("productid", countertext.getText());
            i.putExtra("title", title.getText());
            i.putExtra("link", lh.getText());
            v.getContext().startActivity(i);

        }
    }


        public Products_adapter(List<Products_list> title, Context context) {
            this.listItems = title;
            this.context = context;


        }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listproducts, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products_list list = listItems.get(position);
        holder.title.setText(list.getTitle());
        holder.countertext.setText(list.getId());
        holder.lh.setText(list.getImg());
        Glide.with(context).load(list.getImg()).override(1000,500).into(holder.imgbutton);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

