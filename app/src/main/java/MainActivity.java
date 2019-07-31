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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog_login.ExampleDialogListener
{
    Toolbar toolbar;
    Button btn_cancel_res;
    Button btn_scan;
    Button btn_browse_rooms;
    int choice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setLogo(R.drawable.ic_home_white_24dp);
        toolbar.setTitle("BookIn OCP-OCP Group");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleMarginStart(toolbar.getLogo().getIntrinsicWidth());
        setSupportActionBar(toolbar);

        btn_browse_rooms = findViewById(R.id.btn_browse_rooms);
        btn_cancel_res = findViewById(R.id.btn_cancel_res);
        btn_scan = findViewById(R.id.btn_scan);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_Scanner1.class);
                intent.putExtra("isLogged",choice);
                startActivity(intent);
            }
        });

        btn_browse_rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_Display_rooms.class);
                intent.putExtra("isLogged",choice);
                startActivity(intent);
            }
        });

        btn_cancel_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(getApplicationContext(),Activity_cancel_reservation.class);
                intent.putExtra("choice",1);
                startActivity(intent);*/
                //Toast.makeText(MainActivity.this,Integer.toString(choice),Toast.LENGTH_LONG).show();
                openDialog_res();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.bottom_nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        int i = item.getItemId();
        if(i == R.id.login){
            openDialog();
        }
        return true;
    }

    @Override
    public void isAdmin(int isLogged) {
        choice = isLogged;
    }

    public void openDialog(){
        Dialog_login dialogLogin = new Dialog_login();
        dialogLogin.show(getSupportFragmentManager(),"popup");
    }
    public void openDialog_res(){
        Dialog_Cancel_Reservation dialog_cancel_reservation = new Dialog_Cancel_Reservation();
        dialog_cancel_reservation.show(getSupportFragmentManager(),"popup");
    }
}
