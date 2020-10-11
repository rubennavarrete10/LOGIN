package com.example.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class area extends AppCompatActivity implements datosdialog.Datoslogin {
    Document documento = new Document(PageSize.LETTER);
    String SHARED_PREFS = "sharedPrefs";
    String SHARED_PREFSl = "sharedPrefsL";
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesl;
    private boolean presionado;
    FrameLayout layout1;
    TextView AREA;
    String firmaS ="no";
    String equipo="NA";
    String HOSPITAL="NA, VAYA A CONFIGURACIONES";
    String imgpath="NA";
    String colorcheck = "#2DCCD3";
    Integer bandera=0;
    Button arcoenc,bombainfusion,cunacalor,elecgrafo,electerio,incu,lamp,maqanestesia,microscopioq,poligrafo,torreendo,torrelapa,ultrasonido,ventilador,monitoru,desfibriladoru;
    Button bascula,bisturi,cargadorlapa,extractor,glucometro,hojalari,laringo,mofetal,negatos,octamo,otos,oximetro,videolaringo;
    Button asisresp,autoclave,calentabiberon,desviasareas,esteelectrico,inyector,lavadora,lipo,torniquete;
    Button prevacio,bandaesfuerzo,baumapared,camah,camat,carroparo,cuna,impresora,mesaq,modulosg,sellador;
    final Handler handler = new Handler();


    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.area);
        layout1 = (FrameLayout) findViewById(R.id.firmasview);
        final Lienzo fondo = new Lienzo(this);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        File path1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        File CARPETA = new File(path1.getAbsolutePath(), "VERIFICACIONES EQUIPO MEDICO");
        File CURGENCIAS = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "URGENCIAS");
        File CUCI = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "UCI");
        File CQUIROFANO = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "QUIROFANO");
        File CRECU = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "RECUPERACION");
        File CENDO = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "ENDOSCOPIA");
        File CUCIN = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "UCIN");
        File CHEMO = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "HEMODINAMIA");
        File COTRO = new File(path1.getAbsolutePath()+"/VERIFICACIONES EQUIPO MEDICO", "OTRAS AREAS");
        if(!CARPETA.exists()) {
            CARPETA.mkdirs();
        }
        if(!CURGENCIAS.exists()) {
            CURGENCIAS.mkdirs();
        }
        if(!CUCI.exists()) {
            CUCI.mkdirs();
        }
        if(!CQUIROFANO.exists()) {
            CQUIROFANO.mkdirs();
        }
        if(!CRECU.exists()) {
            CRECU.mkdirs();
        }
        if(!CENDO.exists()) {
            CENDO.mkdirs();
        }
        if(!CUCIN.exists()) {
            CUCIN.mkdirs();
        }
        if(!CHEMO.exists()) {
            CHEMO.mkdirs();
        }
        if(!COTRO.exists()) {
            COTRO.mkdirs();
        }



        AREA = (TextView)findViewById(R.id.textView3);
        final Switch equipomedico = (Switch) findViewById(R.id.switch1);
        final ScrollView SCROLLMEDICO = (ScrollView) findViewById(R.id.scrollView2);
        final Switch inmobiliario = (Switch) findViewById(R.id.switch2);
        final ScrollView SCROLLINMO = (ScrollView) findViewById(R.id.scrollView3);
        final Switch instrumental = (Switch) findViewById(R.id.switch4);
        final ScrollView SCROLLINSTRU = (ScrollView) findViewById(R.id.scrollView5);
        final Switch especializado = (Switch) findViewById(R.id.switch5);
        final ScrollView SCROLLESP= (ScrollView) findViewById(R.id.scrollView6);
        final ScrollView TODO = (ScrollView)findViewById(R.id.scrollView7);

        equipomedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                if (equipomedico.isChecked()){
                    SCROLLMEDICO.setVisibility(View.VISIBLE);
                }
                else{
                    SCROLLMEDICO.setVisibility(View.GONE);
                }
            }
        });
        inmobiliario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                if (inmobiliario.isChecked()){
                    instrumental.setEnabled(false);
                    especializado.setEnabled(false);
                    SCROLLINMO.setVisibility(View.VISIBLE);
                }
                else{
                    SCROLLINMO.setVisibility(View.GONE);
                    instrumental.setEnabled(true);
                    especializado.setEnabled(true);
                }
            }
        });
        instrumental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                if (instrumental.isChecked()){
                    inmobiliario.setEnabled(false);
                    especializado.setEnabled(false);
                    SCROLLINSTRU.setVisibility(View.VISIBLE);

                }
                else{
                    SCROLLINSTRU.setVisibility(View.GONE);
                    inmobiliario.setEnabled(true);
                    especializado.setEnabled(true);
                }
            }
        });
        especializado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                if (especializado.isChecked()){
                    instrumental.setEnabled(false);
                    inmobiliario.setEnabled(false);
                    SCROLLESP.setVisibility(View.VISIBLE);
                }
                else{
                    instrumental.setEnabled(true);
                    inmobiliario.setEnabled(true);
                    SCROLLESP.setVisibility(View.GONE);
                }
            }
        });

        final ImageButton regresar = (ImageButton) findViewById(R.id.button19);
        final ImageButton pdfback = (ImageButton) findViewById(R.id.button);
        final ImageButton firma = (ImageButton)findViewById(R.id.button20);
        final Drawable d = regresar.getBackground();
        regresar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            regresar.setBackgroundColor(Color.parseColor("#D50000"));
                            regresar.setScaleX((float) 1.2);
                            regresar.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        regresar.setScaleX((float) 1);
                        regresar.setScaleY((float) 1);
                        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        String BORRAR = sharedPreferences.getString("BORRAR","NO");
                        if(BORRAR.equals("NO")){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("BORRAR","SI");
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "PRESIONE OTRA VEZ PARA BORRAR LA VERIFICACION", Toast.LENGTH_SHORT).show();
                        }
                        if(BORRAR.equals("SI")){
                        clearData1();
                        }
                        presionado = false;
                        break;
                }
                return true;
            }
        });


        pdfback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            pdfback.setScaleX((float) 1.2);
                            pdfback.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        pdfback.setScaleX((float) 1);
                        pdfback.setScaleY((float) 1);
                        if(firmaS.equals("si")){
                            login();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "FIRME EL DOCUMENTO PARA CREARLO", Toast.LENGTH_SHORT).show();
                        }
                        presionado = false;
                        break;
                }
                return true;

            }
        });

        firma.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("BORRAR","NO");
                editor.apply();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            firma.setScaleX((float) 1.2);
                            firma.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        firma.setScaleX((float) 1);
                        firma.setScaleY((float) 1);

                        if(layout1.getVisibility() == View.VISIBLE){
                            SCROLLMEDICO.setVisibility(View.VISIBLE);
                            SCROLLINSTRU.setVisibility(View.GONE);
                            SCROLLINMO.setVisibility(View.GONE);
                            SCROLLESP.setVisibility(View.GONE);
                            instrumental.setEnabled(true);
                            inmobiliario.setEnabled(true);
                            especializado.setEnabled(true);
                            equipomedico.setEnabled(true);
                            equipomedico.setChecked(true);
                            layout1.removeView(fondo);
                            layout1.setVisibility(View.GONE);
                            TODO.setVisibility(View.VISIBLE);
                            firma.setRotation(0);
                            pdfback.setRotation(0);
                            regresar.setVisibility(View.VISIBLE);

                           ConstraintLayout constraintLayout = findViewById(R.id.LL);
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(constraintLayout);
                            constraintSet.connect(R.id.button, ConstraintSet.START,R.id.LL, ConstraintSet.START,60);
                            constraintSet.connect(R.id.button20, ConstraintSet.START,R.id.LL, ConstraintSet.START,13);
                            constraintSet.applyTo(constraintLayout);

                        }
                        else{
                            SCROLLMEDICO.setVisibility(View.GONE);
                            SCROLLINSTRU.setVisibility(View.GONE);
                            SCROLLINMO.setVisibility(View.GONE);
                            SCROLLESP.setVisibility(View.GONE);
                            instrumental.setEnabled(false);
                            inmobiliario.setEnabled(false);
                            especializado.setEnabled(false);
                            equipomedico.setEnabled(false);
                            instrumental.setChecked(false);
                            inmobiliario.setChecked(false);
                            especializado.setChecked(false);
                            equipomedico.setChecked(false);
                            layout1.setVisibility(View.VISIBLE);
                            TODO.setVisibility(View.INVISIBLE);
                            regresar.setVisibility(View.INVISIBLE);
                            pdfback.setRotation(-90);
                            firma.setRotation(-90);


                            ConstraintLayout constraintLayout = findViewById(R.id.LL);
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(constraintLayout);
                            constraintSet.connect(R.id.button20, ConstraintSet.START,R.id.textVie3, ConstraintSet.START,0);
                            constraintSet.connect(R.id.button, ConstraintSet.END,R.id.textVie3, ConstraintSet.END,0);
                            constraintSet.applyTo(constraintLayout);

                            layout1.addView(fondo, 0);

                            //firma.setScaleX((float) 0.8);
                        }

                        presionado = false;
                        break;
                }
                return true;

            }
        });


        //se asignan los botones de equipo medico
        if(true){
            arcoenc = (Button) findViewById(R.id.button14);
            bombainfusion = (Button) findViewById(R.id.button26);
            cunacalor = (Button) findViewById(R.id.button18);
            elecgrafo = (Button) findViewById(R.id.button11);
            electerio = (Button) findViewById(R.id.button15);
            incu = (Button) findViewById(R.id.button12);
            lamp = (Button) findViewById(R.id.button13);
            maqanestesia= (Button) findViewById(R.id.button10);
            microscopioq= (Button) findViewById(R.id.button27);
            poligrafo= (Button) findViewById(R.id.button16);
            torreendo= (Button) findViewById(R.id.button22);
            torrelapa= (Button) findViewById(R.id.button25);
            ultrasonido= (Button) findViewById(R.id.button21);
            ventilador= (Button) findViewById(R.id.button17);
            monitoru = (Button) findViewById(R.id.button8);
            desfibriladoru =(Button)findViewById(R.id.button9);
        }
        //se asignan los botones de instrumental
        if(true){
            bascula= (Button) findViewById(R.id.button30);
            bisturi= (Button) findViewById(R.id.button31);
            extractor= (Button) findViewById(R.id.button32);
            glucometro= (Button) findViewById(R.id.button33);
            hojalari= (Button) findViewById(R.id.button34);
            laringo= (Button) findViewById(R.id.button41);
            mofetal= (Button) findViewById(R.id.button35);
            negatos= (Button) findViewById(R.id.button36);
            octamo= (Button) findViewById(R.id.button37);
            otos= (Button) findViewById(R.id.button38);
            oximetro= (Button) findViewById(R.id.button39);
            videolaringo= (Button) findViewById(R.id.button40);
        }
        //se asignan los botones de inmobiliario
        if(true){
            prevacio= (Button) findViewById(R.id.button28);
            bandaesfuerzo= (Button) findViewById(R.id.button42);
            baumapared= (Button) findViewById(R.id.button43);
            camah= (Button) findViewById(R.id.button44);
            camat= (Button) findViewById(R.id.button45);
            carroparo= (Button) findViewById(R.id.button46);
            cuna= (Button) findViewById(R.id.button47);
            impresora= (Button) findViewById(R.id.button48);
            mesaq= (Button) findViewById(R.id.button49);
            modulosg= (Button) findViewById(R.id.button50);
            sellador= (Button) findViewById(R.id.button51);
        }
        //se asignana los botones de especialidad
        if(true){
            asisresp= (Button) findViewById(R.id.button29);
            autoclave= (Button) findViewById(R.id.button53);
            calentabiberon= (Button) findViewById(R.id.button54);
            desviasareas= (Button) findViewById(R.id.button55);
            esteelectrico= (Button) findViewById(R.id.button56);
            inyector= (Button) findViewById(R.id.button57);
            lavadora= (Button) findViewById(R.id.button58);
            lipo= (Button) findViewById(R.id.button59);
            torniquete= (Button) findViewById(R.id.button60);
        }

        //aqui se crean todos los eventos de todos los botones de equipo medico
        if(true) {
            arcoenc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="arcoenc";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            bombainfusion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="bombainfusion";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            cunacalor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="cunacalor";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            elecgrafo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="elecgrafo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            electerio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="electerio";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            incu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="incu";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            lamp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="lamp";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            maqanestesia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="maqanestesia";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            microscopioq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="microscopioq";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            poligrafo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="poligrafo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            torreendo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="torreendo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            torrelapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="torrelapa";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            ultrasonido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="ultrasonido";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            ventilador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="ventilador";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            monitoru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="monitor";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            desfibriladoru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="desfibrilador";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
        //aqui se crean todos los eventos de todos los botones de instrumental
        if(true){
            bascula.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="bascula";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            bisturi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="bisturi";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            extractor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="extractor";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            glucometro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="glucometro";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            hojalari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="hojalari";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            laringo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="laringo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            mofetal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="mofetal";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            negatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="negatos";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            octamo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="octamo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            otos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="otos";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            oximetro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="oximetro";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
            videolaringo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="videolaringo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), instrumental.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
        //aqui se crean todos los eventos de todos los botones de inmobiliario
        if(true){
            prevacio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="prevacio";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            bandaesfuerzo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="bandaesfuerzo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            baumapared.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="baumapared";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            camah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="camah";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            camat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="camat";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            carroparo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="carroparo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            cuna.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="cuna";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            impresora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="impresora";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            mesaq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="mesaq";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            modulosg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="modulosg";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
            sellador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="sellador";
                    saveData();
                    Intent intent = new Intent(v.getContext(), inmobiliario.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
        //aqui se crean todos los eventos de todos los botones de especialidad
        if(true){
            asisresp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="asisresp";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            autoclave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="autoclave";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            calentabiberon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="calentabiberon";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            desviasareas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="desviasareas";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            esteelectrico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="esteelectrico";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            inyector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="inyector";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            lavadora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="lavadora";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            lipo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="lipo";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
            torniquete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    equipo="torniquete";
                    saveData();
                    Intent intent = new Intent(v.getContext(), equipomedico.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
        loadArea();
        loadDataEquipo();
    }

    public void loadArea() {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String area = sharedPreferences.getString("AREA","SIN AREA");
        AREA.setText(area);

        SharedPreferences sharedPreferencesL = getSharedPreferences(SHARED_PREFSl, MODE_PRIVATE);
        HOSPITAL = sharedPreferencesL.getString("EMPRESA","NA, VAYA A CONFIGURACIONES");
        imgpath = sharedPreferencesL.getString("imgpath","");


        if(area.equals("UREGENCIAS")){
            seluregncias();
        }
        if(area.equals("UCI")){
            seluci();
        }
        if(area.equals("QUIROFANO")){
            selquirofano();
        }
        if(area.equals("RECUPERACION")){
            selrecu();
        }
        if(area.equals("ENDOSCOPIA")){
            selendo();
        }
        if(area.equals("UCIN")){
            selucin();
        }
        if(area.equals("HEMODINAMIA")){
            selhemo();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BORRAR","NO");
        editor.apply();
    }
    public void loadDataEquipo() {
        String EQUIPOMONITOR = sharedPreferences.getString("EQUIPOMONITOR","");
        String EQUIPOARCOENC = sharedPreferences.getString("EQUIPOARCOENC","");
        String EQUIPOBOMBAINFUSION = sharedPreferences.getString("EQUIPOBOMBAINFUSION","");
        String EQUIPOCUNACALOR = sharedPreferences.getString("EQUIPOCUNACALOR","");
        String EQUIPOELECTERIO = sharedPreferences.getString("EQUIPOELECTERIO","");
        String EQUIPOELECGRAFO = sharedPreferences.getString("EQUIPOELECGRAFO","");
        String EQUIPOINCU = sharedPreferences.getString("EQUIPOINCU","");
        String EQUIPOLAMP = sharedPreferences.getString("EQUIPOLAMP","");
        String EQUIPOMAQUINANESTECIA = sharedPreferences.getString("EQUIPOMAQUINANESTECIA","");
        String EQUIPOMICROSCOPIO = sharedPreferences.getString("EQUIPOMICROSCOPIO","");
        String EQUIPOPOLIGRAFO = sharedPreferences.getString("EQUIPOPOLIGRAFO","");
        String EQUIPOTORREENDO = sharedPreferences.getString("EQUIPOTORREENDO","");
        String EQUIPOTORRELAPARO = sharedPreferences.getString("EQUIPOTORRELAPARO","");
        String EQUIPOULTRASONIDO = sharedPreferences.getString("EQUIPOULTRASONIDO","");
        String EQUIPOVENTILADOR = sharedPreferences.getString("EQUIPOVENTILADOR","");
        String EQUIPODESFIBRILADOR = sharedPreferences.getString("EQUIPODESFIBRILADOR","");

        if (EQUIPOMONITOR.equals("LISTO")){
            monitoru.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOARCOENC.equals("LISTO")){
            arcoenc.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOBOMBAINFUSION.equals("LISTO")){
            bombainfusion.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOCUNACALOR.equals("LISTO")){
            cunacalor.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOELECGRAFO.equals("LISTO")){
            elecgrafo.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOELECTERIO.equals("LISTO")){
            electerio.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOINCU.equals("LISTO")){
            incu.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOLAMP.equals("LISTO")){
            lamp.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOMAQUINANESTECIA.equals("LISTO")){
            maqanestesia.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOMICROSCOPIO.equals("LISTO")){
            microscopioq.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOPOLIGRAFO.equals("LISTO")){
            poligrafo.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOTORREENDO.equals("LISTO")){
            torreendo.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOTORRELAPARO.equals("LISTO")){
            torrelapa.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOULTRASONIDO.equals("LISTO")){
            ultrasonido.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOVENTILADOR.equals("LISTO")){
            ventilador.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPODESFIBRILADOR.equals("LISTO")){
            desfibriladoru.setBackgroundColor(Color.parseColor(colorcheck));
        }

        String EQUIPOASISRESP = sharedPreferences.getString("EQUIPOASISRESP","");
        String EQUIPOAUTOCLAVE = sharedPreferences.getString("EQUIPOAUTOCLAVE","");
        String EQUIPOCALENTABIBERON = sharedPreferences.getString("EQUIPOCALENTABIBERON","");
        String EQUIPODESVIASAREAS = sharedPreferences.getString("EQUIPODESVIASAREAS","");
        String EQUIPOESTEELECTRICO = sharedPreferences.getString("EQUIPOESTEELECTRICO","");
        String EQUIPOINYECTOR = sharedPreferences.getString("EQUIPOINYECTOR","");
        String EQUIPOLAVADORA = sharedPreferences.getString("EQUIPOLAVADORA","");
        String EQUIPOLIPO = sharedPreferences.getString("EQUIPOLIPO","");
        String EQUIPOTORNIQUETE = sharedPreferences.getString("EQUIPOTORNIQUETE","");
        //String EQUIPO = sharedPreferences.getString("EQUIPO","");

        if (EQUIPOASISRESP.equals("LISTO")){
            asisresp.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOAUTOCLAVE.equals("LISTO")){
            autoclave.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOCALENTABIBERON.equals("LISTO")){
            calentabiberon.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPODESVIASAREAS.equals("LISTO")){
            desviasareas.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOESTEELECTRICO.equals("LISTO")){
            esteelectrico.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOINYECTOR.equals("LISTO")){
            inyector.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOLAVADORA.equals("LISTO")){
            lavadora.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOLIPO.equals("LISTO")){
            lipo.setBackgroundColor(Color.parseColor(colorcheck));
        }
        if (EQUIPOTORNIQUETE.equals("LISTO")){
            torniquete.setBackgroundColor(Color.parseColor(colorcheck));
        }

        String EQUIPOPREVACIO = sharedPreferences.getString("EQUIPOPREVACIO","");
        String EQUIPOBANDAESFUERZO = sharedPreferences.getString("EQUIPOBANDAESFUERZO","");
        String EQUIPOBAUMAPARED = sharedPreferences.getString("EQUIPOBAUMAPARED","");
        String EQUIPOCAMAH = sharedPreferences.getString("EQUIPOCAMAH","");
        String EQUIPOCAMAT = sharedPreferences.getString("EQUIPOCAMAT","");
        String EQUIPOCARROPARO = sharedPreferences.getString("EQUIPOCARROPARO","");
        String EQUIPOCUNA = sharedPreferences.getString("EQUIPOCUNA","");
        String EQUIPOIMPRESORA = sharedPreferences.getString("EQUIPOIMPRESORA","");
        String EQUIPOMESAQ = sharedPreferences.getString("EQUIPOMESAQ","");
        String EQUIPOMODULOSG = sharedPreferences.getString("EQUIPOMODULOSG","");
        String EQUIPOSELLADOR = sharedPreferences.getString("EQUIPOSELLADOR","");

        if (EQUIPOPREVACIO.equals("LISTO")){ prevacio.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOBANDAESFUERZO.equals("LISTO")){  bandaesfuerzo.setBackgroundColor(Color.parseColor(colorcheck));  }
        if (EQUIPOBAUMAPARED.equals("LISTO")){  baumapared.setBackgroundColor(Color.parseColor(colorcheck));  }
        if (EQUIPOCAMAH.equals("LISTO")){ camah.setBackgroundColor(Color.parseColor(colorcheck));  }
        if (EQUIPOCAMAT.equals("LISTO")){ camat.setBackgroundColor(Color.parseColor(colorcheck));  }
        if (EQUIPOCARROPARO.equals("LISTO")){  carroparo.setBackgroundColor(Color.parseColor(colorcheck));  }
        if (EQUIPOCUNA.equals("LISTO")){  cuna.setBackgroundColor(Color.parseColor(colorcheck));        }
        if (EQUIPOIMPRESORA.equals("LISTO")){ impresora.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOMESAQ.equals("LISTO")){mesaq.setBackgroundColor(Color.parseColor(colorcheck));}
        if (EQUIPOMODULOSG.equals("LISTO")){modulosg.setBackgroundColor(Color.parseColor(colorcheck));}
        if (EQUIPOSELLADOR.equals("LISTO")){sellador.setBackgroundColor(Color.parseColor(colorcheck));}

        String EQUIPOBASCULA = sharedPreferences.getString("EQUIPOBASCULA","");
        String EQUIPOBISTURI = sharedPreferences.getString("EQUIPOBISTURI","");
        String EQUIPOEXTRACTOR = sharedPreferences.getString("EQUIPOEXTRACTOR","");
        String EQUIPOGLUCOMETRO = sharedPreferences.getString("EQUIPOGLUCOMETRO","");
        String EQUIPOHOJALARI = sharedPreferences.getString("EQUIPOHOJALARI","");
        String EQUIPOLARINGO = sharedPreferences.getString("EQUIPOLARINGO","");
        String EQUIPOMOFETAL = sharedPreferences.getString("EQUIPOMOFETAL","");
        String EQUIPONEGATOS = sharedPreferences.getString("EQUIPONEGATOS","");
        String EQUIPOOCTAMO = sharedPreferences.getString("EQUIPOOCTAMO","");
        String EQUIPOOTOS = sharedPreferences.getString("EQUIPOOTOS","");
        String EQUIPOOXIMETRO = sharedPreferences.getString("EQUIPOOXIMETRO","");
        String EQUIPOVIDEOLARINGO = sharedPreferences.getString("EQUIPOVIDEOLARINGO","");

        if (EQUIPOBASCULA.equals("LISTO")){ bascula.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOBISTURI.equals("LISTO")){ bisturi.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOEXTRACTOR.equals("LISTO")){ extractor.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOGLUCOMETRO.equals("LISTO")){ glucometro.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOHOJALARI.equals("LISTO")){ hojalari.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOLARINGO.equals("LISTO")){ laringo.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOMOFETAL.equals("LISTO")){ mofetal.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPONEGATOS.equals("LISTO")){ negatos.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOOCTAMO.equals("LISTO")){ octamo.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOOTOS.equals("LISTO")){ otos.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOOXIMETRO.equals("LISTO")){ oximetro.setBackgroundColor(Color.parseColor(colorcheck)); }
        if (EQUIPOVIDEOLARINGO.equals("LISTO")){ videolaringo.setBackgroundColor(Color.parseColor(colorcheck)); }
    }
    public void saveData(){
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("EQUIPO",equipo);
        editor.apply();
        //Toast.makeText(this, "Data saved"+sum1+"--"+sum2+"--"+sum3+"--"+sum4+"--"+sum5, Toast.LENGTH_SHORT).show();
    }
    public void clearData(){
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        SharedPreferences sharedPreferencesL = getSharedPreferences(SHARED_PREFSl, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferencesL.edit();
        editor1.putString("imgpath", imgpath);
        editor1.putString("EMPRESA",HOSPITAL);
        editor1.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        Toast.makeText(this, "DOCUMENTO GUARDADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
    }
    public void clearData1(){
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();

        SharedPreferences sharedPreferencesL = getSharedPreferences(SHARED_PREFSl, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferencesL.edit();
        editor1.putString("imgpath", imgpath);
        editor1.putString("EMPRESA",HOSPITAL);
        editor1.apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        Toast.makeText(this, "BITACORA LIMPIADA", Toast.LENGTH_SHORT).show();
    }
    public void seluregncias(){
        arcoenc.setVisibility(View.GONE);
        cunacalor.setVisibility(View.GONE);
        electerio.setVisibility(View.GONE);
        incu.setVisibility(View.GONE);
        maqanestesia.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);
        torreendo.setVisibility(View.GONE);
        torrelapa.setVisibility(View.GONE);

        bisturi.setVisibility(View.GONE);
        mofetal.setVisibility(View.GONE);

        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        inyector.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        lipo.setVisibility(View.GONE);
        torniquete.setVisibility(View.GONE);

        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void seluci(){
        arcoenc.setVisibility(View.GONE);
        cunacalor.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        electerio.setVisibility(View.GONE);
        incu.setVisibility(View.GONE);
        maqanestesia.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);
        torreendo.setVisibility(View.GONE);
        torrelapa.setVisibility(View.GONE);

        bisturi.setVisibility(View.GONE);
        mofetal.setVisibility(View.GONE);

        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        inyector.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        lipo.setVisibility(View.GONE);
        torniquete.setVisibility(View.GONE);

        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void selquirofano(){

        asisresp.setVisibility(View.GONE);
        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        desviasareas.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        camah.setVisibility(View.GONE);
        camat.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void selrecu(){
        maqanestesia.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);
        torrelapa.setVisibility(View.GONE);
        torreendo.setVisibility(View.GONE);
        asisresp.setVisibility(View.GONE);
        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        desviasareas.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        camah.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);

    }
    public void selendo(){
        arcoenc.setVisibility(View.GONE);
        cunacalor.setVisibility(View.GONE);
        electerio.setVisibility(View.GONE);
        incu.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);

        bisturi.setVisibility(View.GONE);
        mofetal.setVisibility(View.GONE);

        asisresp.setVisibility(View.GONE);
        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        desviasareas.setVisibility(View.GONE);
        inyector.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        lipo.setVisibility(View.GONE);
        torniquete.setVisibility(View.GONE);

        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        camah.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void selucin(){
        arcoenc.setVisibility(View.GONE);
        bombainfusion.setVisibility(View.GONE);
        electerio.setVisibility(View.GONE);
        maqanestesia.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);
        torreendo.setVisibility(View.GONE);
        torrelapa.setVisibility(View.GONE);

        bisturi.setVisibility(View.GONE);
        mofetal.setVisibility(View.GONE);

        asisresp.setVisibility(View.GONE);
        autoclave.setVisibility(View.GONE);
        desviasareas.setVisibility(View.GONE);
        inyector.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        lipo.setVisibility(View.GONE);
        torniquete.setVisibility(View.GONE);

        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        camah.setVisibility(View.GONE);
        camat.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void selhemo(){
        cunacalor.setVisibility(View.GONE);
        incu.setVisibility(View.GONE);
        microscopioq.setVisibility(View.GONE);
        torreendo.setVisibility(View.GONE);
        torrelapa.setVisibility(View.GONE);

        bisturi.setVisibility(View.GONE);
        mofetal.setVisibility(View.GONE);

        asisresp.setVisibility(View.GONE);
        autoclave.setVisibility(View.GONE);
        calentabiberon.setVisibility(View.GONE);
        desviasareas.setVisibility(View.GONE);
        esteelectrico.setVisibility(View.GONE);
        lavadora.setVisibility(View.GONE);
        lipo.setVisibility(View.GONE);
        torniquete.setVisibility(View.GONE);

        prevacio.setVisibility(View.GONE);
        bandaesfuerzo.setVisibility(View.GONE);
        camah.setVisibility(View.GONE);
        camat.setVisibility(View.GONE);
        cuna.setVisibility(View.GONE);
        mesaq.setVisibility(View.GONE);
        sellador.setVisibility(View.GONE);
    }
    public void login(){
        datosdialog login = new datosdialog();
        login.show(getSupportFragmentManager(), "VALIDACION");
    }
    public void generarPdf() {
        try {

            File path1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
            Date fecha = new Date();
            String fechabien = dateFormat.format(fecha);
            fechabien = fechabien.replaceAll("/","");
            String horabien = horaFormat.format(fecha);
            String AREAPDF=AREA.getText().toString();

            String CA="/VERIFICACIONES EQUIPO MEDICO/OTRAS AREAS";
            if(AREAPDF.equals("URGENCIAS")) {CA="/VERIFICACIONES EQUIPO MEDICO/URGENCIAS";}
            if(AREAPDF.equals("UCI")) {CA="/VERIFICACIONES EQUIPO MEDICO/UCI";}
            if(AREAPDF.equals("QUIROFANO")) {CA="/VERIFICACIONES EQUIPO MEDICO/QUIROFANO";}
            if(AREAPDF.equals("ENDOSCOPIA")) {CA="/VERIFICACIONES EQUIPO MEDICO/ENDOSCOPIA";}
            if(AREAPDF.equals("UCIN")) {CA="/VERIFICACIONES EQUIPO MEDICO/UCIN";}
            if(AREAPDF.equals("HEMODINAMIA")) {CA="/VERIFICACIONES EQUIPO MEDICO/HEMODINAMIA";}
            if(AREAPDF.equals("RECUPERACION")) {CA="/VERIFICACIONES EQUIPO MEDICO/RECUPERACION";}

            File f = new File(path1.getAbsolutePath()+CA);
            FileOutputStream ficheroPdf = new FileOutputStream(f.getAbsolutePath()+"/"+AREAPDF+fechabien+".pdf");
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VERIFICACIONES EQUIPO MEDICO");
            Image imagen = Image.getInstance(path.getAbsolutePath()+"/firma.png");
            File pathIMG = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VERIFICACIONES EQUIPO MEDICO"+"/LOGO.jpg");
            Image imagen1 = Image.getInstance(path.getAbsolutePath()+"/logo.jpg");


            /*Uri imageUri = Uri.parse(imgpath);
            String imagePath = getRealPathFromURI(imageUri);
            File path2 = new File(imagePath);*/
            documento.open();
            if(true) {
                if(pathIMG.exists()) {
                    imagen1.setAlignment(Element.ALIGN_CENTER);
                    imagen1.scaleAbsolute(170, 70);
                    imagen1.setSpacingAfter(10);
                    documento.add(imagen1);
               }

                fechabien = dateFormat.format(fecha);
                //IMAGEN DEL HOSPITAL
                Font ftitulo= new Font(Font.FontFamily.COURIER,15.0f,Color.GREEN);
                Paragraph p2=new Paragraph("BITACORA DE REVISION DIARIA DE EQUIPO MEDICO "+HOSPITAL+" ",ftitulo);
                p2.setLeading(1, 1);
                p2.setAlignment(Element.ALIGN_CENTER);
                p2.setSpacingAfter(10);
                p2.setSpacingBefore(10);
                documento.add(p2);
                PdfPTable AREA = new PdfPTable(2);
                AREA.addCell("AREA: "+AREAPDF);
                AREA.addCell("FECHA REALIZACION: "+fechabien+"                   HORA: "+horabien);
                AREA.setSpacingAfter(10);
                AREA.setSpacingBefore(10);
                AREA.setWidths(new int[]{200,200});
                documento.add(AREA);

                if(true) {
                    String EQUIPOMONITOR = sharedPreferences.getString("EQUIPOMONITOR", "");
                    String EQUIPOARCOENC = sharedPreferences.getString("EQUIPOARCOENC", "");
                    String EQUIPOBOMBAINFUSION = sharedPreferences.getString("EQUIPOBOMBAINFUSION", "");
                    String EQUIPOCUNACALOR = sharedPreferences.getString("EQUIPOCUNACALOR", "");
                    String EQUIPOELECTERIO = sharedPreferences.getString("EQUIPOELECTERIO", "");
                    String EQUIPOELECGRAFO = sharedPreferences.getString("EQUIPOELECGRAFO", "");
                    String EQUIPOINCU = sharedPreferences.getString("EQUIPOINCU", "");
                    String EQUIPOLAMP = sharedPreferences.getString("EQUIPOLAMP", "");
                    String EQUIPOMAQUINANESTECIA = sharedPreferences.getString("EQUIPOMAQUINANESTECIA", "");
                    String EQUIPOMICROSCOPIO = sharedPreferences.getString("EQUIPOMICROSCOPIO", "");
                    String EQUIPOPOLIGRAFO = sharedPreferences.getString("EQUIPOPOLIGRAFO", "");
                    String EQUIPOTORREENDO = sharedPreferences.getString("EQUIPOTORREENDO", "");
                    String EQUIPOTORRELAPARO = sharedPreferences.getString("EQUIPOTORRELAPARO", "");
                    String EQUIPOULTRASONIDO = sharedPreferences.getString("EQUIPOULTRASONIDO", "");
                    String EQUIPOVENTILADOR = sharedPreferences.getString("EQUIPOVENTILADOR", "");
                    String EQUIPODESFIBRILADOR = sharedPreferences.getString("EQUIPODESFIBRILADOR", "");

                    if (EQUIPOMONITOR.equals("LISTO")) {
                        String equipoS = "monitor";
                        String Nequipo = "MONITOR DE SIGNOS VITALES";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPODESFIBRILADOR.equals("LISTO")) {
                        String equipoS = "desfibrilador";
                        String Nequipo = "DESFIBRILADOR";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOVENTILADOR.equals("LISTO")) {
                        String equipoS = "ventilador";
                        String Nequipo = "VENTILADOR MECANICO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOMAQUINANESTECIA.equals("LISTO")) {
                        String equipoS = "maqanestesia";
                        String Nequipo = "MAQUINA DE ANESTECIA";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOELECGRAFO.equals("LISTO")) {
                        String equipoS = "elecgrafo";
                        String Nequipo = "ELECTROCARDIOGRAFO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOINCU.equals("LISTO")) {
                        String equipoS = "incu";
                        String Nequipo = "INCUBADORA";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOLAMP.equals("LISTO")) {
                        String equipoS = "lamp";
                        String Nequipo = "LAMPARA";
                        checarmonitores(equipoS, Nequipo);
                    }

                    if (EQUIPOARCOENC.equals("LISTO")) {
                        String equipoS = "arcoenc";
                        String Nequipo = "ARCO EN C";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOELECTERIO.equals("LISTO")) {
                        String equipoS = "electerio";
                        String Nequipo = "ELECTROCAUTERIO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOPOLIGRAFO.equals("LISTO")) {
                        String equipoS = "poligrafo";
                        String Nequipo = "POLIGRAFO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOCUNACALOR.equals("LISTO")) {
                        String equipoS = "cunacalor";
                        String Nequipo = "CUNA DE CARLOR RADIANTE";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOULTRASONIDO.equals("LISTO")) {
                        String equipoS = "ultrasonido";
                        String Nequipo = "ULTRASONIDO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOTORREENDO.equals("LISTO")) {
                        String equipoS = "torreendo";
                        String Nequipo = "TORRE DE ENDOSCOPIA";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOTORRELAPARO.equals("LISTO")) {
                        String equipoS = "torrelapa";
                        String Nequipo = "TORRE LAPAROSCOPIA";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOBOMBAINFUSION.equals("LISTO")) {
                        String equipoS = "bombainfusion";
                        String Nequipo = "BOMBA DE INFUSION";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOMICROSCOPIO.equals("LISTO")) {
                        String equipoS = "microscopioq";
                        String Nequipo = "MICROSCOPIO QUIRURGICO";
                        checarmonitores(equipoS, Nequipo);
                    }

                }//equipo medico
                if(true) {
                    String EQUIPOASISRESP = sharedPreferences.getString("EQUIPOASISRESP", "");
                    String EQUIPOAUTOCLAVE = sharedPreferences.getString("EQUIPOAUTOCLAVE", "");
                    String EQUIPOCALENTABIBERON = sharedPreferences.getString("EQUIPOCALENTABIBERON", "");
                    String EQUIPODESVIASAREAS = sharedPreferences.getString("EQUIPODESVIASAREAS", "");
                    String EQUIPOESTEELECTRICO = sharedPreferences.getString("EQUIPOESTEELECTRICO", "");
                    String EQUIPOINYECTOR = sharedPreferences.getString("EQUIPOINYECTOR", "");
                    String EQUIPOLAVADORA = sharedPreferences.getString("EQUIPOLAVADORA", "");
                    String EQUIPOLIPO = sharedPreferences.getString("EQUIPOLIPO", "");
                    String EQUIPOTORNIQUETE = sharedPreferences.getString("EQUIPOTORNIQUETE", "");
                    //String EQUIPO = sharedPreferences.getString("EQUIPO","");

                    if (EQUIPOASISRESP.equals("LISTO")) {
                        String equipoS = "asisresp";
                        String Nequipo = "ASISTENTE RESPIRATORIO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOAUTOCLAVE.equals("LISTO")) {
                        String equipoS = "autoclave";
                        String Nequipo = "AUTOCLAVE";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOCALENTABIBERON.equals("LISTO")) {
                        String equipoS = "calentabiberon";
                        String Nequipo = "CALENTADOR DE BIBERON";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPODESVIASAREAS.equals("LISTO")) {
                        String equipoS = "desviasareas";
                        String Nequipo = "DESOBSTRURADOR VIAS AEREAS";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOESTEELECTRICO.equals("LISTO")) {
                        String equipoS = "esteelectrico";
                        String Nequipo = "ESTERILIZADOR ELECTRICO";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOINYECTOR.equals("LISTO")) {
                        String equipoS = "inyector";
                        String Nequipo = "INYECTOR DE CONTRASTE";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOLAVADORA.equals("LISTO")) {
                        String equipoS = "lavadora";
                        String Nequipo = "LAVADORA TERMODESINFECTADORA";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOLIPO.equals("LISTO")) {
                        String equipoS = "lipo";
                        String Nequipo = "MAQUINA LIPOSUCCION";
                        checarmonitores(equipoS, Nequipo);
                    }
                    if (EQUIPOTORNIQUETE.equals("LISTO")) {
                        String equipoS = "torniquete";
                        String Nequipo = "TORNIQUETE NEUMATICO";
                        checarmonitores(equipoS, Nequipo);
                    }


                }//equipo especializado
                if(true){
                    String EQUIPOPREVACIO = sharedPreferences.getString("EQUIPOPREVACIO","");
                    String EQUIPOBANDAESFUERZO = sharedPreferences.getString("EQUIPOBANDAESFUERZO","");
                    String EQUIPOBAUMAPARED = sharedPreferences.getString("EQUIPOBAUMAPARED","");
                    String EQUIPOCAMAH = sharedPreferences.getString("EQUIPOCAMAH","");
                    String EQUIPOCAMAT = sharedPreferences.getString("EQUIPOCAMAT","");
                    String EQUIPOCARROPARO = sharedPreferences.getString("EQUIPOCARROPARO","");
                    String EQUIPOCUNA = sharedPreferences.getString("EQUIPOCUNA","");
                    String EQUIPOIMPRESORA = sharedPreferences.getString("EQUIPOIMPRESORA","");
                    String EQUIPOMESAQ = sharedPreferences.getString("EQUIPOMESAQ","");
                    String EQUIPOMODULOSG = sharedPreferences.getString("EQUIPOMODULOSG","");
                    String EQUIPOSELLADOR = sharedPreferences.getString("EQUIPOSELLADOR","");

                    if (EQUIPOPREVACIO.equals("LISTO")){ String equipoS="prevacio"; String Nequipo="ALMACEN DE PREVACIO"; checarinmobiliario(equipoS,Nequipo); }
                    if (EQUIPOBANDAESFUERZO.equals("LISTO")){  String equipoS="bandaesfuerzo"; String Nequipo="BANDA DE ESFUERZO"; checarinmobiliario(equipoS,Nequipo);  }
                    if (EQUIPOBAUMAPARED.equals("LISTO")){  String equipoS="baumapared"; String Nequipo="BAUMANOMETRO DE PARED"; checarinmobiliario(equipoS,Nequipo);  }
                    if (EQUIPOCAMAH.equals("LISTO")){ String equipoS="camah"; String Nequipo="CAMA HOSPITALARIA / GINECOLOGICA"; checarinmobiliario(equipoS,Nequipo);  }
                    if (EQUIPOCAMAT.equals("LISTO")){ String equipoS="camat"; String Nequipo="CAMILLA DE TRASLADOS"; checarinmobiliario(equipoS,Nequipo);  }
                    if (EQUIPOCARROPARO.equals("LISTO")){ String equipoS="carroparo"; String Nequipo="CARRO DE PARO / EMERGENCIA"; checarinmobiliario(equipoS,Nequipo);   }
                    if (EQUIPOCUNA.equals("LISTO")){  String equipoS="cuna"; String Nequipo="CUNA"; checarinmobiliario(equipoS,Nequipo);      }
                    if (EQUIPOIMPRESORA.equals("LISTO")){ String equipoS="impresora"; String Nequipo="IMPRESORA"; checarinmobiliario(equipoS,Nequipo); }
                    if (EQUIPOMESAQ.equals("LISTO")){    String equipoS="mesaq"; String Nequipo="MESA QUIRURGICA"; checarinmobiliario(equipoS,Nequipo); }
                    if (EQUIPOMODULOSG.equals("LISTO")){  String equipoS="modulosg"; String Nequipo="MODULO DE SOPORTE DE VIDA"; checarinmobiliario(equipoS,Nequipo); }
                    if (EQUIPOSELLADOR.equals("LISTO")){  String equipoS="sellador"; String Nequipo="SELLADOR DE BOLSAS ESTERILIZACION"; checarinmobiliario(equipoS,Nequipo); }
                }//inmobiliaria
                if(true){
                    String EQUIPOBASCULA = sharedPreferences.getString("EQUIPOBASCULA","");
                    String EQUIPOBISTURI = sharedPreferences.getString("EQUIPOBISTURI","");
                    String EQUIPOEXTRACTOR = sharedPreferences.getString("EQUIPOEXTRACTOR","");
                    String EQUIPOGLUCOMETRO = sharedPreferences.getString("EQUIPOGLUCOMETRO","");
                    String EQUIPOHOJALARI = sharedPreferences.getString("EQUIPOHOJALARI","");
                    String EQUIPOLARINGO = sharedPreferences.getString("EQUIPOLARINGO","");
                    String EQUIPOMOFETAL = sharedPreferences.getString("EQUIPOMOFETAL","");
                    String EQUIPONEGATOS = sharedPreferences.getString("EQUIPONEGATOS","");
                    String EQUIPOOCTAMO = sharedPreferences.getString("EQUIPOOCTAMO","");
                    String EQUIPOOTOS = sharedPreferences.getString("EQUIPOOTOS","");
                    String EQUIPOOXIMETRO = sharedPreferences.getString("EQUIPOOXIMETRO","");
                    String EQUIPOVIDEOLARINGO = sharedPreferences.getString("EQUIPOVIDEOLARINGO","");


                    if (EQUIPOBASCULA.equals("LISTO")){ String equipoS="bascula"; String Nequipo="BASCULA"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOBISTURI.equals("LISTO")){ String equipoS="bisturi"; String Nequipo="BISTURI ARMONICO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOEXTRACTOR.equals("LISTO")){ String equipoS="extractor"; String Nequipo="EXTRACTORES DE SEÑAL"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOGLUCOMETRO.equals("LISTO")){ String equipoS="glucometro"; String Nequipo="GLUCOMETRO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOHOJALARI.equals("LISTO")){ String equipoS="hojalari"; String Nequipo="HOJAS PARA LARINGOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOLARINGO.equals("LISTO")){ String equipoS="laringo"; String Nequipo="LARINGOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOMOFETAL.equals("LISTO")){ String equipoS="mofetal"; String Nequipo="MONITOR FETAL"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPONEGATOS.equals("LISTO")){ String equipoS="negatos"; String Nequipo="NEGATOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOOCTAMO.equals("LISTO")){String equipoS="octamo"; String Nequipo="OCTALMOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOOTOS.equals("LISTO")){ String equipoS="otos"; String Nequipo="OTOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOOXIMETRO.equals("LISTO")){ String equipoS="oximetro"; String Nequipo="OXIMETRO"; checarinstrumentos(equipoS,Nequipo); }
                    if (EQUIPOVIDEOLARINGO.equals("LISTO")){ String equipoS="videolaringo"; String Nequipo="VIDEO-LARINGOSCOPIO"; checarinstrumentos(equipoS,Nequipo); }
                }//instrumentos

                PdfPTable CREDENCIALES = new PdfPTable(1);
                PdfPCell CRS = new PdfPCell();
                CRS.setBorder(Rectangle.NO_BORDER);

                String ING = sharedPreferences.getString("INGE","");
                ftitulo= new Font(Font.FontFamily.COURIER,12.0f,Color.GREEN);
                Paragraph TX=new Paragraph("DOCUMENTO ELABORADO POR:",ftitulo);
                TX.setAlignment(Element.ALIGN_CENTER);
                TX.setSpacingAfter(10);
                TX.setSpacingBefore(10);

                CRS.addElement(TX);
                //documento.add(CRS);

                ftitulo= new Font(Font.FontFamily.COURIER,10.0f,Color.GREEN);
                Paragraph INGE=new Paragraph(ING,ftitulo);
                INGE.setLeading(1, 1);
                INGE.setAlignment(Element.ALIGN_CENTER);
                INGE.setSpacingAfter(10);
                INGE.setSpacingBefore(10);
                CRS.addElement(INGE);
                //documento.add(INGE);

                ftitulo= new Font(Font.FontFamily.COURIER,12.0f,Color.GREEN);
                TX=new Paragraph("DOCUMENTO VALIDADO POR:",ftitulo);
                TX.setAlignment(Element.ALIGN_CENTER);
                TX.setSpacingAfter(1);
                TX.setSpacingBefore(1);
                CRS.addElement(TX);
                //documento.add(TX);

                //FIRMA
                imagen.setAlignment(Element.ALIGN_CENTER);
                imagen.scaleAbsolute(200,100);
                imagen.setRotationDegrees(-90);
                imagen.setSpacingAfter(1);
                imagen.setSpacingBefore(1);
                CRS.addElement(imagen);
                //documento.add(imagen);

                TX=new Paragraph("____________________________________");
                TX.setAlignment(Element.ALIGN_CENTER);
                CRS.addElement(TX);
                //documento.add(TX);

                ftitulo= new Font(Font.FontFamily.COURIER,10.0f,Color.GREEN);
                String NOMBRE = sharedPreferences.getString("NOMBRE","");
                Paragraph FIRMA=new Paragraph(NOMBRE,ftitulo);
                FIRMA.setLeading(1, 1);
                FIRMA.setAlignment(Element.ALIGN_CENTER);
                FIRMA.setSpacingAfter(1);
                FIRMA.setSpacingBefore(1);
                CRS.addElement(FIRMA);
                CREDENCIALES.addCell(CRS);
                documento.add(CREDENCIALES);

            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            documento.close();
            if(bandera == 1){
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toastalerta,null);
                Toast toast = new Toast(getApplicationContext());
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                bandera=0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            // Do something after 5s = 5000ms

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after 5s = 5000ms
                                clearData();
                            }
                        }, 3500);
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toastalerta1,null);
                        Toast toast = new Toast(getApplicationContext());
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                    }
                }, 3500);
            }else{
                clearData();
            }

        }
    }

    public void checarmonitores(String equi,String name) throws DocumentException {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        equipo=equi;
        Integer EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E1",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"1","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE1",name+" 1");////////////////////
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"11",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"12",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"13",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"14",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"15",0);
            Integer numero =1;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E2",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"2","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE2",name+" 2");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"21",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"22",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"23",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"24",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"25",0);
            Integer numero =2;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E3",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"3","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE3",name+" 3");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"31",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"32",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"33",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"34",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"35",0);
            Integer numero =3;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E4",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"4","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE4",name+" 4");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"41",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"42",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"43",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"44",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"45",0);
            Integer numero =4;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E5",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"5","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE5",name+" 5");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"51",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"52",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"53",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"54",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"55",0);
            Integer numero =5;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E6",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"6","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE6",name+" 6");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"61",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"62",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"63",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"64",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"65",0);
            Integer numero =6;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E7",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"7","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE7",name+" 7");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"71",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"72",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"73",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"74",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"75",0);
            Integer numero =7;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E8",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"8","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE8",name+" 8");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"81",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"82",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"83",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"84",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"85",0);
            Integer numero =8;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E9",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"9","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE9",name+" 9");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"91",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"92",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"93",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"94",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"95",0);
            Integer numero =9;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E10",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"10","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE10",name+" 10");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"101",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"102",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"103",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"104",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"105",0);
            Integer numero =10;
            formatoMonitor(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITOR14,EQUIPOMONITOR15,EQUIPOMONITORO1,numero,EQUIPOMONITOROE1);
        }
    }
    public void formatoMonitor(String name, Integer EQUIPOMONITOR11, Integer EQUIPOMONITOR12, Integer EQUIPOMONITOR13,
                               Integer EQUIPOMONITOR14,Integer EQUIPOMONITOR15, String EQUIPOMONITORO1,Integer n,String EQUIPOMONITOROE1) throws DocumentException {
        //TITULO DEL EQUIPO
        PdfPTable tabla = new PdfPTable(1);
        Font ftitulo= new Font(Font.FontFamily.COURIER,10.0f);
        ftitulo.setColor(5, 0, 163);
        PdfPCell cell = new PdfPCell(new Phrase(EQUIPOMONITOROE1,ftitulo));
        tabla.addCell(cell);
        documento.add(tabla);
        //TEXTO DE FORMATO
        PdfPTable tabla2 = new PdfPTable(4);
        Font ftabla= new Font(Font.FontFamily.COURIER,8.0f);
        ftabla.setColor(0, 0, 0);
        PdfPCell celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.setWidths(new int[]{125,40,125,40});
        tabla2.addCell(celleq);
        documento.add(tabla2);
        //RELLENOD DE FORMATO
        PdfPTable tabla3 = new PdfPTable(4);
        ftabla= new Font(Font.FontFamily.COURIER,5.0f);
        PdfPCell cell2 = new PdfPCell(new Phrase("1) CABLE CORRIENTE ALTERNA CONECTADO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR11 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        cell2 = new PdfPCell(new Phrase("2) ESTADO FISICO EXTERIOR DEL EQUIPO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        cell2 = new PdfPCell(new Phrase("3) ENCENDIDO CORRECTO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        cell2 = new PdfPCell(new Phrase("4) CABLE CORRIENTE ALTERNA CONECTADO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR14 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        cell2 = new PdfPCell(new Phrase("5) CORRECTA LECTURA DE PARAMETROS",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR15 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        tabla3.addCell("");
        tabla3.addCell("");
        tabla3.setWidths(new int[]{125,40,125,40});
        documento.add(tabla3);
        //OBSERVACIONES
        PdfPTable tablao = new PdfPTable(2);
        tablao.setWidths(new int[]{60,230});
        Font fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(255, 0, 0);
        PdfPCell cello = new PdfPCell(new Phrase("OBSERVACIONES: ",fobs));
        tablao.addCell(cello);
        fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(0, 0, 0);
        cello = new PdfPCell(new Phrase(EQUIPOMONITORO1,fobs));
        tablao.addCell(cello);
        tablao.setSpacingAfter(10);
        documento.add(tablao);
    }

    public void checarinmobiliario(String equi,String name) throws DocumentException {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        equipo=equi;
        Integer EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E1",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"1","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE1",name+" 1");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"11",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"12",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"13",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"14",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"15",0);
            Integer numero =1;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E2",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"2","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE2",name+" 1");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"21",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"22",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"23",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"24",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"25",0);
            Integer numero =2;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E3",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"3","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE3",name+" 3");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"31",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"32",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"33",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"34",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"35",0);
            Integer numero =3;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E4",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"4","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE4",name+" 4");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"41",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"42",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"43",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"44",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"45",0);
            Integer numero =4;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E5",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"5","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE5",name+" 5");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"51",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"52",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"53",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"54",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"55",0);
            Integer numero =5;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E6",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"6","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE6",name+" 6");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"61",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"62",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"63",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"64",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"65",0);
            Integer numero =6;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E7",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"7","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE7",name+" 7");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"71",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"72",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"73",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"74",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"75",0);
            Integer numero =7;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E8",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"8","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE8",name+" 8");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"81",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"82",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"83",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"84",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"85",0);
            Integer numero =8;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E9",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"9","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE9",name+" 9");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"91",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"92",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"93",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"94",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"95",0);
            Integer numero =9;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E10",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"10","TODO EN CORRECTO ESTADO");
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE10",name+" 10");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"101",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"102",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"103",0);
            Integer EQUIPOMONITOR14 = sharedPreferences.getInt(this.equipo +"104",0);
            Integer EQUIPOMONITOR15 = sharedPreferences.getInt(this.equipo +"105",0);
            Integer numero =10;
            name=EQUIPOMONITOROE1;
            formatoInmo(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }
    }
    public void formatoInmo(String name, Integer EQUIPOMONITOR11, Integer EQUIPOMONITOR12, Integer EQUIPOMONITOR13, String EQUIPOMONITORO1,Integer n) throws DocumentException {
        //TITULO DEL EQUIPO
        PdfPTable tabla = new PdfPTable(1);
        Font ftitulo= new Font(Font.FontFamily.COURIER,10.0f);
        ftitulo.setColor(5, 0, 163);
        PdfPCell cell = new PdfPCell(new Phrase(name,ftitulo));
        tabla.addCell(cell);
        documento.add(tabla);
        //TEXTO DE FORMATO
        PdfPTable tabla2 = new PdfPTable(4);
        Font ftabla= new Font(Font.FontFamily.COURIER,8.0f);
        ftabla.setColor(0, 0, 0);
        PdfPCell celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.setWidths(new int[]{125,40,125,40});
        tabla2.addCell(celleq);
        documento.add(tabla2);
        //RELLENOD DE FORMATO
        PdfPTable tabla3 = new PdfPTable(4);
        ftabla= new Font(Font.FontFamily.COURIER,5.0f);
        PdfPCell cell2 = new PdfPCell(new Phrase("1) ESTADO FISICO EXTERIOR DEL MUEBLE / APARATO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR11 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);


        if(equipo.equals("carroparo") || equipo.equals("camat") || equipo.equals("camah") || equipo.equals("cuna")){
            cell2 = new PdfPCell(new Phrase("2) MOVILIDAD EN BUEN ESTADO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("3) FUNCIONAMIENTO CORRECTO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
        }
        else if(equipo.equals("baumapared")){
            cell2 = new PdfPCell(new Phrase("2) PRESION DE MANOMETRO CORRECTA",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("3) CIRCUITO DE TOMA DE PRESION SIN FUGAS",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
        }
        else if(equipo.equals("modulosg")){
            cell2 = new PdfPCell(new Phrase("2) FUNCIONAMIENTO CORRECTO DE TOMACORRIENTES",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("3) PRESION DE MANOMETROS CORRECTAS Y FLUJO DE TORRES",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
        }
        else{
            cell2 = new PdfPCell(new Phrase("2) CABLE CORRIENTE ALTERNA CONECTADO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("3) FUNCIONAMIENTO CORRECTO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
        }
        tabla3.addCell("");
        tabla3.addCell("");
        tabla3.setWidths(new int[]{125,40,125,40});
        documento.add(tabla3);
        //OBSERVACIONES
        PdfPTable tablao = new PdfPTable(2);
        tablao.setWidths(new int[]{60,230});
        Font fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(255, 0, 0);
        PdfPCell cello = new PdfPCell(new Phrase("OBSERVACIONES: ",fobs));
        tablao.addCell(cello);
        fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(0, 0, 0);
        cello = new PdfPCell(new Phrase(EQUIPOMONITORO1,fobs));
        tablao.addCell(cello);
        tablao.setSpacingAfter(10);
        documento.add(tablao);
    }

    public void checarinstrumentos(String equi,String name) throws DocumentException {
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        equipo=equi;
        Integer EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E1",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"1","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"11",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"12",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"13",0);
            Integer numero =1;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE1",name+" 1");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E2",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"2","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"21",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"22",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"23",0);
            Integer numero =2;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE2",name+" 2");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E3",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"3","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"31",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"32",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"33",0);
            Integer numero =3;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE3",name+" 3");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E4",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"4","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"41",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"42",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"43",0);
            Integer numero =4;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE4",name+" 4");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E5",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"5","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"51",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"52",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"53",0);
            Integer numero =5;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE5",name+" 5");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E6",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"6","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"61",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"62",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"63",0);
            Integer numero =6;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE6",name+" 6");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E7",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"7","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"71",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"72",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"73",0);
            Integer numero =7;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE7",name+" 7");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E8",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"8","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"81",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"82",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"83",0);
            Integer numero =8;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE8",name+" 8");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E9",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"9","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"91",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"92",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"93",0);
            Integer numero =9;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE9",name+" 9");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }

        EQUIPOMONITOR1 = sharedPreferences.getInt(this.equipo +"E10",0);
        if(EQUIPOMONITOR1==1){
            String EQUIPOMONITORO1 = sharedPreferences.getString(this.equipo +"10","TODO EN CORRECTO ESTADO");
            Integer EQUIPOMONITOR11 = sharedPreferences.getInt(this.equipo +"101",0);
            Integer EQUIPOMONITOR12 = sharedPreferences.getInt(this.equipo +"102",0);
            Integer EQUIPOMONITOR13 = sharedPreferences.getInt(this.equipo +"103",0);
            Integer numero =10;
            String EQUIPOMONITOROE1 = sharedPreferences.getString(this.equipo +"EE10",name+" 10");
            name=EQUIPOMONITOROE1;
            formatoInstru(name,EQUIPOMONITOR11,EQUIPOMONITOR12,EQUIPOMONITOR13,EQUIPOMONITORO1,numero);
        }
    }
    public void formatoInstru(String name, Integer EQUIPOMONITOR11, Integer EQUIPOMONITOR12, Integer EQUIPOMONITOR13, String EQUIPOMONITORO1,Integer n) throws DocumentException {
        //TITULO DEL EQUIPO
        PdfPTable tabla = new PdfPTable(1);
        Font ftitulo= new Font(Font.FontFamily.COURIER,10.0f);
        ftitulo.setColor(5, 0, 163);
        PdfPCell cell = new PdfPCell(new Phrase(name,ftitulo));
        tabla.addCell(cell);
        documento.add(tabla);
        //TEXTO DE FORMATO
        PdfPTable tabla2 = new PdfPTable(4);
        Font ftabla= new Font(Font.FontFamily.COURIER,8.0f);
        ftabla.setColor(0, 0, 0);
        PdfPCell celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("PUNTOS A VERIFICAR",ftabla));
        tabla2.addCell(celleq);
        celleq = new PdfPCell(new Phrase("ESTADO",ftabla));
        tabla2.setWidths(new int[]{125,40,125,40});
        tabla2.addCell(celleq);
        documento.add(tabla2);
        //RELLENOD DE FORMATO
        PdfPTable tabla3 = new PdfPTable(4);
        ftabla= new Font(Font.FontFamily.COURIER,5.0f);
        PdfPCell cell2 = new PdfPCell(new Phrase("1) ESTADO FISICO EXTERIOR DEL INSTRUMENTO",ftabla));
        tabla3.addCell(cell2);
        if(EQUIPOMONITOR11 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
        tabla3.addCell(cell2);
        if(equipo.equals("hojalari")){
            cell2 = new PdfPCell(new Phrase("2) FUNCIONAMIENTO CORRECTO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);

        }
        else{
            cell2 = new PdfPCell(new Phrase("2) CABLE AC CONECTADO / ALIMENTACION",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR12 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            cell2 = new PdfPCell(new Phrase("3) FUNCIONAMIENTO CORRECTO",ftabla));
            tabla3.addCell(cell2);
            if(EQUIPOMONITOR13 == 1){cell2 = new PdfPCell(new Phrase("X",ftabla));}else{cell2 = new PdfPCell(new Phrase("OK",ftabla)); }
            tabla3.addCell(cell2);
            tabla3.addCell("");
            tabla3.addCell("");
        }
        tabla3.setWidths(new int[]{125,40,125,40});
        documento.add(tabla3);
        //OBSERVACIONES
        PdfPTable tablao = new PdfPTable(2);
        tablao.setWidths(new int[]{60,230});
        Font fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(255, 0, 0);
        PdfPCell cello = new PdfPCell(new Phrase("OBSERVACIONES: ",fobs));
        tablao.addCell(cello);
        fobs= new Font(Font.FontFamily.COURIER,7.0f);
        fobs.setColor(0, 0, 0);
        cello = new PdfPCell(new Phrase(EQUIPOMONITORO1,fobs));
        tablao.addCell(cello);
        tablao.setSpacingAfter(10);
        documento.add(tablao);
    }

    @Override
    public void applyTexts(String encargado, String inge) {
        if (!encargado.equals("")) {
            if(!encargado.equals(" ")) {
                if (!inge.equals("")) {
                    if(!inge.equals(" ")) {
                        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        Toast.makeText(getApplicationContext(), "VALIDACION COMPLETA", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("NOMBRE", encargado.toUpperCase());
                        editor.putString("INGE", inge.toUpperCase());
                        editor.apply();
                        generarPdf();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "NO SE VALIDO DOCUMENTO", Toast.LENGTH_SHORT).show();
        }

    }


    class Lienzo extends View {
        float x = 0;
        float y = 0;
        String accion = "down";
        Path path = new Path();
        Paint firma = new Paint();
        Canvas canvas1= new Canvas();

        public Lienzo(Context context) {
            super(context);
            firma.setStyle(Paint.Style.STROKE);
            firma.setStrokeWidth(7);
            firma.setColor(Color.BLACK);
            setDrawingCacheEnabled(true);

        }

        protected void onDraw(Canvas canvas) {
            if (accion.equals("down")) {
                path.moveTo(x, y);
            }
            if (accion.equals("move")) {
                path.lineTo(x, y);
            }
            canvas.drawPath(path, firma);
            canvas1.drawPath(path, firma);

        }

        public boolean onTouchEvent(MotionEvent event) {
            x = event.getX();
            y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                accion = "move";
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                accion = "down";
                path.moveTo(x + 10, y);
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                png();
            }
            invalidate();
            return true;
        }


        @SuppressLint("WrongThread")
        public void png(){

                try {

                    View view = getWindow().getDecorView().getRootView();
                    View content = view;
                    this.setDrawingCacheEnabled(true);
                    Bitmap bitmap = this.getDrawingCache();

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VERIFICACIONES EQUIPO MEDICO/firma.png");
                    FileOutputStream ostream; try {
                        file.createNewFile();
                        ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                        ostream.flush();
                        ostream.close();
                        firmaS="si";
                        //Toast.makeText(getApplicationContext(), "FIRMA GENERADA", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) { e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }





                    /*String mPath = Environment.getExternalStorageDirectory().toString() + "/firma.jpg";
                    View v1 = getWindow().getDecorView().getRootView();
                    v1.setDrawingCacheEnabled(true);
                    Bitmap bitmap1 = Bitmap.createBitmap(v1.getDrawingCache());
                    v1.setDrawingCacheEnabled(false);
                    File imageFile = new File(mPath);
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();*/


                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
                }
            }


    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }else{
            return "";
        }
    }

    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }

}
