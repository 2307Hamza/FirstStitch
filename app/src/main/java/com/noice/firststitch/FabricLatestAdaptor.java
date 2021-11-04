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

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class FabricLatestAdaptor extends RecyclerView.Adapter<FabricLatestAdaptor.FabricLatestViewHolder> {
    private Context context;
    private List<Fabric> fabricList;

    public FabricLatestAdaptor(List<Fabric> fabricList, Context context) {
        this.fabricList = fabricList;
        this.context = context;
    }

    @NonNull
    @Override
    public FabricLatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.latestfabricrow,parent,false);
        return new FabricLatestViewHolder(row,context);
    }

    @Override
    public void onBindViewHolder(@NonNull FabricLatestViewHolder holder, int position) {
        g.setRvCurrentIndex(position);
        g.setRvSize(fabricList.size());
        holder.fabricName.setText(fabricList.get(position).getName());
        holder.fabricDesc.setText(fabricList.get(position).getDescription());
        //Glide.with(context).load(fabricList.get(position).getImageURL()).into(holder.fabricImage);
        Picasso.get().load(fabricList.get(position).getImageURL()).transform(new RoundedCornersTransformation(20,0)).into(holder.fabricImage);
    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }

    public class FabricLatestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView fabricImage;
        TextView fabricName;
        TextView fabricDesc;
        Context context;
        public FabricLatestViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            fabricImage= (ImageView) itemView.findViewById(R.id.imageViewLatestFabricPic);
            fabricName=itemView.findViewById(R.id.rowLatestFabricName);
            fabricDesc=itemView.findViewById(R.id.rowLatestFabricDesc);

            this.context=context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            g.setIsEditing(true);
            g.setFabric(fabricList.get(getAdapterPosition()));
            g.setFabricID(g.getFabric().getFabricID());
            Intent intent=new Intent(context,FabricDetailsCustomer.class);
            context.startActivity(intent);
            return;

        }
    }
}
