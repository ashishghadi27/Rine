package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asg.ashish.rine.Cart_Activity;
import com.asg.ashish.rine.R;

import java.util.List;

import Database.DBHandler;
import Interface.ItemClickListener;
import Model.Cart_list;

import static android.content.Context.MODE_PRIVATE;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder> {

    private List<Cart_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;
    DBHandler dbHandler;
    public int total_price = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, countertext, lh, count, Remove;
        public ImageView imgbutton;
        CardView cardView;
        private TextView price_button;
        private TextView total_set;



        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.bluetext);
            countertext = view.findViewById(R.id.counter);
            //imgbutton = (ImageView) view.findViewById(R.id.blueberry);
            lh = view.findViewById(R.id.linkholder);
            count = view.findViewById(R.id.count);
            Remove = view.findViewById(R.id.remove);
            price_button = view.findViewById(R.id.product_price);
            dbHandler = new DBHandler(view.getContext(), null, null, 2);
            cardView = view.findViewById(R.id.card_view);
            total_set = view.findViewById(R.id.price_set);




        }

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
        //YoYo.with(Techniques.StandUp).playOn(holder.cardView);
        final Cart_list list = listItems.get(position);
        holder.title.setText(list.getTitle());
        holder.countertext.setText(list.getId());
        holder.lh.setText(list.getImg());
        //Glide.with(context).load(list.getImg()).override(1000,500).into(holder.imgbutton);
        holder.count.setText(list.getCount());
        Log.v("ID IN ADAPTER IS",list.getId());
        final SharedPreferences sharedPreferences = context.getSharedPreferences("price", MODE_PRIVATE);
        String price = sharedPreferences.getString(list.getTitle(), "360");
        int p_price = Integer.parseInt(holder.count.getText().toString()) * Integer.parseInt(price);
        total_price = total_price + p_price;
        price = "Rs " +price;
        holder.price_button.setText(price);

        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = holder.countertext.getText().toString();
                Log.v("ID HOLDER IS:", id);
                dbHandler.deleteProduct(id);
                holder.cardView.setVisibility(View.GONE);
                total_price = total_price - Integer.parseInt(holder.count.getText().toString()) * Integer.parseInt(sharedPreferences.getString(holder.title.getText().toString(), "360"));
                SharedPreferences sharedPreferences1 = context.getSharedPreferences("totalprice",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("tp", String.valueOf(total_price));
                editor.apply();
                SharedPreferences sharedPreferences2 = context.getSharedPreferences("totalprice", MODE_PRIVATE);
                String cp = sharedPreferences1.getString("tp", "0");
                Log.v("UPDATED PRICE", cp);
               /* Intent intent = new Intent(context, Cart_Activity.class);
                v.getContext().startActivity(intent);*/
                ((Cart_Activity)v.getContext()).recreate();


            }
        });

        if(list.getTitle().contains("Blueberry")){
            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1565c0")));
        }
        else if(list.getTitle().contains("Strawberry")){

            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f50057")));
        }
        else if(list.getTitle().contains("Peanut")){

            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
        }
        else if(list.getTitle().contains("Chocolate")){

            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4e342e")));
        }
        else {

            holder.cardView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1de9b6")));
        }

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("totalprice", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("tp", Integer.toString(total_price));
        editor.apply();


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

