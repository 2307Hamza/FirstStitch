package com.noice.firststitch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.PointerIconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderlistAdaptor extends RecyclerView.Adapter<OrderlistAdaptor.OrderlistViewholder> {
    private List<Order> orderList;
    private Context context;

    public OrderlistAdaptor(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrow,parent,false);
        return new OrderlistViewholder(row,context);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderlistViewholder holder, int position) {

        //while (orderList.size()<1 || tailorList.size()<1 || customerList.size()<1 || fabricList.size()<1);

        if(g.getUserType().compareTo("customer")==0){
            if(orderList.get(position).getCustomer().getCustomerID().compareTo(g.getCustomerID())==0){
                holder.tailorname.setText(orderList.get(position).getTailor().getUsername());
                holder.tailorphone.setText(orderList.get(position).getTailor().getPhone());
                holder.customername.setText(orderList.get(position).getCustomer().getUsername());
                holder.customerphone.setText(orderList.get(position).getCustomer().getPhone());
                holder.cost.setText(orderList.get(position).getCost());
                holder.status.setText(orderList.get(position).getStatus());

                if(orderList.get(position).getStatus().compareTo("completed")==0){
                    holder.orderStatusImage.setImageResource(R.drawable.ic_baseline_done_outline_24);
                    holder.orderImage.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                }

                holder.customername.setVisibility(View.GONE);
                holder.customerphone.setVisibility(View.GONE);
                holder.customerphoneicon.setVisibility(View.GONE);
            }else{
                holder.orderItem.setVisibility(View.GONE);
            }
        }else if(g.getUserType().compareTo("tailor")==0){
            if(orderList.get(position).getTailor().getTailorID().compareTo(g.getTailorID())==0){

                if(g.getOrderFragmentType().compareTo("pending")==0){
                    if(orderList.get(position).getStatus().compareTo("pending")==0){
                        holder.tailorname.setText(orderList.get(position).getTailor().getUsername());
                        holder.tailorphone.setText(orderList.get(position).getTailor().getPhone());
                        holder.customername.setText(orderList.get(position).getCustomer().getUsername());
                        holder.customerphone.setText(orderList.get(position).getCustomer().getPhone());
                        holder.cost.setText(orderList.get(position).getCost());
                        holder.status.setText(orderList.get(position).getStatus());

                        if(orderList.get(position).getStatus().compareTo("completed")==0){
                            holder.orderStatusImage.setImageResource(R.drawable.ic_baseline_done_outline_24);
                            holder.orderImage.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                        }
                    }else{
                        holder.orderItem.setVisibility(View.GONE);
                    }
                }else if(g.getOrderFragmentType().compareTo("completed")==0){
                    if(orderList.get(position).getStatus().compareTo("completed")==0){
                        holder.tailorname.setText(orderList.get(position).getTailor().getUsername());
                        holder.tailorphone.setText(orderList.get(position).getTailor().getPhone());
                        holder.customername.setText(orderList.get(position).getCustomer().getUsername());
                        holder.customerphone.setText(orderList.get(position).getCustomer().getPhone());
                        holder.cost.setText(orderList.get(position).getCost());
                        holder.status.setText(orderList.get(position).getStatus());

                        if(orderList.get(position).getStatus().compareTo("completed")==0){
                            holder.orderStatusImage.setImageResource(R.drawable.ic_baseline_done_outline_24);
                            holder.orderImage.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                        }
                    }else{
                        holder.orderItem.setVisibility(View.GONE);
                    }
                }
                holder.tailorname.setVisibility(View.GONE);
                holder.tailorphone.setVisibility(View.GONE);
                holder.tailorphoneicon.setVisibility(View.GONE);
            }else{
                holder.orderItem.setVisibility(View.GONE);
            }
        }else if(g.getUserType().compareTo("admin")==0){
            if(g.getOrderFragmentType().compareTo("pending")==0){
                if(orderList.get(position).getStatus().compareTo("pending")==0){
                    holder.tailorname.setText(orderList.get(position).getTailor().getUsername());
                    holder.tailorphone.setText(orderList.get(position).getTailor().getPhone());
                    holder.customername.setText(orderList.get(position).getCustomer().getUsername());
                    holder.customerphone.setText(orderList.get(position).getCustomer().getPhone());
                    holder.cost.setText(orderList.get(position).getCost());
                    holder.status.setText(orderList.get(position).getStatus());

                    if(orderList.get(position).getStatus().compareTo("completed")==0){
                        holder.orderStatusImage.setImageResource(R.drawable.ic_baseline_done_outline_24);
                        holder.orderImage.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                    }
                }else{
                    holder.orderItem.setVisibility(View.GONE);
                }
            }else if(g.getOrderFragmentType().compareTo("completed")==0){
                if(orderList.get(position).getStatus().compareTo("completed")==0){
                    holder.tailorname.setText(orderList.get(position).getTailor().getUsername());
                    holder.tailorphone.setText(orderList.get(position).getTailor().getPhone());
                    holder.customername.setText(orderList.get(position).getCustomer().getUsername());
                    holder.customerphone.setText(orderList.get(position).getCustomer().getPhone());
                    holder.cost.setText(orderList.get(position).getCost());
                    holder.status.setText(orderList.get(position).getStatus());

                    if(orderList.get(position).getStatus().compareTo("completed")==0){
                        holder.orderStatusImage.setImageResource(R.drawable.ic_baseline_done_outline_24);
                        holder.orderImage.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                    }
                }else{
                    holder.orderItem.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderlistViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tailorname;
        TextView tailorphone;
        TextView customername;
        TextView customerphone;
        TextView cost;
        TextView status;
        ImageView orderImage;
        ImageView orderStatusImage;
        ImageView tailorphoneicon;
        ImageView customerphoneicon;

        ConstraintLayout orderItem;
        Context context;
        public OrderlistViewholder(@NonNull View itemView, Context context) {
            super(itemView);

            tailorname=itemView.findViewById(R.id.rowtailorname);
            tailorphone=itemView.findViewById(R.id.rowtailorrphone);
            customername=itemView.findViewById(R.id.rowcustomername);
            customerphone=itemView.findViewById(R.id.rowcustomerphone);
            cost=itemView.findViewById(R.id.rowordercost);
            status=itemView.findViewById(R.id.roworderstatus);
            orderImage=(ImageView)itemView.findViewById(R.id.imageViewOrderPic);
            orderStatusImage=(ImageView)itemView.findViewById(R.id.imageViewStatus);
            tailorphoneicon=(ImageView)itemView.findViewById(R.id.tailorphoneicon);
            customerphoneicon=(ImageView)itemView.findViewById(R.id.customerphoneicon);
            orderItem=(ConstraintLayout) itemView.findViewById(R.id.orderItem);

            this.context=context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            g.setOrder(orderList.get(getAdapterPosition()));
            //Toast.makeText(context,"order clicked",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(context,OrderDetails.class);
            context.startActivity(intent);
            return;
        }
    }
}
