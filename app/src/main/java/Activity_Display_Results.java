package com.example.book_inbeta1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Display_Results extends AppCompatActivity {

    SpringRestApi springRestApi;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_shedule adapter_shedule;
    List<String> list_date;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__display__results);
//Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_schedule);
        layoutManager = new LinearLayoutManager(this);/*
        recyclerView.setLayoutManager(layoutManager);
        adapter_shedule = new RecyclerAdapter_shedule(list_date);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter_shedule);*/

//Sring Boot
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.7.1:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        springRestApi = retrofit.create(SpringRestApi.class);

        toolbar = findViewById(R.id.toolbar_schedule);
        toolbar.setNavigationIcon(R.drawable.ic_room_white_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Room information:");
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        long id_r;
        Intent intent = getIntent();
        /*
        String text = intent.getExtras().getString("id_room");
        id_r = Long.parseLong(text);*/
        id_r = intent.getLongExtra("id_room",0);
        getListDate(id_r);
        Toast.makeText(Activity_Display_Results.this,"All data retrieved",Toast.LENGTH_LONG).show();
    }
    public void getListDate(long id_r){
        Call<List<String>> call_1 = springRestApi.getListDate(id_r);
        call_1.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Activity_Display_Results.this,"Code: "+response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    list_date = response.body();
                    recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_schedule);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter_shedule = new RecyclerAdapter_shedule(list_date);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter_shedule);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });
    }
    }


