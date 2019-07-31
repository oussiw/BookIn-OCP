package com.example.book_inbeta1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter_rooms extends RecyclerView.Adapter<RecyclerAdapter_rooms.RoomViewHolder> {

    private List<Long> list_id;
    private List<String> list_name;
    private List<Long> list_floor;
    private List<String> list_equipments;
    private Context context;
    int logS;

    public RecyclerAdapter_rooms(List<Long> list_id1,List<String> list_name1,List<Long> list_floor1,List<String> list_equipments1,Context context1,int logged1){
        this.list_id = list_id1;
        this.list_name = list_name1;
        this.list_floor = list_floor1;
        this.list_equipments = list_equipments1;
        this.context = context1;
        this.logS = logged1;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rooms_tv,parent,false);
        RoomViewHolder viewHolder = new RoomViewHolder(view,context,list_id,logS);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.tv_room_in.setText("Room :"+position);
        holder.tv_Rname2.setText(list_name.get(position));
        holder.tv_Requipment2.setText(list_equipments.get(position));
        holder.tv_Rfloor2.setText(String.valueOf(list_floor.get(position)));

    }

    @Override
    public int getItemCount() {
        return list_name.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_Rname2;
        TextView tv_Rfloor2;
        TextView tv_Requipment2;
        TextView tv_room_in;
        Context context;
        List<Long> list_id;
        int logS;

        public RoomViewHolder (View itemView,Context context1,List<Long> list_id1,int logS1){
            super(itemView);
            tv_Rname2 = itemView.findViewById(R.id.tv_Rname2);
            tv_Rfloor2 = itemView.findViewById(R.id.tv_Rfloor2);
            tv_Requipment2 = itemView.findViewById(R.id.tv_Requipment2);
            tv_room_in = itemView.findViewById(R.id.tv_room_in);
            itemView.setOnClickListener(this);
            this.context = context1;
            this.list_id = list_id1;
            this.logS = logS1;
        }

        @Override
        public void onClick(View view) {
            int p = getAdapterPosition();
            long i= list_id.get(p);
            Intent intent = new Intent(context,Activity_after_main.class);
            intent.putExtra("loggedi",logS);
            intent.putExtra("activi",2);
            intent.putExtra("idRoom",i);
            context.startActivity(intent);
        }
    }
}
