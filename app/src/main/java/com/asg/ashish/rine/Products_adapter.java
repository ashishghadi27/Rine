package com.asg.ashish.rine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
        private CardView cv;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bluetext);
            countertext = (TextView)view.findViewById(R.id.counter);
            imgbutton = (ImageView) view.findViewById(R.id.blueberry);
            lh = (TextView) view.findViewById(R.id.linkholder);
            context = view.getContext();
            cv = (CardView)view.findViewById(R.id.card_view);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            //Toast.makeText(v.getContext(),countertext.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), blueberry.class);

            i.putExtra("productid", countertext.getText());
            i.putExtra("title", title.getText());
            i.putExtra("link", lh.getText());
            //v.getContext().startActivity(i);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,imgbutton, ViewCompat.getTransitionName(imgbutton));
            v.getContext().startActivity(i,optionsCompat.toBundle());

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
        YoYo.with(Techniques.FadeIn).playOn(holder.cv);
        if(list.getTitle().contains("Blueberry")){
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1565c0")));
        }
        else if(list.getTitle().contains("Strawberry")){

            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f50057")));
        }
        else if(list.getTitle().contains("Peanut")){

            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
        }
        else if(list.getTitle().contains("Chocolate")){

            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4e342e")));
        }
        else {

            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1de9b6")));
        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

