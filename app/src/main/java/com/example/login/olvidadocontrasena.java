package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

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
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String TAG = "ERROR";
    ImageButton senCorreo;
    EditText correo;
    TextView ginaga;
    Intent intent;
    Integer check = 0;


    String dirrecioncorreo = "";
    String contrasena = "";
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    Boolean presionado=false;
    float sx = (float) 1.1;
    String sinEternet="NO";

    Timer tiempo=new Timer();

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
            loadLocal(correo);
            //VERIFICAR SI NO EXISTE
            if (check == 1) {
                //correo
                sendMsj(correo);
            } else {

                String msj = "CORREO SIN REGISTRO PREVIO";
                alerta(msj);
            }

        } else {
            String msj = "ESCRIBA EL CORREO CORRECTAMENTE";
            alerta(msj);
        }

    }

    public void loadLocal(String idData) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String loadData = sharedPreferences.getString(idData, "");
        if (loadData.equals("")) {
            check = 0;
        } else {
            check = 1;
            contrasena = loadData;
        }
    }

    public void sendMsj(String d) {
        dirrecioncorreo = d;
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dirrecioncorreo));
            message.setSubject("CONTRASEÑA DE LA APLICACION: "+APP);
            message.setText("TU CONTRASEÑA ES: " + contrasena);
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
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    public static boolean isOnline(Context context) {
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
                }
            }
        }

        return false;

    }
}

