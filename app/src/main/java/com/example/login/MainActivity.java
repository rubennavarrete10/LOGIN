package com.example.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFSl = "sharedPrefsL";
    private static final String TAG ="";
    private boolean presionado;
    //String push="#2DCCD3";
    String push = "#ffffff";
    String area = "";
    Timer tiempo=new Timer();
    String resultado = "";
    TimerTask ciclo;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        ciclo = new TimerTask() {
            @Override
            public void run() {
                    validar();
                    if(resultado.toLowerCase().equals("no")){
                        ciclo.cancel();
                    }
            }
        };
        tiempo.schedule(ciclo,1000,60000);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }


        final ImageButton OTRO = (ImageButton) findViewById(R.id.button24);
        final EditText otro = (EditText)findViewById(R.id.editText2);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(otro.getWindowToken(), 0);

        final Button URGENCIAS = (Button) findViewById(R.id.button2);
        final Button UCI= (Button) findViewById(R.id.button3);
        final Button QUIROFANO = (Button) findViewById(R.id.button4);
        final Button RECUPERACION = (Button) findViewById(R.id.button5);
        final Button ENDOSCOPIA = (Button) findViewById(R.id.button6);
        final Button UCIN = (Button) findViewById(R.id.button7);
        final Button HEMODINAMIA = (Button) findViewById(R.id.button23);
        final Drawable d = URGENCIAS.getBackground();
        final ImageButton imageb = (ImageButton)findViewById(R.id.imageButton);


        imageb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            imageb.setScaleX((float) 1.2);
                            imageb.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        imageb.setScaleX((float) 1);
                        imageb.setScaleY((float) 1);
                        Intent intent = new Intent(v.getContext(), configuraciones.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        URGENCIAS.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            URGENCIAS.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        URGENCIAS.setBackgroundDrawable(d);
                        area = "URGENCIAS";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        UCI.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            UCI.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        UCI.setBackgroundDrawable(d);
                        area = "UCI";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });


        QUIROFANO.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            QUIROFANO.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        QUIROFANO.setBackgroundDrawable(d);
                        area = "QUIROFANO";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        RECUPERACION.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            RECUPERACION.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        RECUPERACION.setBackgroundDrawable(d);
                        area = "RECUPERACION";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        ENDOSCOPIA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            ENDOSCOPIA.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ENDOSCOPIA.setBackgroundDrawable(d);
                        area = "ENDOSCOPIA";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        UCIN.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            UCIN.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        UCIN.setBackgroundDrawable(d);
                        area = "UCIN";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        HEMODINAMIA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            HEMODINAMIA.setBackgroundColor(Color.parseColor(push));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        HEMODINAMIA.setBackgroundDrawable(d);
                        area = "HEMODINAMIA";
                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        OTRO.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            OTRO.setScaleX((float) 1.2);
                            OTRO.setScaleY((float) 1.2);

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        OTRO.setScaleX((float) 1);
                        OTRO.setScaleY((float) 1);
                        area = otro.getText().toString();
                        area =area.toUpperCase();
                        if(area.equals("")) {
                            Toast.makeText(getApplicationContext(), "ESCRIBA EL AREA", Toast.LENGTH_SHORT).show();
                        }else if(area.equals(" ")) {
                            Toast.makeText(getApplicationContext(), "AREA INVALIDA", Toast.LENGTH_SHORT).show();
                        }else if (area.equals("HEMODINAMIA") || area.equals("UCIN") || area.equals("ENDOSCOPIA") || area.equals("RECUPERACION") || area.equals("QUIROFANO") || area.equals("UCI") || area.equals("URGENCIAS")) {
                            Toast.makeText(getApplicationContext(), "AREAS YA ASIGNADAS, ASIGNAR OTRO NOMBRE", Toast.LENGTH_SHORT).show();
                        } else {
                            saveData();
                            Intent intent = new Intent(v.getContext(), area.class);
                            startActivityForResult(intent, 0);
                        }

                        presionado = false;
                        break;
                }
                return true;
            }
        });

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AREA",area);
        editor.apply();
    }
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }
    public void validar(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
        String userP= sharedPreferences.getString("USUARIOTASK","NO");

        if (isOnline(getApplicationContext())) {
            userP=userP.replace(".","");
            final String userv=userP;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("USUARIOS/USUARIO");

            myRef.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (final DataSnapshot dsnapshot : snapshot.getChildren()) {

                            userLoginGetData DATOSUSUARIO = dsnapshot.getValue(userLoginGetData.class);
                            String email = DATOSUSUARIO.getEMAIL();
                            String telefono = DATOSUSUARIO.getTELEFONO();
                            String pago = DATOSUSUARIO.getPAGO();
                            pago=pago.toLowerCase();

                            if (userv.equals(email) || userv.equals(telefono)) {
                                if (pago.equals("pago")) {
                                    saveR("pago");
                                } else if (pago.equals("rsi")) {
                                    saveR("rsi");
                                } else if (pago.equals("rno")) {
                                    saveR("rno");
                                } else {
                                }

                            }
                        }
                    }
                    resolucion();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }else{
            String msj = "No existe conexión a Internet, intente mas tarde...";
            alerta1(msj);
        }
    }
    public void saveR(String sr){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PAGO", sr);
        editor.apply();
    }
    public void resolucion(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
        resultado = sharedPreferences.getString("PAGO","NO");
        if(resultado.toUpperCase().equals("NO")){
            ciclo.cancel();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
            finishAndRemoveTask();
        }
        if(resultado.toUpperCase().equals("RNO")){
            ciclo.cancel();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
            finishAndRemoveTask();
        }
    }
    public void alerta1(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity.this));
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
