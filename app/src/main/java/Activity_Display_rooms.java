package com.example.book_inbeta1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Display_rooms extends AppCompatActivity implements Dialog_room_input.DialogRoomListener {

    SpringRestApi springRestApi;
    SpringRestApi springRestApi2;
    RecyclerView recyclerView;
    private RecyclerAdapter_rooms adapter_rooms;
    private RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    int isLogged;
    String r_name;
    long r_floor;
    String r_equipment;
    int valid_input = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__display_rooms);

        recyclerView = findViewById(R.id.RecyclerView_rooms);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        toolbar = findViewById(R.id.toolbar_rooms);
        toolbar.setNavigationIcon(R.drawable.ic_dashboard_white_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Room information:");
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        isLogged = getIntent().getIntExtra("isLogged",2);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.7.1:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        springRestApi = retrofit.create(SpringRestApi.class);
        getRooms();
    }
    private void getRooms(){
        Toast.makeText(Activity_Display_rooms.this,"Searching....",Toast.LENGTH_SHORT).show();
        Call<List<MeetingRoom>> call = springRestApi.getRooms();
        call.enqueue(new Callback<List<MeetingRoom>>() {
            @Override
            public void onResponse(Call<List<MeetingRoom>> call, Response<List<MeetingRoom>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Activity_Display_rooms.this,"Code:"+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    final List<MeetingRoom> list_rooms = response.body();
                    List<Long> list_ids = new ArrayList<>();
                    List<String> list_names = new ArrayList<>();
                    List<Long> list_floors = new ArrayList<>();
                    List<String> list_equipments = new ArrayList<>();
                    for (MeetingRoom room :list_rooms){
                        list_ids.add(room.getId());
                        list_names.add(room.getName());
                        list_floors.add(room.getFloor());
                        list_equipments.add(room.getEquipment());
                    }
                    adapter_rooms = new RecyclerAdapter_rooms(list_ids,list_names,list_floors,list_equipments,Activity_Display_rooms.this,isLogged);
                    recyclerView.setAdapter(adapter_rooms);
                    Toast.makeText(Activity_Display_rooms.this,"Room found !",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<MeetingRoom>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(Activity_Display_rooms.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(isLogged == 1){
            getMenuInflater().inflate(R.menu.menu_admin2,menu);
        }
        else if(isLogged == 2){
            getMenuInflater().inflate(R.menu.menu_guest2,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int i = item.getItemId();
        if(i == R.id.add_room_m){
            openDialogRoom();
            if(valid_input == 1){
                createRoom(r_name,r_floor,r_equipment);
                Toast.makeText(Activity_Display_rooms.this,"Create Room",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Activity_Display_rooms.this,"There has been an error!!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(i == R.id.search){
            Toast.makeText(Activity_Display_rooms.this,"Search",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void openDialogRoom(){
        Dialog_room_input dialog_room_input = new Dialog_room_input();
        dialog_room_input.show(getSupportFragmentManager(),"popup");
    }

    public void createRoom(String name, long floor,String equipment){
        long id = 16;
        MeetingRoom meetingRoom = new MeetingRoom(id,name,floor,equipment);
        Call<MeetingRoom> call = springRestApi.createMeetingRoom(meetingRoom);
        call.enqueue(new Callback<MeetingRoom>() {
            @Override
            public void onResponse(Call<MeetingRoom> call, Response<MeetingRoom> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Activity_Display_rooms.this, "Couldn't create this room.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(Activity_Display_rooms.this, "Room created!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MeetingRoom> call, Throwable t) {
                Toast.makeText(Activity_Display_rooms.this, "Erreur:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getInf(String na, long fl, String equi,int goodEntries) {
        r_name = na;
        r_floor = fl;
        r_equipment = equi;
        valid_input = goodEntries;
    }
}
