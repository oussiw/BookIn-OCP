package com.example.book_inbeta1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_cancel_reservation extends AppCompatActivity {

    Button btn_cancel_reservation;
    TextView tv_reservation_cancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);
        btn_cancel_reservation = (Button)findViewById(R.id.btn_cancel_reservation);
        btn_cancel_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_reservation_cancelled = (TextView)findViewById(R.id.tv_reservation_cancelled);
                tv_reservation_cancelled.setText("Reservation cancelled !");
            }
        });
    }
}
