package com.example.book_inbeta1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_reservation_input extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    TextView edT_date_reservation;
    EditText edT_Email;
    EditText edT_time_inputD;
    EditText edT_time_inputB;
    TextView tv_floor_output;
    TextView tv_name_output;
    Button btn_book_in;
    TextView tv_reservation_registred;
    ProgressBar progressBar;
    SpringRestApi springRestApi;
    long id_r;
    Toolbar toolbar;
    String time1;
    String date1;
    String duration1;
    int hello = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_input);

        id_r = 2;
        Intent intent11 = getIntent();
        /*
        String text = intent11.getExtras().getString("id_room");
        id_r = Long.parseLong(text);*/
        id_r = intent11.getLongExtra("id_room",0);
        tv_reservation_registred = (TextView)findViewById(R.id.tv_reservation_registred);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        barAnim(progressBar);

        toolbar = findViewById(R.id.toolbar_reservation);
        toolbar.setNavigationIcon(R.drawable.ic_room_white_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle("Reservation information:");
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.7.1:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        springRestApi = retrofit.create(SpringRestApi.class);
        getRoom(id_r);

        edT_date_reservation = (TextView) findViewById(R.id.edT_date_reservation);
        edT_Email = (EditText)findViewById(R.id.edT_Email);

        edT_date_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
                //edT_date_reservation.setText(" "+date1);
            }
        });

        Spinner spinner_time = (Spinner)findViewById(R.id.spinner_time);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this,R.array.time,android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(arrayAdapter1);
        spinner_time.setOnItemSelectedListener(this);

        Spinner spinner_duration = (Spinner)findViewById(R.id.spinner_duration);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this,R.array.duration,android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_duration.setAdapter(arrayAdapter2);
        spinner_duration.setOnItemSelectedListener(this);

        btn_book_in = (Button)findViewById(R.id.btn_book_in);
        btn_book_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String date1 = edT_date_reservation.getText().toString();
                //String time1 = edT_time_inputB.getText().toString();
                //String time3 = edT_time_inputD.getText().toString();
                String email = edT_Email.getText().toString();
                String time = date1+time1;
                String date = (date1+"-"+time1+"-"+duration1);
                makeReservation(id_r,date,email,time);
            }
        });
    }

    public void barAnim(ProgressBar progressBar){
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, 410);
        anim.setDuration(3000);
        progressBar.startAnimation(anim);
    }

    public void getRoom(long id){
        Call<MeetingRoom> call = springRestApi.getsMeetingRoom(id);
        call.enqueue(new Callback<MeetingRoom>() {
            @Override
            public void onResponse(Call<MeetingRoom> call, Response<MeetingRoom> response) {
                if(!response.isSuccessful()){
                    tv_reservation_registred.setText("Code: "+response.code());
                    return;
                }
                else{
                    tv_floor_output = (TextView)findViewById(R.id.tv_floor_output);
                    tv_name_output = (TextView)findViewById(R.id.tv_name_output);
                    MeetingRoom meetingRoom = response.body();
                    String name = meetingRoom.getName();
                    tv_name_output.setText(name);
                    long floor = meetingRoom.getFloor();
                    String content_floor;
                    content_floor = Long.toString(floor);
                    tv_floor_output.setText(content_floor);
                    //tv_reservation_registred.setText("Room found !");
                }
            }
            @Override
            public void onFailure(Call<MeetingRoom> call, Throwable t) {
                tv_reservation_registred.setText("No room fond !");
            }
        });
    }

    public void makeReservation(long id_r, final String date, String email, final String time){
        final Reservation reservation = new Reservation(0,id_r,email,date);
        Call<List<String>> call_1 = springRestApi.getListDate(id_r);
        call_1.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    tv_reservation_registred.setText("Code: "+response.code());
                    return;
                }
                else{
                    List<String> list_date = response.body();
                    for(String datee :list_date){
                        if (datee.equals(time)){
                            Toast.makeText(Activity_reservation_input.this,"Date non disponible!",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    insertReservation(reservation);
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                tv_reservation_registred.setText("Procedure non effectuee ! ");
            }
        });
    }

    public void insertReservation(Reservation reservation){
        Call<Reservation> call_2 = springRestApi.makeReservation(reservation);
        call_2.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if(!response.isSuccessful()){
                    tv_reservation_registred.setText("Code: "+response.code());
                    return;
                }
                else{
                    Toast.makeText(Activity_reservation_input.this,"Salle reservee!",Toast.LENGTH_LONG).show();
                    tv_reservation_registred.setText("Reservation registred !");
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
            }
        });
    }

    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_reservation_input.this,this,Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month = month + 1;
        //date1 = d+"/"+ m +"/"+y;
        //date1 = ;
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR,year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,day);
        Date date11 = myCalendar.getTime();
        date1 = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(date11);

        edT_date_reservation.setText(" "+date1);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(hello == 1) {
            time1 = adapterView.getItemAtPosition(i).toString();
        }
        hello = 0;
        duration1 = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}

class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}
