package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asg.ashish.rine.Order_info;
import com.asg.ashish.rine.R;

import java.util.List;

import Interface.ItemClickListener;
import Model.Orders_list;

import static android.content.Context.MODE_PRIVATE;

public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.MyViewHolder> {

    private List<Orders_list> listItems;
    private Context context;
    public String id;
    private ItemClickListener itemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView order_id, date, status, total;


        public MyViewHolder(View view) {
            super(view);
            order_id = view.findViewById(R.id.orderid);
            date = view.findViewById(R.id.date);
            status = view.findViewById(R.id.status);
            total = view.findViewById(R.id.total);
            context = view.getContext();
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            //Toast.makeText(v.getContext(),"Clicked", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(v.getContext(), Order_info.class);
            i.putExtra("orderid", order_id.getText());
            i.putExtra("finaltotal", total.getText());
            context.startActivity(i);

        }
    }


    public Orders_Adapter(List<Orders_list> title, Context context) {
        this.listItems = title;
        this.context = context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_pro, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //YoYo.with(Techniques.StandUp).playOn(holder.cardView);
        final Orders_list list = listItems.get(position);
        holder.order_id.setText(list.getOrder_id());
        holder.status.setText(list.getStatus());
        holder.date.setText(list.getDate());
        String total = "\u20B9 "+list.getTotal();
        Log.v("THE PRODUCT JSON", list.getProduct());
        holder.total.setText(total);
        String orderid = list.getOrder_id();
        SharedPreferences sharedPreferences = context.getSharedPreferences(orderid,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("productinfo", list.getProduct());
        editor.putString("address1", list.getAddress_1());
        editor.putString("address2", list.getAddress_2());
        editor.putString("city", list.getCity());
        editor.putString("postcode", list.getPostcode());
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

