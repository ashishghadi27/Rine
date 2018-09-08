package com.asg.ashish.rine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import Interface.ItemClickListener;

import static android.content.Context.MODE_PRIVATE;

public class Products_adapter extends RecyclerView.Adapter<Products_adapter.MyViewHolder>  {

    private List<Products_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;
    private String imga[] = new String[5];






    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, countertext, lh;
        public ImageView imgbutton;
        private CardView cv;
        private Button price;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.bluetext);
            countertext = view.findViewById(R.id.counter);
            imgbutton = view.findViewById(R.id.blueberry);
            lh = view.findViewById(R.id.linkholder);
            context = view.getContext();
            cv = view.findViewById(R.id.card_view);
            price = view.findViewById(R.id.price);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            //Toast.makeText(v.getContext(),countertext.getText(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), blueberry.class);

            SharedPreferences sharedPreferences = context.getSharedPreferences("sendpro", MODE_PRIVATE);
            String imga = sharedPreferences.getString(title.getText().toString()+"a","");
            String imgb = sharedPreferences.getString(title.getText().toString()+"b","");
            String imgc = sharedPreferences.getString(title.getText().toString()+"c","");
            String imgd = sharedPreferences.getString(title.getText().toString()+"d","");
            String imgdesc = sharedPreferences.getString(title.getText().toString()+"desc","");

            i.putExtra("productid", countertext.getText());
            i.putExtra("title", title.getText());
            i.putExtra("link1", imga);
            i.putExtra("link2", imgb);
            i.putExtra("link3", imgc);
            i.putExtra("link4", imgd);
            i.putExtra("description", imgdesc);
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
        //holder.lh.setText(list.getImg());
        //imga  = new String []{list.getImg1(), list.getImg2(), list.getImg3(), list.getImg4(), list.getDescription()};
        SharedPreferences sharedPreferences = context.getSharedPreferences("sendpro", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(list.getTitle()+ "a", list.getImg1() );
        editor.putString(list.getTitle()+ "b", list.getImg2() );
        editor.putString(list.getTitle()+ "c", list.getImg3() );
        editor.putString(list.getTitle()+ "d", list.getImg4() );
        editor.putString(list.getTitle()+ "desc", list.getDescription() );
        editor.apply();

        Log.v("GET IMG", list.getImg1());
        if(Integer.parseInt(list.getRegular_price()) > Integer.parseInt(list.getSale_price())){
            holder.price.setText("Rs " + list.getSale_price());
            SharedPreferences sharedPreferences1 = context.getSharedPreferences("price",MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor.putString(list.getTitle(), list.getSale_price());
            editor.apply();
        }
        else{
            holder.price.setText("Rs " + list.getRegular_price());
            SharedPreferences sharedPreferences1 = context.getSharedPreferences("price",MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor.putString(list.getTitle(), list.getRegular_price());
            editor.apply();
        }


        Glide.with(context).load(list.getImg1()).override(1000,500).placeholder(R.drawable.sampleprofile).error(R.drawable.sampleprofile).animate(R.anim.fadein).into(holder.imgbutton);
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

