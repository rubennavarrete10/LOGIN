package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registro extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    ImageButton registro;
    EditText phone;
    EditText email;
    EditText password;
    TextView terminos;
    TextView politicas;
    TextView ginaga;
    Intent intent;
    Integer check=0;
    Integer check1=0;
    Integer check2=0;
    Integer check3=0;
    TextView prubas;
    Boolean presionado=false;
    float sx = (float) 1.05;
    float sx1 = (float) 1.2;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        declaraciones();

        /*registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneE = phone.getText().toString();
                String emailE = email.getText().toString();
                String passE = password.getText().toString();
                phoneE = phoneE.replace(" ", "");
                phoneE = phoneE.replace("-", "");
                checarDatos(phoneE,emailE,passE);
            }
        });*/

        registro.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            registro.setScaleX(sx1);
                            registro.setScaleY(sx1);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        registro.setScaleX(1);
                        registro.setScaleY(1);

                        String phoneE = phone.getText().toString();
                        String emailE = email.getText().toString();
                        String passE = password.getText().toString();
                        phoneE = phoneE.replace(" ", "");
                        phoneE = phoneE.replace("-", "");
                        checarDatos(phoneE,emailE,passE);

                        presionado = false;
                        break;
                }
                return true;
            }
        });

        terminos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            terminos.setScaleX(sx);
                            terminos.setScaleY(sx);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        terminos.setScaleX(1);
                        terminos.setScaleY(1);
                        Termino();
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        politicas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            politicas.setScaleX(sx);
                            politicas.setScaleY(sx);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        politicas.setScaleX(1);
                        politicas.setScaleY(1);
                        Politica();
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        ginaga.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            ginaga.setScaleX(sx);
                            ginaga.setScaleY(sx);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ginaga.setScaleX(1);
                        ginaga.setScaleY(1);
                        Ginaga();
                        presionado = false;
                        break;
                }
                return true;
            }
        });
    }

    private void declaraciones() {
        registro = (ImageButton)findViewById(R.id.imageregistro);
        phone = (EditText)findViewById(R.id.editPhone);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPass);
        terminos = (TextView)findViewById(R.id.textTerminos);
        politicas = (TextView)findViewById(R.id.textPoliticas);
        ginaga = (TextView)findViewById(R.id.textGinaga);
        prubas =(TextView)findViewById(R.id.textView8);
    }
    public void checarDatos(String phoneR, String emailR, String passR){
        //verificar que los campos exten correctos tener @ y el numero 10 datos
        if (emailR.toLowerCase().contains("@")){
            if(phoneR.equals("")) {
                String msj = "NUMERO INCORRECTO USE 10 DIGITOS";
                alerta(msj);
            }
            else
            {
                Integer iCantidad = 0;
                float iTemp = Float.parseFloat(phoneR);
                while (iTemp > 0.9) {
                    iTemp = iTemp / 10;
                    iCantidad++;
                }
                if (iCantidad == 10) {
                    if(passR.equals("")){
                        String msj = "ESCRIBE UNA CONTRASEÑA";
                        alerta(msj);
                    }else if (passR.equals(" ")){
                        String msj = "ESCRIBE UNA CONTRASEÑA";
                        alerta(msj);
                    }else{
                        existeLocal(phoneR, emailR, passR);
                    }
                } else {
                    String msj = "NUMERO INCORRECTO USE 10 DIGITOS";
                    alerta(msj);
                }
            }
        }
        else
        {

            String msj = "DIRECCION DE CORREO INVALIDA";
            alerta(msj);
        }
    }
    public void existeLocal(String phoneL, String emailL ,String passL){
        loadLocal(emailL);
        if(check==1){
            //ya existe
            String msj = "YA EXISTE UNA CUENTA CON ESTE EMAIL O NUMERO";
            alerta(msj);
            check1=check;
        }
        loadLocal(phoneL);
        if(check==1){
            //ya existe
            String msj = "YA EXISTE UNA CUENTA CON ESTE EMAIL O NUMERO";
            alerta(msj);
            check2=check;
        }

        check = check1 + check2 +check3;
        if(check==0) {
           //no existe el usuario
           registrarLocal(emailL, phoneL, passL);
        }else {
            check1 = 0;
            check2 = 0;
            check3 = 0;
            check = 0;
        }
    }
    public void loadLocal(String idData){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String loadData = sharedPreferences.getString(idData,"");
        if (loadData.equals("")){
            check=0;
        }
        else {
            check=1;
        }
    }
    public void registrarLocal(String a, String b ,String c){
        saveLocal(a,c);
        saveLocal(b,c);
        Toast.makeText(getApplicationContext(), "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
        saveRemoto(a,b,c);
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
    public void saveLocal(String idData,String saveData){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(idData,saveData);
        editor.apply();
    }
    public void saveRemoto(String a, String b ,String c){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("USUARIOS");
        myRef.child(b).child("PASSWORD").setValue(c);
        myRef.child(b).child("EMAIL").setValue(a);
        myRef.child(b).child("PAGO").setValue("NO");
        a=a.replace(".","");
        myRef.child(a).child("PASSWORD").setValue(c);
        myRef.child(a).child("TELEFONO").setValue(b);
        myRef.child(a).child("PAGO").setValue("NO");
    }
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }
    public void Termino (){
        intent = new Intent(getApplicationContext(), terminos.class);
        startActivityForResult(intent, 0);
    }
    public void Politica(){
        intent = new Intent(getApplicationContext(), politicas.class);
        startActivityForResult(intent, 0);
    }
    public void Ginaga(){
        intent = new Intent(getApplicationContext(), ginaga.class);
        startActivityForResult(intent, 0);
    }
    public void alerta(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((registro.this));
        builder.setCancelable(false);
        builder.setTitle("ERROR");
        builder.setMessage(msj);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
