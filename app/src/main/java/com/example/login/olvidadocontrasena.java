package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class olvidadocontrasena extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
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


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_olvidadocontrasena);

        senCorreo = (ImageButton) findViewById(R.id.sendcorreo);
        correo = (EditText) findViewById(R.id.editCorreo);
        ginaga = (TextView) findViewById(R.id.textGinaga1);

        senCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccion = correo.getText().toString();
                sendcorreo(direccion);
            }
        });

        ginaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ginaga();
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
            message.setSubject("CONTRASEÑA");
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
            try {
                Transport.send(messages[0]);
                return "EMAIL ENVIADO";
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s){
            progressDialog.dismiss();
            super.onPostExecute(s);
            if(s.equals("EMAIL ENVIADO")) {
                Toast.makeText(getApplicationContext(),"EMAIL ENVIADO",Toast.LENGTH_LONG).show();
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder((olvidadocontrasena.this));
                builder.setCancelable(false);
                builder.setTitle("ERROR");
                builder.setMessage("PROBLEMAS PARA ENVIAR EL EMAIL");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        correo.setText("");
                    }
                });
                builder.show();
            }
            //
        }
    }
}

