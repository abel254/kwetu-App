package com.project.kwetu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.kwetu.R;
import com.project.kwetu.model.ChatModel;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    Context mContext;
    List<ChatModel> chatModels;

    public ChatAdapter(Context context, ArrayList<ChatModel> models){
        mContext = context;
        chatModels = models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_chart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.senderName.setText(chatModels.get(position).getSender());
        holder.suggestion.setText(chatModels.get(position).getSuggestion());
        holder.suggestionTime.setText(chatModels.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView senderName, suggestion, suggestionTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            senderName = itemView.findViewById(R.id.sender_name);
            suggestion = itemView.findViewById(R.id.suggestion);
            suggestionTime = itemView.findViewById(R.id.suggestion_time);
        }
    }
}

