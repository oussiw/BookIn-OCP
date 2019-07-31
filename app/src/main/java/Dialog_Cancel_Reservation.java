package com.example.book_inbeta1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_Cancel_Reservation extends AppCompatDialogFragment {

    private EditText edT_reservation_code;
    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.popup_cancel, null);
        builder.setView(view)
                .setTitle("Cancelling reservation ?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Reservation not cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edT_reservation_code = view.findViewById(R.id.edT_reservation_code);
                        try {
                            long res_code = Long.parseLong(edT_reservation_code.getText().toString());
                            Toast.makeText(getActivity(), "Reservation cancelled", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Erreur"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }

}