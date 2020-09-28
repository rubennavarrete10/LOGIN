package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    ImageButton login;
    ImageButton showhide;
    Integer showhidebandera=0;
    TextView olvidadocontra;
    TextView registrarse;
    EditText pass;
    EditText phone;
    Intent intent;
    Integer c=0;
    String passG="NADA";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        declaraciones();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent, 0);*/
                String user = phone.getText().toString();
                String password = pass.getText().toString();
                validar(user,password);
            }
        });
        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showhidebandera == 0){
                    //pon ojo abierto y muestra password
                    showhidebandera = 1;
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    //pon ojo bloqueado y oculta password
                    showhidebandera = 0;
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });
        olvidadocontra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), olvidadocontrasena.class);
                startActivityForResult(intent, 0);

            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), registro.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void declaraciones() {
        login =(ImageButton) findViewById(R.id.imagelogin);
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
        finishAndRemoveTask();
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