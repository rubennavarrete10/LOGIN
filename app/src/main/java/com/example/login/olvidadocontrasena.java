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
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
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

import java.util.Properties;
import java.util.Timer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class olvidadocontrasena extends AppCompatActivity {
    String APP = "LOGIN";
    public static final String SHARED_PREFS = "sharedPrefsL";
    private static final String TAG = "ERROR";
    ImageButton senCorreo;
    EditText correo;
    TextView ginaga;
    Intent intent;

    String dirrecioncorreo = "";
    Session session = null;
    ProgressDialog pdialog = null;
    Boolean presionado=false;
    float sx = (float) 1.1;
    String sinEternet="NO";
    Integer bandera=0;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_olvidadocontrasena);

        senCorreo = (ImageButton) findViewById(R.id.sendcorreo);
        correo = (EditText) findViewById(R.id.editCorreo);
        ginaga = (TextView) findViewById(R.id.textGinaga1);


        senCorreo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            senCorreo.setScaleX(sx);
                            senCorreo.setScaleY(sx);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        senCorreo.setScaleX(1);
                        senCorreo.setScaleY(1);
                        String direccion = correo.getText().toString();
                        dirrecioncorreo=correo.getText().toString();
                        sendcorreo(direccion);
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

    public void Ginaga() {
        intent = new Intent(getApplicationContext(), ginaga.class);
        startActivityForResult(intent, 0);
    }
    private void sendcorreo(String correo) {
        //verificar que los campos exten correctos
        if (correo.contains("@")) {
                validar(correo);
        } else {
            String msj = "ESCRIBA EL CORREO CORRECTAMENTE";
            alerta(msj);
        }
    }
    public void sendMsj() {

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
        String a = sharedPreferences.getString("CORREOOLVIDO","nada@gmail.com");
        String b = sharedPreferences.getString("TELEFONOOLVIDO","0000000000");
        String c = sharedPreferences.getString("PASSOLVIDO","00000000");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "587");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ginagaappservice@gmail.com", "ALEESPUTA12_");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ginagaappservice@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(a));
            message.setSubject("CONTRASEÑA DE LA APLICACION: "+APP);
            message.setText("USUARIOS SON:\n"+a+"\n"+b+""+"\nTU CONTRASEÑA ES:\n" + c);
            new SendMail().execute(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void alerta(String MSJ){
        AlertDialog.Builder builder = new AlertDialog.Builder((olvidadocontrasena.this));
        builder.setCancelable(false);
        builder.setTitle("ERROR");
        builder.setMessage(MSJ);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                correo.setText("");
            }
        });
        builder.show();
    }
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }

    private class SendMail extends AsyncTask<Message, String, String> {
        private ProgressDialog progressDialog;

        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = progressDialog.show(olvidadocontrasena.this,"ESPERE POR FAVOR","ENVIANDO MENSAJE",true,false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            if(isOnline(getApplicationContext())){
                //Continua...Aquí puedes agregar el intent a MainActivity.
                try {
                    Transport.send(messages[0]);
                    sinEternet="SI";
                    return "OK";
                } catch (MessagingException e) {
                    e.printStackTrace();
                    sinEternet="EMAIL";
                }

            }else{
                sinEternet="NO";
            }

            return "NA";
        }

        protected void onPostExecute(String s){
            SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("CORREOOLVIDO", "N/A");
            editor.putString("TELEFONOOLVIDO", "N/A");
            editor.putString("PASSOLVIDO", "N/A");
            editor.apply();
            progressDialog.dismiss();
            if(sinEternet == "SI"){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"EMAIL ENVIADO",Toast.LENGTH_LONG).show();
            }
            else if (sinEternet=="EMAIL")
            {
                progressDialog.dismiss();
                alerta("Problema al enviar el Email, intente mas tarde...");
            }
            else
            {
                alerta("No existe conexión a Internet, intente mas tarde...");
            }
        }
    }


    public void validar(String user){
        if (isOnline(getApplicationContext())) {
            user=user.replace(".","");
            final String userv=user;

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("USUARIOS/USUARIO");

            myRef.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (final DataSnapshot dsnapshot : snapshot.getChildren()) {

                            userLoginGetData DATOSUSUARIO = dsnapshot.getValue(userLoginGetData.class);
                            String email = DATOSUSUARIO.getEMAIL();
                            String password = DATOSUSUARIO.getPASSWORD();
                            String pago = DATOSUSUARIO.getPAGO();
                            String telefono = DATOSUSUARIO.getTELEFONO();
                            pago=pago.toLowerCase();
                            if (userv.equals(email)) {
                                saveR(dirrecioncorreo,telefono,password);
                                //Toast.makeText(getApplicationContext(), ""+dirrecioncorreo+" "+telefono+" "+password,Toast.LENGTH_SHORT).show();
                                bandera=1;
                            }else{

                            }

                        }

                        if(bandera==1){
                            sendMsj();
                            bandera=0;
                        }else{
                            String msj = "ESTE CORREO NO ESTA REGISTRADO, VERIFIQUE DE NUEVO";
                            alerta(msj);
                            bandera=0;
                        }
                    }
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
    public void saveR(String a, String b , String c){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefsL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CORREOOLVIDO", a);
        editor.putString("TELEFONOOLVIDO", b);
        editor.putString("PASSOLVIDO", c);
        editor.apply();
    }
    public void alerta1(String msj){
        AlertDialog.Builder builder = new AlertDialog.Builder((olvidadocontrasena.this));
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

