package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    ImageView login;
    ImageButton showhide;
    Integer showhidebandera=0;
    TextView olvidadocontra;
    TextView registrarse;
    EditText pass;
    EditText phone;
    Intent intent;
    Integer c=0;
    Boolean presionado=false;
    float sx = (float) 1.2;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
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

    public void validar(String userv, String passv){
        load(userv,passv);
        if(c==1){
            //preguntar pago
            pagocheck(userv);
        }
    }
    public void load(String idData, String saveData){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String loadData = sharedPreferences.getString(idData,"");
        if(loadData.equals("")){
            c=0;
            String msj = "USUARIO NO REGISTRADO EN ESTE DISPOSITIVO";
            alerta(msj);
        }
        else{
            if(saveData.equals(loadData)){
                c=1;
            }
            else{
                c=0;
                String msj = "USUARIO O CONTRASEÑA INCORRECTAS";
                alerta(msj);
            }
        }

    }
    public void pagocheck(String userP){
        final String user=userP.replace(".","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("USUARIOS");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                String resultado = "";
                resultado = dataSnapshot.child(user).child("PAGO").getValue(String.class);
                resolucion(resultado);
            }
            @Override
            public void onCancelled(DatabaseError error){
                String msj = "ERROR EN LA CONEXION DEL SERVIDOR";
                alerta(msj);
            }

        });

    }
    public void resolucion(String desicion){
        if(desicion.toLowerCase().equals("si")){
            Toast.makeText(getApplicationContext(), "BIENVENIDO", Toast.LENGTH_SHORT).show();
        }else
        {
            String msj = "ESPERANDO RESPUESTA BANCARIA";
            alerta1(msj);
        }
    }

    public void onBackPressed () {
        finishAffinity();
    }
    public void alerta(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity.this));
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
        AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity.this));
        builder.setCancelable(false);
        builder.setTitle("VERIFICANDO TRANSACCION");
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
// Toast.makeText(getApplicationContext(), String.valueOf(showhidebandera), Toast.LENGTH_SHORT).show();