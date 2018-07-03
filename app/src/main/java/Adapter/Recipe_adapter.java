package Adapter;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asg.ashish.rine.R;
import com.asg.ashish.rine.loadl_link;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Interface.ItemClickListener;
import Model.Recipe_list;

public class Recipe_adapter extends RecyclerView.Adapter<Recipe_adapter.MyViewHolder>  {

    private List<Recipe_list> listItems;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;
    String passlink;
    Context context;






    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, desc, date, link;
        public ImageView img;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtTitle);
            date = (TextView)view.findViewById(R.id.publishdate);
            img = (ImageView) view.findViewById(R.id.feedimage);
            desc = (TextView) view.findViewById(R.id.txtcontent);
            link = (TextView)view.findViewById(R.id.linktext);
            context = view.getContext();
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            passlink = link.getText().toString();
            Log.v(TAG, "inhere in item click listener "+passlink);
            Context c = v.getContext();
            Intent link = new Intent(c, loadl_link.class);
            link.putExtra("link",passlink);
            c.startActivity(link);

        }
    }


    public Recipe_adapter(List<Recipe_list> title, Context context) {
        this.listItems = title;
        this.context = context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recipe_list list = listItems.get(position);
        holder.title.setText(list.getTitle());
        holder.link.setText(list.getBrowser_link());
        holder.date.setText(list.getDate());
        String info = list.getDesc();
        info = info.replaceAll("\\<[^>]*>", "");
        info = info.replaceAll("\n","");
        info = info.replaceAll(";",".");
        holder.desc.setText(info);
        Glide.with(context).load(list.getLink()).override(700,400).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

