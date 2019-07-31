package com.example.book_inbeta1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

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

public class Dialog_room_input extends AppCompatDialogFragment {
    View view;
    private DialogRoomListener listener;
    EditText edT_name_input1;
    EditText edT_floor_input1;
    EditText edT_equipment_input1;
    SpringRestApi springRestApi;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.popup_room_input, null);
        builder.setView(view)
                .setTitle("Provide details:")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Room not cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edT_name_input1 = view.findViewById(R.id.edT_name_input1);
                        edT_floor_input1 = view.findViewById(R.id.edT_floor_input1);
                        edT_equipment_input1 = view.findViewById(R.id.edT_equipment_input1);
                        try {
                            String name = edT_name_input1.getText().toString();
                            long floor = Long.parseLong(edT_floor_input1.getText().toString());
                            String equipment = edT_equipment_input1.getText().toString();
                            if ((0 > floor)||(floor > 20)){
                                Toast.makeText(getActivity(), "Floor invalid!", Toast.LENGTH_SHORT).show();
                            }else {
                                listener.getInf(name, floor, equipment,1);
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Erreur"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogRoomListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement DeleteListener");
        }
    }

    public interface DialogRoomListener{
        void getInf(String na,long fl,String equi,int goodEntries);
    }
}
