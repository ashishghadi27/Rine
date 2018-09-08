package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asg.ashish.rine.R;

import java.util.List;

import Interface.ItemClickListener;
import Model.Order_temp_list;

public class Order_temp_adapter extends RecyclerView.Adapter<Order_temp_adapter.MyViewHolder> {

    private List<Order_temp_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView product, quantity, subtotal;


        public MyViewHolder(View view) {
            super(view);
            product = view.findViewById(R.id.order_temp_pro);
            quantity = view.findViewById(R.id.order_temp_quantity);
            subtotal = view.findViewById(R.id.order_temp_subtotal);

        }

    }


    public Order_temp_adapter(List<Order_temp_list> title, Context context) {
        this.listItems = title;
        this.context = context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_template, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //YoYo.with(Techniques.StandUp).playOn(holder.cardView);
        final Order_temp_list list = listItems.get(position);
        holder.product.setText(list.getProductname());
        holder.quantity.setText(list.getQuantity());
        String total = "\u20B9 "+list.getSubtotal();
        holder.subtotal.setText(total);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}

