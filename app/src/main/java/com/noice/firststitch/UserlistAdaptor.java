package com.noice.firststitch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserlistAdaptor extends RecyclerView.Adapter<UserlistAdaptor.UserlistViewholder> {
    List<User> userList;

    public UserlistAdaptor(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.userrow,parent,false);
        return new UserlistViewholder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull UserlistViewholder holder, int position) {

        holder.username.setText(userList.get(position).getUsername());
        holder.phone.setText(userList.get(position).getPhone());
        holder.type.setText(userList.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserlistViewholder extends RecyclerView.ViewHolder {

        TextView username;
        TextView phone;
        TextView type;

        public UserlistViewholder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.rowusername);
            phone=itemView.findViewById(R.id.rowphone);
            type=itemView.findViewById(R.id.rowusertype);
        }
    }
}
