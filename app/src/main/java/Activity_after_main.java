package com.example.book_inbeta1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.book_inbeta1.R.*;

public class Activity_after_main extends AppCompatActivity implements Dialog_delete.DeleteListener {

    Button fbtn_book;
    Toolbar toolbar;
    SpringRestApi springRestApi1;
    SpringRestApi springRestApi2;
    TextView tv_Rname;
    TextView tv_Rfloor;
    TextView tv_Requipment;
    int choice ;
    int delete_i = 0;
    int found_i = 0;
    long id_r ;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_after_main);

        tv_Rname = findViewById(R.id.tv_Rname);
        tv_Rfloor = findViewById(R.id.tv_Rfloor);
        tv_Requipment = findViewById(R.id.tv_Requipment);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://172.17.7.1:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        springRestApi1 = retrofit1.create(SpringRestApi.class);

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://172.17.7.1:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        springRestApi2 = retrofit2.create(SpringRestApi.class);

//getting results from previous activities
        Intent intent = getIntent();
        int ActivityScanner = intent.getIntExtra("activ",0);
        int ActivityMain = intent.getIntExtra("activi",0);
        if(ActivityScanner == 1){
            text = intent.getExtras().getString("scanResult");
            id_r = Long.parseLong(text);
            choice = getIntent().getIntExtra("logS",2);
        }
        else if(ActivityMain == 2){
            id_r = intent.getLongExtra("idRoom",0);
            choice = getIntent().getIntExtra("loggedi",2);
        }

//Toolbar settings
        toolbar = findViewById(R.id.toolbar_after_main);
        toolbar.setNavigationIcon(drawable.ic_room_white_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Room information:");
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
//fill CardView
        getRoom(id_r);
        //Toast.makeText(Activity_after_main.this,text,Toast.LENGTH_LONG).show();
        if (delete_i == 1 && found_i == 1){
            deleteRoom(id_r);
        } //Delete room
        fbtn_book = (Button) findViewById(R.id.btn_bookk);
        fbtn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (found_i == 0){
                    Toast.makeText(Activity_after_main.this,"None existing room !",Toast.LENGTH_SHORT).show();
                }else if (found_i == 1){
                    Intent intent22 = new Intent(getApplicationContext(), Activity_reservation_input.class);
                    intent22.putExtra("id_room",id_r);        //room id
                    startActivity(intent22);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if(choice == 1){
            getMenuInflater().inflate(R.menu.menu_admin,menu);
        }
        else if(choice == 2){
            getMenuInflater().inflate(R.menu.menu_guest,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int i = item.getItemId();
        if(i == R.id.delete){
            openDialog_delete();
        }
        else if(i == R.id.browse_schedule){
            if (found_i == 0){
                Toast.makeText(Activity_after_main.this,"None existing room !",Toast.LENGTH_SHORT).show();
            }else if (found_i == 1) {
                Intent intent222 = new Intent(getApplicationContext(), Activity_Display_Results.class);
                intent222.putExtra("id_room", id_r);        //room id
                startActivity(intent222);
            }
        }
        return true;
    }

    public void deleteRoom(long id){

        Call<Void> call = springRestApi2.deleteMeetingRoom(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Activity_after_main.this,"Code: "+response.code()+"/No Room Found.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Activity_after_main.this,"Room deleted !!!",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Activity_after_main.this,"No Room Found.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getRoom(long id){
        Toast.makeText(Activity_after_main.this,"Searching....",Toast.LENGTH_SHORT).show();
        Call<MeetingRoom> call = springRestApi1.getsMeetingRoom(id);
        call.enqueue(new Callback<MeetingRoom>() {
            @Override
            public void onResponse(Call<MeetingRoom> call, Response<MeetingRoom> response) {
                if(!response.isSuccessful()){
                    found_i = 0;
                    Toast.makeText(Activity_after_main.this,"Code:"+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    found_i = 1;
                    MeetingRoom meetingRoom = response.body();
                    String name = meetingRoom.getName();
                    String equip = meetingRoom.getEquipment();
                    long floor = meetingRoom.getFloor();
                    String content_floor;
                    content_floor = Long.toString(floor);
                    tv_Rname.setText(name);
                    tv_Rfloor.setText(content_floor);
                    tv_Requipment.setText(equip);
                    Toast.makeText(Activity_after_main.this,"Room found !",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MeetingRoom> call, Throwable t) {
                found_i = 0;
                t.printStackTrace();
                //Toast.makeText(Activity_after_main.this,t.printStackTrace(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialog_delete(){
        Dialog_delete dialog_delete = new Dialog_delete();
        dialog_delete.show(getSupportFragmentManager(),"popup");
    }

    @Override
    public void delete_confirmation(int delete) {
        delete_i = delete;
    }
}
