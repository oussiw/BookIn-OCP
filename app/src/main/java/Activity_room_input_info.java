package com.example.book_inbeta1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_room_input_info extends AppCompatActivity {

    Button btn_save_room;
    EditText edT_room_floor;
    EditText edT_room_name;
    ImageView iv_QR_CODE_viewer1;
    TextView tv_qr_generated;
    SpringRestApi springRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_input_info);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://6ce0fe65.ngrok.io/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        springRestApi = retrofit.create(SpringRestApi.class);

        btn_save_room = (Button)findViewById(R.id.btn_save_room);
        btn_save_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edT_room_floor = (EditText)findViewById(R.id.edT_room_floor);
                edT_room_name = (EditText)findViewById(R.id.edT_room_name);
                String name = edT_room_name.getText().toString();
                long floor = Long.parseLong(edT_room_floor.getText().toString());
                createRooom(name,floor);
            }
        });
    }
    public void createRooom(String name, long floor){
        final long id = 0;
        final String idq = String.valueOf(id).toString();
        MeetingRoom meetingRoom = new MeetingRoom(id,name,floor);
        Call<MeetingRoom> call = springRestApi.createMeetingRoom(meetingRoom);
        call.enqueue(new Callback<MeetingRoom>() {
            @Override
            public void onResponse(Call<MeetingRoom> call, Response<MeetingRoom> response) {
                if(!response.isSuccessful()){
                    tv_qr_generated.setText("Code: "+response.code());
                    return;
                }
                else{
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(idq, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        iv_QR_CODE_viewer1 = (ImageView)findViewById(R.id.iv_QR_CODE_viewer1);
                        iv_QR_CODE_viewer1.setImageBitmap(bitmap);
                        tv_qr_generated = (TextView)findViewById(R.id.tv_qr_generated);
                        tv_qr_generated.setText("Qr Code Generated !");
                    }catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<MeetingRoom> call, Throwable t) {
                tv_qr_generated.setText("Room not created !");
            }
        });
    }
}
