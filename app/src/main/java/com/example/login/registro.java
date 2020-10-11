package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefsL";
    ImageButton registro;
    EditText phone;
    EditText email;
    EditText password;
    EditText name;
    TextView terminos;
    TextView politicas;
    TextView ginaga;
    Intent intent;
    Integer f=0;
    Integer check=0;
    Integer check1=0;
    Integer check2=0;
    Integer check3=0;
    TextView prubas;
    Boolean presionado=false;
    float sx = (float) 1.05;
    float sx1 = (float) 1.2;
    private static final String TAG = "ERROR";
    String msj="";
    String msj1="";
    ProgressDialog progressDialog;

    TextView t;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_registro);
        declaraciones();


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
                        String nameE = name.getText().toString().toUpperCase();
                        phoneE = phoneE.replace(" ", "");
                        phoneE = phoneE.replace("-", "");
                        checarDatos(phoneE,emailE,passE,nameE);

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
        name = (EditText)findViewById(R.id.editName);
        password = (EditText)findViewById(R.id.editPass);
        terminos = (TextView)findViewById(R.id.textTerminos);
        politicas = (TextView)findViewById(R.id.textPoliticas);
        ginaga = (TextView)findViewById(R.id.textGinaga);
        prubas =(TextView)findViewById(R.id.textView8);
        t=(TextView)findViewById(R.id.textView8);
    }
    public void checarDatos(String phoneR, String emailR, String passR,String nameR){
        //verificar que los campos exten correctos tener @ y el numero 10 datos
        if (emailR.toLowerCase().contains("") || emailR.toLowerCase().contains(" ")){
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
                            nameR=nameR.toLowerCase();
                            emailR=emailR.toLowerCase();
                            existeLocal(phoneR, emailR, passR,nameR);
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
        }else{
            String msj = "NOMBRE INVALIDO";
            alerta(msj);
        }
    }
    public void existeLocal(final String phoneL, final String emailL , final String passL, final String nameL){

        if (isOnline(getApplicationContext())) {

            final String emailLS = emailL.replace(".","");

            progressDialog = progressDialog.show(registro.this,"REGISTRANDO CUENTA","ESPERE POR FAVOR",false,false);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("USUARIOS/USUARIO");

            myRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (final DataSnapshot dsnapshot : snapshot.getChildren()) {
                            userLoginGetData DATOSUSUARIO = dsnapshot.getValue(userLoginGetData.class);
                            String nombre = DATOSUSUARIO.getNOMBRE();
                            String email = DATOSUSUARIO.getEMAIL();
                            email=email.replace(".","");
                            String telefono = DATOSUSUARIO.getTELEFONO();
                            String phoneLS = String.valueOf(phoneL);

                            if (nombre.equals(nameL)) {
                                check = 1;
                                check1=1;
                            } else if (email.equals(emailLS)) {
                                check = 1;
                                check2=1;
                            } else if (telefono.equals(phoneLS)) {
                                check = 1;
                                check3=1;
                            } else {
                                check = 0;
                            }
                        }
                    }
                    progressDialog.dismiss();
                    if (check == 0) {
                        f=0;
                        registrarLocal(emailL, phoneL, passL, nameL);
                    } else {

                        if(check1==1){
                        String msj = "YA ESTA REGISTRADO UN USUARIO CON ESE NOMBRE";
                        alerta(msj);
                        }else if(check2==1){
                            String msj = "YA ESTA REGISTRADO UN USUARIO CON ESE EMAIL";
                            alerta(msj);
                        }else if(check3==1){
                            String msj = "YA ESTA REGISTRADO UN USUARIO CON ESE NUMERO DE TELEFONO";
                            alerta(msj);
                        }else{
                            String msj = "PROBLEMAS CON EL SERVIDOR";
                            alerta(msj);
                        }
                        check1 = 0;
                        check2 = 0;
                        check3 = 0;
                        check = 0;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            String msj = "No existe conexión a Internet, intente mas tarde...";
            alerta(msj);
        }
    }

    public void registrarLocal(String a, String b ,String c,String d){
        if (isOnline(getApplicationContext())) {
           saveRemoto(a, b, c, d);
            Toast.makeText(getApplicationContext(), "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
           intent = new Intent(getApplicationContext(), LoginActivity.class);
           startActivityForResult(intent, 0);
        } else {
           String msj = "No existe conexión a Internet, intente mas tarde...";
           alerta(msj);
        }
    }
    public void saveRemoto(String a, String b ,String c,String d){
        if (isOnline(getApplicationContext())) {

        a=a.replace(".","");
        String idA = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("USUARIOS");

        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("NOMBRE",d);
        datosUsuario.put("EMAIL",a);
        datosUsuario.put("TELEFONO",b);
        datosUsuario.put("PASSWORD",c);
        datosUsuario.put("DISPOSITIVO",idA);
        datosUsuario.put("PAGO","RNO");
        myRef.child("USUARIO").child(a).setValue(datosUsuario);
        }
        else {
            String msj = "No existe conexión a Internet, intente mas tarde...";
            alerta(msj);
        }
    }
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR");
                    return true;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI");
                    return true;
                }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET");
                    return true;
                }else{
                    String msj = "No existe conexión a Internet, intente mas tarde...";
                    alerta(msj);
                    return false;
                }
            }
        }

        return false;

    }

}
