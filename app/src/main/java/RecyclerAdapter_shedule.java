package com.example.book_inbeta1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter_shedule extends RecyclerView.Adapter<RecyclerAdapter_shedule.ViewHolder1> {

    private List<String> list;
    public RecyclerAdapter_shedule(List<String> list){
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TextView textView = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schedule_tv,parent,false);
        ViewHolder1 viewHolder1 = new ViewHolder1(textView);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        holder.VersionName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView VersionName;
        public ViewHolder1(TextView itemView){
            super(itemView);
            VersionName = itemView;
        }
    }
}
