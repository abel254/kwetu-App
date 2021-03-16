package com.project.kwetu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.kwetu.R;
import com.project.kwetu.model.Tenant;

import java.util.List;

public class NeighboursAdapter extends RecyclerView.Adapter<NeighboursAdapter.myViewHolder>{
    Context mContext;
    List<Tenant> tenants;

    public NeighboursAdapter(Context context, List<Tenant> tenant){
        mContext = context;
        tenants = tenant;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_neighbours, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.name.setText(tenants.get(position).getName());
        holder.houseNumber.setText(tenants.get(position).getHouseNumber());
        holder.phoneNumber.setText(tenants.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return tenants.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView name, houseNumber, phoneNumber;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            houseNumber = itemView.findViewById(R.id.house_number);
            phoneNumber = itemView.findViewById(R.id.phone_number);
        }
    }
}
