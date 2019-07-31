package com.example.book_inbeta1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_delete extends AppCompatDialogFragment {

    View view;
    private DeleteListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.popup_delete_room, null);
        builder.setView(view)
                .setTitle("Delete Room")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int delete = 0;
                        listener.delete_confirmation(delete);
                        Toast.makeText(getActivity(), "Room not deleted.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int delete = 1;
                        listener.delete_confirmation(delete);
                        Toast.makeText(getActivity(), "Room deleted successfully !!", Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement DeleteListener");
        }
    }

    public interface DeleteListener{
        void delete_confirmation(int delete);
    }
}
