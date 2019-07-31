package com.example.book_inbeta1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;

public class Activity_Scanner1 extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private static final int REQUEST_CAMERA = 1 ;
    private ZXingScannerView scannerView;
    SpringRestApi springRestApi;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                Toast.makeText(Activity_Scanner1.this,"Permission is granted!",Toast.LENGTH_LONG).show();
            }
            else{
                requestPermission();
            }
        }
    }
    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(Activity_Scanner1.this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[] , int grantResults[])
    {
        switch(requestCode)
        {
            case REQUEST_CAMERA:
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted)
                    {

                        Toast.makeText(Activity_Scanner1.this,"Permission granted!",Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                        Toast.makeText(Activity_Scanner1.this,"Permission denied!",Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(CAMERA))
                            {
                                displayAlertMessage("Allow Permission", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA},REQUEST_CAMERA);
                                        }
                                    }
                                });
                            return;
                            }
                        }
                    }
                }
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(Activity_Scanner1.this)
                .setMessage(message)
                .setPositiveButton("OK",listener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }
    @Override
    public void handleResult(Result result) {
        /*
        final String scanResult = result.getText();
        Intent intent1 = new Intent(getApplicationContext(),Activity_Display_Results.class);
        intent1.putExtra("org.mentorschools.quicklauncher.SOMETHING",scanResult);
        startActivity(intent1);
        */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.4.252:8080/TheBookInApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        springRestApi = retrofit.create(SpringRestApi.class);

        String scanResult = result.getText();
        int choice;
        choice = getIntent().getIntExtra("isLogged",0);
        Intent i2;
        i2 = new Intent(getApplicationContext(),Activity_after_main.class);
        i2.putExtra("activ",1);
        i2.putExtra("scanResult",scanResult);
        i2.putExtra("logS",choice);
        startActivity(i2);
        /*
        if (choice == 1){//Edit room name

        }
        else if(choice == 2){//Delete room
            long id = Long.parseLong(scanResult);
            deleteRoom(id);
            startActivity(new Intent(getApplicationContext(),Activity_Scanner1.class));
        }
        else if(choice == 3){//Book
            i2 = new Intent(getApplicationContext(),Activity_reservation_input.class);
            i2.putExtra("Book",scanResult);
            startActivity(i2);
        }
        else if(choice == 4){//Browse schedule
            i2 = new Intent(getApplicationContext(),Activity_Display_Results.class);
            i2.putExtra("Browse",scanResult);
            startActivity(i2);
        }*/
    }/*
    public void deleteRoom(long id){

        Call<Void> call = springRestApi.deleteMeetingRoom(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Activity_Scanner1.this,"Code: "+response.code()+"/No Room Found.",Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    Toast.makeText(Activity_Scanner1.this,"Room deleted !!!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }*/

}
