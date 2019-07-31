package com.example.book_inbeta1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_login extends AppCompatDialogFragment {

    private EditText edT_user_input;
    private EditText edT_pass_input;
    private ExampleDialogListener listener;
    View view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.popup_login,null);
        builder.setView(view)
                .setTitle("Login menu:")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int isLogged;
                        isLogged = 2;
                        listener.isAdmin(isLogged);
                    }
                })
                .setPositiveButton("Sign", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = edT_user_input.getText().toString();
                        String password = edT_pass_input.getText().toString();
                        if(username.equals("admin")  && password.equals("admin")){
                            int isLogged;
                            isLogged = 1;
                            listener.isAdmin(isLogged);
                            Toast.makeText(getActivity(),"Logged successfully.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            int isLogged;
                            isLogged = 2;
                            listener.isAdmin(isLogged);
                            Toast.makeText(getActivity(),"Wrong Login",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        edT_user_input = view.findViewById(R.id.edT_user_input);
        edT_pass_input = view.findViewById(R.id.edT_pass_input);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener{
        void isAdmin(int isLogged);
    }
}
