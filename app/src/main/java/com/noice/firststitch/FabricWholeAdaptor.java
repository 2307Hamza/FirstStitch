package com.noice.firststitch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class FabricWholeAdaptor extends RecyclerView.Adapter<FabricWholeAdaptor.FabricWholeViewHolder> {
    private Context context;
    private List<Fabric> fabricList;

    public FabricWholeAdaptor(List<Fabric> fabricList,Context context) {
        this.fabricList = fabricList;
        this.context = context;
    }

    @NonNull
    @Override
    public FabricWholeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.fabricwholerow,parent,false);
        return new FabricWholeViewHolder(row,context);
    }

    @Override
    public void onBindViewHolder(@NonNull FabricWholeViewHolder holder, int position) {
        holder.fabricName.setText(fabricList.get(position).getName());
        //Glide.with(context).load(fabricList.get(position).getImageURL()).into(holder.fabricImage);
        Picasso.get().load(fabricList.get(position).getImageURL()).transform(new RoundedCornersTransformation(30,0)).into(holder.fabricImage);
    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }

    public class FabricWholeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView fabricImage;
        TextView fabricName;
        Context context;
        public FabricWholeViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            fabricImage= (ImageView) itemView.findViewById(R.id.imageViewFabricItem);
            fabricName=itemView.findViewById(R.id.faricItemName);

            this.context=context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(g.getUserType().compareTo("admin")==0){
                g.setIsEditing(true);
                g.setFabric(fabricList.get(getAdapterPosition()));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(context,FabricDetails.class);
                context.startActivity(intent);
                return;
            }else if(g.getUserType().compareTo("customer")==0){
                g.setIsComingFromAllFabric(true);
                g.setFabric(fabricList.get(getAdapterPosition()));
                g.setFabricID(g.getFabric().getFabricID());
                Intent intent=new Intent(context,FabricDetailsCustomer.class);
                context.startActivity(intent);
                return;
            }

        }
    }
}
