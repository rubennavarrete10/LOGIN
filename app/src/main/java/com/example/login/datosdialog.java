package com.example.login;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class datosdialog extends AppCompatDialogFragment {

    private EditText responsable, ingeniero;
    private Datoslogin listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.datos,null);
        builder.setView(view).setTitle("VERIFICACION").setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("VALIDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String Rs = responsable.getText().toString();
                String is = ingeniero.getText().toString();
                listener.applyTexts(Rs,is);
            }
        });
        responsable=view.findViewById(R.id.editText);
        ingeniero=view.findViewById(R.id.editText);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Datoslogin)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() +"error");
        }

    }

    public interface Datoslogin{
        void applyTexts(String encargado , String inge);

    }
}