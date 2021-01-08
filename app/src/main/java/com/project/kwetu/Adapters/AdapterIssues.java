package com.project.kwetu.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.kwetu.R;
import com.project.kwetu.model.Issues;
import com.squareup.picasso.Picasso;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterIssues extends RecyclerView.Adapter<AdapterIssues.MyHolder> {


    Context context;
    List<Issues> issuesList;

    public AdapterIssues(Context context, List<Issues> issuesList) {
        this.context = context;
        this.issuesList = issuesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_issues, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        
        String uid = issuesList.get(position).getUid();
        String uEmail = issuesList.get(position).getuEmail();
        String uName = issuesList.get(position).getuName();
        String uDp = issuesList.get(position).getuDp();
        String pId = issuesList.get(position).getpId();
        String pTitle = issuesList.get(position).getpTitle();
        String pDescription = issuesList.get(position).getpDescription();
        String pImage = issuesList.get(position).getpImage();
        String uHouse = issuesList.get(position).getuHouse();
        String pTimeStamp = issuesList.get(position).getpTime();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyy hh:mm aa", calendar).toString();
        
        holder.uNameTv.setText(uName);
        holder.pTimeTv.setText(pTime);
        holder.pTitleTv.setText(pTitle);
        holder.pDescriptionTv.setText(pDescription);
        holder.uHouseTv.setText(uHouse);

                
        
        try{
            Picasso.get().load(uDp).placeholder(R.drawable.ic_face).into(holder.uPictureIv);
            
        }
        catch(Exception e){
            
        }
        
        if(pImage.equals("noImage")){

            holder.pImageIv.setVisibility(View.GONE);

        }
        else{
            try{
                Picasso.get().load(pImage).into(holder.pImageIv);

            }
            catch(Exception e){



            }
        }


        
        
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();
            }
        });

        holder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "comment", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return issuesList.size();
    }
    
    



    class MyHolder extends RecyclerView.ViewHolder{
        
        
        ImageView uPictureIv, pImageIv;
        TextView  uNameTv,pTimeTv, pTitleTv, pDescriptionTv, uHouseTv;
        ImageButton moreBtn;
        Button commentBtn;
        

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            
            
            uPictureIv = itemView.findViewById(R.id.uPicturetv);
            pImageIv = itemView.findViewById(R.id.pImageIv);
            uNameTv = itemView.findViewById(R.id.uNameTv);
            pTimeTv = itemView.findViewById(R.id.pTimeSv);
            pTitleTv = itemView.findViewById(R.id.pTitleTv);
            pDescriptionTv = itemView.findViewById(R.id.pDescriptionTv);
            uHouseTv = itemView.findViewById(R.id.uHouseTv);
            moreBtn = itemView.findViewById(R.id.moreBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);

        }
    }
}
