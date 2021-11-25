package com.noice.firststitch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TailorlistAdaptor extends RecyclerView.Adapter<TailorlistAdaptor.TailorlistViewholder> {
    /*declaring required variables for the adaptor*/
    List<Tailor> tailorList;
    Context context;

    /*adaptor constructor*/
    public TailorlistAdaptor(List<Tailor> tailorList, Context context) {
        this.tailorList = tailorList;
        this.context = context;
    }

    @NonNull
    @Override
    public TailorlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*inflating row in recycler view*/
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.tailorrow,parent,false);
        return new TailorlistViewholder(row,context);
    }

    @Override
    public void onBindViewHolder(@NonNull TailorlistViewholder holder, int position) {
        /*binding date onto the recycler view*/

        holder.username.setText(tailorList.get(position).getUsername());
        holder.phone.setText(tailorList.get(position).getPhone());
        int specialityCode = tailorList.get(position).getSpeciality();
        if(specialityCode==0){
            holder.speciality.setText("none");
        }else if(specialityCode==1){
            holder.speciality.setText("Ladies clothing");
        }else if(specialityCode==2){
            holder.speciality.setText("Gents clothing");
        }else if(specialityCode==3){
            holder.speciality.setText("Children clothing");
        }else if(specialityCode==4){
            holder.speciality.setText("Ladies & Gents clothing");
        }else if(specialityCode==5){
            holder.speciality.setText("Ladies & Children clothing");
        }else if(specialityCode==6){
            holder.speciality.setText("Gents & Children clothing");
        }else if(specialityCode==7){
            holder.speciality.setText("Ladies, Gents & Children clothing");
        }else {
            holder.speciality.setText("unrecognizable code, please contact admin");
        }
        String address = "'" + tailorList.get(position).getAddress() +"'";
        holder.address.setText(address);
    }

    @Override
    public int getItemCount() {
        return tailorList.size();
    }

    public class TailorlistViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username;
        TextView phone;
        TextView speciality;
        TextView address;
        Context context;
        public TailorlistViewholder(@NonNull View itemView,Context context) {
            super(itemView);

            username=itemView.findViewById(R.id.rowtailorname);
            phone=itemView.findViewById(R.id.rowtailorrphone);
            speciality=itemView.findViewById(R.id.rowtailorspeciality);
            address=itemView.findViewById(R.id.rowtailoraddress);

            this.context=context;
            itemView.setOnClickListener(this);
        }

        /*navigating to next activity if user confirms the tailor*/
        @Override
        public void onClick(View v) {
            //Toast.makeText(context,"tailor selected",Toast.LENGTH_SHORT).show();
            g.setTailor(tailorList.get(getAdapterPosition()));
            g.setTailorID(g.getTailor().getTailorID());
            Intent intent=new Intent(context,ConfirmOrder.class);
            context.startActivity(intent);
            return;

        }
    }

}
