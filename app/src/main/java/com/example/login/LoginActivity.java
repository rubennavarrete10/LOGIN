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
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    String SHARED_PREFS = "sharedPrefsL";
    ImageView login;
    ImageButton showhide;
    Integer showhidebandera=0;
    TextView olvidadocontra;
    TextView registrarse;
    EditText pass;
    EditText phone;
    Intent intent;
    Boolean presionado=false;
    private static final String TAG = "ERROR";
    float sx = (float) 1.2;
    Integer bandera=0;
    ProgressDialog progressDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        declaraciones();


        login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            login.setScaleX(sx);
                            login.setScaleY(sx);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        bandera=1;
                        login.setScaleX(1);
                        login.setScaleY(1);
                        String user = phone.getText().toString();
                        String password = pass.getText().toString();
                        validar(user,password);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showhidebandera == 0){
                    //pon ojo abierto y muestra password
                    showhidebandera = 1;
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    int id = getResources().getIdentifier("com.example.login:drawable/" + "pass1", null, null);
                    showhide.setImageResource(id);
                }
                else{
                    //pon ojo bloqueado y oculta password
                    showhidebandera = 0;
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    int id = getResources().getIdentifier("com.example.login:drawable/" + "pass2", null, null);
                    showhide.setImageResource(id);
                }

            }
        });
        olvidadocontra.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            olvidadocontra.setTextColor(Color.parseColor("#757575"));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        olvidadocontra.setTextColor(Color.parseColor("#2DCCD3"));
                        intent = new Intent(v.getContext(), olvidadocontrasena.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        registrarse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            registrarse.setTextColor(Color.parseColor("#757575"));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        registrarse.setTextColor(Color.parseColor("#2DCCD3"));
                        intent = new Intent(v.getContext(), registro.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });
    }
    public void declaraciones() {
        login =(ImageView) findViewById(R.id.imagelogin);
        showhide =(ImageButton) findViewById(R.id.showbutton);
        olvidadocontra = (TextView)findViewById(R.id.recordarpassword);
        registrarse = (TextView)findViewById(R.id.registrarse);
        pass= (EditText)findViewById(R.id.passwordtext);
        phone = (EditText)findViewById(R.id.usertext);
    }

    public void validar(final String userV, final String passv){
        if (isOnline(getApplicationContext())) {

            final String userv = userV.replace(".","");

            progressDialog = progressDialog.show(LoginActivity.this,"INICIADO SESION","ESPERE POR FAVOR",false,false);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("USUARIOS/USUARIO");

            myRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (final DataSnapshot dsnapshot : snapshot.getChildren()) {

                            userLoginGetData DATOSUSUARIO = dsnapshot.getValue(userLoginGetData.class);
                            String email = DATOSUSUARIO.getEMAIL();
                            String telefono = DATOSUSUARIO.getTELEFONO();
                            String password = DATOSUSUARIO.getPASSWORD();
                            String pago = DATOSUSUARIO.getPAGO();
                            String dispositivo = DATOSUSUARIO.getDISPOSITIVO();
                            pago=pago.toLowerCase();
                            String idA = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                                if (userv.equals(email) || userv.equals(telefono)) {
                                    if (passv.equals(password)) {
                                        if(dispositivo.equals(idA) || dispositivo.equals("libre")) {
                                            if (pago.equals("pago")) {
                                                saveR("pago", userv);
                                            } else if (pago.equals("rsi")) {
                                                saveR("rsi", userv);
                                            } else if (pago.equals("rno")) {
                                                saveR("rno", userv);
                                            } else {

                                            }
                                        }else
                                        {
                                            saveR("nodispositivo", userv);
                                        }
                                    }
                                }
                        }
                    }
                    progressDialog.dismiss();
                    resolucion();
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
    public void saveR(String sr,String user){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ESTATUSSERVIDOR", sr);
            editor.putString("USUARIOTASK", user);
            editor.apply();
    }
    public void resolucion(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String desicion = sharedPreferences.getString("ESTATUSSERVIDOR","NO");

            if (desicion.toLowerCase().equals("pago")) {
                if (bandera == 1) {
                    //Toast.makeText(getApplicationContext(), "BIENVENIDO pago", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(intent, 0);
                    bandera = 0;
                }
            } else if (desicion.toLowerCase().equals("rsi")) {
                if (bandera == 1) {
                    //Toast.makeText(getApplicationContext(), "BIENVENIDO pago", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivityForResult(intent, 0);
                    bandera = 0;
                }
            } else if (desicion.toLowerCase().equals("rno")) {
                String msj = "ESPERANDO CONFIRMACION DE PAGO";
                alerta1(msj);

            }else if(desicion.toLowerCase().equals("no")) {
                String msj = "USUARIO O CONTRASEÑA INCORRECTA";
                alerta1(msj);
            }
            else if(desicion.toLowerCase().equals("nodispositivo")) {
                String msj = "SU CUENTA SOLO PUEDE SER USADA EN EL DISPOSITIVO DONDE SE DIO DE ALTA";
                alerta1(msj);
            }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ESTATUSSERVIDOR", "NO");
        editor.apply();
    }
    public void onBackPressed () {
        finishAffinity();
    }
    public void alerta(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((LoginActivity.this));
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
    public void alerta1(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((LoginActivity.this));
        builder.setCancelable(false);
        builder.setTitle("COMPROBANDO");
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
                    alerta1(msj);
                    return false;
                }
            }
        }

        return false;

    }
}
