package com.project.kwetu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.kwetu.ChatActivity;
import com.project.kwetu.R;
import com.project.kwetu.model.Users;
import com.squareup.picasso.Picasso;

import java.util.List;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.myHolder> {

    Context mContext;
    List<Users> mList;

    public usersAdapter(Context context, List<Users> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_users, parent, false);


        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {

        final String herUid = mList.get(position).getUid();
        String userImage = mList.get(position).getImage();
        String textView = mList.get(position).getTenant_name();
        String textView1 = mList.get(position).getHouse_number();

        holder.nameTv.setText(textView);
        holder.houseTv.setText(textView1);
        try{
            Picasso.get().load(userImage)
                    .placeholder(R.drawable.ic_face)
                    .into(holder.avatar);
        }
        catch (Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("herUid", herUid);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class myHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView nameTv;
        TextView houseTv;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            nameTv = itemView.findViewById(R.id.nameTv);
            houseTv = itemView.findViewById(R.id.houseTv);
        }
    }
}
