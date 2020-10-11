package com.example.login;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Timer;
import java.util.TimerTask;



public class equipomedico extends AppCompatActivity  {

    Switch monitor1;    LinearLayout linearLayoutm1;
    Switch monitor2;    LinearLayout linearLayoutm2;
    Switch monitor3;    LinearLayout linearLayoutm3;
    Switch monitor4;    LinearLayout linearLayoutm4;
    Switch monitor5;    LinearLayout linearLayoutm5;
    Switch monitor6;    LinearLayout linearLayoutm6;
    Switch monitor7;    LinearLayout linearLayoutm7;
    Switch monitor8;    LinearLayout linearLayoutm8;
    Switch monitor9;    LinearLayout linearLayoutm9;
    Switch monitor10;    LinearLayout linearLayoutm10;

    String colorcheck = "#2DCCD3";
    TextView TX1;String equipo;
    public static final String SHARED_PREFS = "sharedPrefs";
    private boolean presionado;
    String push="#2DCCD3";
    Timer tiempo=new Timer();
    String resultado = "";
    TimerTask ciclo;

    Integer sum1=0;Integer sum2=0;Integer sum3=0;Integer sum4=0;Integer sum5=0;
    Integer sum6=0;Integer sum7=0;Integer sum8=0;Integer sum9=0;Integer sum10=0;
    String OUM1 = "";    String OUM2 = "";    String OUM3 = "";    String OUM4 = "";    String OUM5 = "";
    String OUM6 = "";    String OUM7 = "";    String OUM8 = "";    String OUM9 = "";    String OUM10 = "";
    Integer rumx11=0; Integer rumx12=0; Integer rumx13=0; Integer rumx14=0; Integer rumx15=0;
    Integer rumx21=0; Integer rumx22=0; Integer rumx23=0; Integer rumx24=0; Integer rumx25=0;
    Integer rumx31=0; Integer rumx32=0; Integer rumx33=0; Integer rumx34=0; Integer rumx35=0;
    Integer rumx41=0; Integer rumx42=0; Integer rumx43=0; Integer rumx44=0; Integer rumx45=0;
    Integer rumx51=0; Integer rumx52=0; Integer rumx53=0; Integer rumx54=0; Integer rumx55=0;
    Integer rumx61=0; Integer rumx62=0; Integer rumx63=0; Integer rumx64=0; Integer rumx65=0;
    Integer rumx71=0; Integer rumx72=0; Integer rumx73=0; Integer rumx74=0; Integer rumx75=0;
    Integer rumx81=0; Integer rumx82=0; Integer rumx83=0; Integer rumx84=0; Integer rumx85=0;
    Integer rumx91=0; Integer rumx92=0; Integer rumx93=0; Integer rumx94=0; Integer rumx95=0;
    Integer rumx101=0; Integer rumx102=0; Integer rumx103=0; Integer rumx104=0; Integer rumx105=0;

    ImageButton qr1,qr2,qr3,qr4,qr5,qr6,qr7,qr8,qr9,qr10;
    String OUME1 = "";    String OUME2 = "";    String OUME3 = "";    String OUME4 = "";    String OUME5 = "";
    String OUME6 = "";    String OUME7 = "";    String OUME8 = "";    String OUME9 = "";    String OUME10 = "";
    String qrid="";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.monitor);

        TX1 = (TextView)findViewById(R.id.textView2);//titulo del equipo
        qr1 = (ImageButton)findViewById(R.id.qr1);qr2 = (ImageButton)findViewById(R.id.qr2);qr3 = (ImageButton)findViewById(R.id.qr3);qr4 = (ImageButton)findViewById(R.id.qr4);
        qr5 = (ImageButton)findViewById(R.id.qr5);qr6 = (ImageButton)findViewById(R.id.qr6);qr7 = (ImageButton)findViewById(R.id.qr7);qr8 = (ImageButton)findViewById(R.id.qr8);
        qr9 = (ImageButton)findViewById(R.id.qr9);qr10 = (ImageButton)findViewById(R.id.qr10);
        final ImageButton finalizar = (ImageButton) findViewById(R.id.button);
        monitor1 = (Switch) findViewById(R.id.sum1);        linearLayoutm1 = (LinearLayout)findViewById(R.id.linearLayoutm1);
        monitor2 = (Switch) findViewById(R.id.sum2);        linearLayoutm2 = (LinearLayout)findViewById(R.id.linearLayoutm2);
        monitor3 = (Switch) findViewById(R.id.sum3);        linearLayoutm3 = (LinearLayout)findViewById(R.id.linearLayoutm3);
        monitor4 = (Switch) findViewById(R.id.sum4);        linearLayoutm4 = (LinearLayout)findViewById(R.id.linearLayoutm4);
        monitor5 = (Switch) findViewById(R.id.sum5);        linearLayoutm5 = (LinearLayout)findViewById(R.id.linearLayoutm5);
        monitor6 = (Switch) findViewById(R.id.sum6);        linearLayoutm6 = (LinearLayout)findViewById(R.id.linearLayoutm6);
        monitor7 = (Switch) findViewById(R.id.sum7);        linearLayoutm7 = (LinearLayout)findViewById(R.id.linearLayoutm7);
        monitor8 = (Switch) findViewById(R.id.sum8);        linearLayoutm8 = (LinearLayout)findViewById(R.id.linearLayoutm8);
        monitor9 = (Switch) findViewById(R.id.sum9);        linearLayoutm9 = (LinearLayout)findViewById(R.id.linearLayoutm9);
        monitor10 = (Switch) findViewById(R.id.sum10);        linearLayoutm10 = (LinearLayout)findViewById(R.id.linearLayoutm10);
        final EditText Oum1 = (EditText)findViewById(R.id.oum1);       final EditText Oum2 = (EditText)findViewById(R.id.oum2);    final EditText Oum3 = (EditText)findViewById(R.id.oum3);
        final EditText Oum4 = (EditText)findViewById(R.id.oum4);     final EditText Oum5 = (EditText)findViewById(R.id.oum5);     final EditText Oum6 = (EditText)findViewById(R.id.oum6);
        final EditText Oum7 = (EditText)findViewById(R.id.oum7);       final EditText Oum8 = (EditText)findViewById(R.id.oum8);   final EditText Oum9 = (EditText)findViewById(R.id.oum9);
        final EditText Oum10 = (EditText)findViewById(R.id.oum10);
        final Drawable d = finalizar.getBackground();

        loadData();
        creacionOncreate();
        finalizar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            finalizar.setScaleX((float) 1.2);
                            finalizar.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        finalizar.setScaleX((float) 1);
                        finalizar.setScaleY((float) 1);
                        OUM1 = Oum1.getText().toString();
                        OUM2 = Oum2.getText().toString();
                        OUM3 = Oum3.getText().toString();
                        OUM4 = Oum4.getText().toString();
                        OUM5 = Oum5.getText().toString();
                        OUM6 = Oum6.getText().toString();
                        OUM7 = Oum7.getText().toString();
                        OUM8 = Oum8.getText().toString();
                        OUM9 = Oum9.getText().toString();
                        OUM10 = Oum10.getText().toString();

                        OUME1 = monitor1.getText().toString();//////////////////////
                        OUME2 = monitor2.getText().toString();
                        OUME3 = monitor3.getText().toString();
                        OUME4 = monitor4.getText().toString();
                        OUME5 = monitor5.getText().toString();
                        OUME6 = monitor6.getText().toString();
                        OUME7 = monitor7.getText().toString();
                        OUME8 = monitor8.getText().toString();
                        OUME9 = monitor9.getText().toString();
                        OUME10 = monitor10.getText().toString();

                        saveData();
                        Intent intent = new Intent(v.getContext(), area.class);
                        startActivityForResult(intent, 0);
                        presionado = false;
                        break;
                }
                return true;
            }
        });
        asignarqrbuttons();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        asigVariable();

        if (equipo.equals("monitor")) {
            equipo = "EQUIPOMONITOR";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("arcoenc")){
            equipo = "EQUIPOARCOENC";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("bombainfusion")){
            equipo ="EQUIPOBOMBAINFUSION";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("cunacalor")){
            equipo ="EQUIPOCUNACALOR";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("elecgrafo")){
            equipo ="EQUIPOELECGRAFO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("incu")){
            equipo ="EQUIPOINCU";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("lamp")){
            equipo ="EQUIPOLAMP";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("maqanestesia")){
            equipo ="EQUIPOMAQUINANESTECIA";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("microscopioq")){
            equipo ="EQUIPOMICROSCOPIO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("poligrafo")){
            equipo ="EQUIPOPOLIGRAFO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("torreendo")){
            equipo ="EQUIPOTORREENDO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("torrelapa")){
            equipo ="EQUIPOTORRELAPARO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("ultrasonido")){
            equipo ="EQUIPOULTRASONIDO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("ventilador")){
            equipo ="EQUIPOVENTILADOR";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("desfibrilador")){
            equipo ="EQUIPODESFIBRILADOR";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("electerio")){
            equipo ="EQUIPOELECTERIO";
            editor.putString(equipo,"LISTO");
        }

        if (equipo.equals("asisresp")){
            equipo = "EQUIPOASISRESP";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("autoclave")){
            equipo = "EQUIPOAUTOCLAVE";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("calentabiberon")){
            equipo = "EQUIPOCALENTABIBERON";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("desviasareas")){
            equipo = "EQUIPODESVIASAREAS";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("esteelectrico")){
            equipo = "EQUIPOESTEELECTRICO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("inyector")){
            equipo = "EQUIPOINYECTOR";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("lavadora")){
            equipo = "EQUIPOLAVADORA";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("lipo")){
            equipo = "EQUIPOLIPO";
            editor.putString(equipo,"LISTO");
        }
        if (equipo.equals("torniquete")){
            equipo = "EQUIPOTORNIQUETE";
            editor.putString(equipo,"LISTO");
        }
        editor.apply();
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        equipo = sharedPreferences.getString("EQUIPO","NA");
        String nequipo;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BORRAR","NO");
        editor.apply();

        if (equipo.equals("monitor")){
            TX1.setText("MONITOR SIGNOS VITALES ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("arcoenc")){
            TX1.setText("ARCO EN C ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("bombainfusion")){
            TX1.setText("BOMBA DE INFUSION ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("cunacalor")){
            TX1.setText("CUNA DE CALOR RADIANTE ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("elecgrafo")){
            TX1.setText("ELECTROCARDIOGRAFO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("incu")){
            TX1.setText("INCUBADORA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("lamp")){
            TX1.setText("LAMPARA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("maqanestesia")){
            TX1.setText("MAQUINA DE ANESTECIA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("microscopioq")){
            TX1.setText("MICROSCOPIO QUIRURGICO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("poligrafo")){
            TX1.setText("POLIGRAFO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("torreendo")){
            TX1.setText("TORRE DE ENDOSCOPIA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("torrelapa")){
            TX1.setText("TORRE DE LAPAROSCOPIA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("ultrasonido")){
            TX1.setText("ULTRASONIDO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("ventilador")){
            TX1.setText("VENTILADOR MECANICO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("desfibrilador")){
            TX1.setText("DESFIBILADOR ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("electerio")){
            TX1.setText("ELECTROCAUTERIO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("asisresp")){
            TX1.setText("ASISTENTE RESPIRATORIO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("autoclave")){
            TX1.setText("AUTOCLAVE ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("calentabiberon")){
            TX1.setText("CALENTADOR DE BIBERON ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("desviasareas")){
            TX1.setText("DESOBSTRURADOR VIAS AEREAS ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("esteelectrico")){
            TX1.setText("ESTERILIZADOR ELECTRICO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("inyector")){
            TX1.setText("INYECTOR DE CONTRASTE ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("lavadora")){
            TX1.setText("LAVADORA TERMODESINFECTADORA ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("lipo")){
            TX1.setText("MAQUINA LIPOSUCCION ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
        if (equipo.equals("torniquete")){
            TX1.setText("TORNIQUETE NEUMATICO ");
            nequipo=TX1.getText().toString();
            setText(nequipo);
        }
    }
    public void setText(String nequipo){
        monitor1.setText(nequipo+"1");monitor2.setText(nequipo+"2");monitor3.setText(nequipo+"3");
        monitor4.setText(nequipo+"4");monitor5.setText(nequipo+"5");monitor6.setText(nequipo+"6");
        monitor7.setText(nequipo+"7");monitor8.setText(nequipo+"8");monitor9.setText(nequipo+"9");
        monitor10.setText(nequipo+"10");
    }
    public void enableTrue(){
        monitor1.setEnabled(true);
        monitor2.setEnabled(true);
        monitor3.setEnabled(true);
        monitor4.setEnabled(true);
        monitor5.setEnabled(true);
        monitor6.setEnabled(true);
        monitor7.setEnabled(true);
        monitor8.setEnabled(true);
        monitor9.setEnabled(true);
        monitor10.setEnabled(true);
    }
    public void asigVariable() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sum1 == 1 || sum2 == 1 || sum3 == 1 || sum4 == 1 || sum5 == 1 || sum6 == 1 || sum7 == 1 || sum8 == 1 || sum9 == 1 || sum10 == 1) {
            editor.putInt(equipo + "E1", sum1);
            editor.putInt(equipo + "E2", sum2);
            editor.putInt(equipo + "E3", sum3);
            editor.putInt(equipo + "E4", sum4);
            editor.putInt(equipo + "E5", sum5);
            editor.putInt(equipo + "E6", sum6);
            editor.putInt(equipo + "E7", sum7);
            editor.putInt(equipo + "E8", sum8);
            editor.putInt(equipo + "E9", sum9);
            editor.putInt(equipo + "E10", sum10);

            if (sum1 == 1) {
                editor.putString(equipo + "1", OUM1);
                editor.putString(equipo + "EE1", OUME1);/////////////////////////
                editor.putInt(equipo + "11", rumx11);
                editor.putInt(equipo + "12", rumx12);
                editor.putInt(equipo + "13", rumx13);
                editor.putInt(equipo + "14", rumx14);
                editor.putInt(equipo + "15", rumx15);
            }
            if (sum2 == 1) {
                editor.putString(equipo + "2", OUM2);
                editor.putString(equipo + "EE2", OUME2);
                editor.putInt(equipo + "21", rumx21);
                editor.putInt(equipo + "22", rumx22);
                editor.putInt(equipo + "23", rumx23);
                editor.putInt(equipo + "24", rumx24);
                editor.putInt(equipo + "25", rumx25);
            }
            if (sum3 == 1) {
                editor.putString(equipo + "3", OUM3);
                editor.putString(equipo + "EE3", OUME3);
                editor.putInt(equipo + "31", rumx31);
                editor.putInt(equipo + "32", rumx32);
                editor.putInt(equipo + "33", rumx33);
                editor.putInt(equipo + "34", rumx34);
                editor.putInt(equipo + "35", rumx35);
            }
            if (sum4 == 1) {
                editor.putString(equipo + "4", OUM4);
                editor.putString(equipo + "EE4", OUME4);
                editor.putInt(equipo + "41", rumx41);
                editor.putInt(equipo + "42", rumx42);
                editor.putInt(equipo + "43", rumx43);
                editor.putInt(equipo + "44", rumx44);
                editor.putInt(equipo + "45", rumx45);
            }
            if (sum5 == 1) {
                editor.putString(equipo + "5", OUM5);
                editor.putString(equipo + "EE5", OUME5);
                editor.putInt(equipo + "51", rumx51);
                editor.putInt(equipo + "52", rumx52);
                editor.putInt(equipo + "53", rumx53);
                editor.putInt(equipo + "54", rumx54);
                editor.putInt(equipo + "55", rumx55);
            }
            if (sum6 == 1) {
                editor.putString(equipo + "6", OUM6);
                editor.putString(equipo + "EE6", OUME6);
                editor.putInt(equipo + "61", rumx61);
                editor.putInt(equipo + "62", rumx62);
                editor.putInt(equipo + "63", rumx63);
                editor.putInt(equipo + "64", rumx64);
                editor.putInt(equipo + "65", rumx65);
            }
            if (sum7 == 1) {
                editor.putString(equipo + "7", OUM7);
                editor.putString(equipo + "EE7", OUME7);
                editor.putInt(equipo + "71", rumx71);
                editor.putInt(equipo + "72", rumx72);
                editor.putInt(equipo + "73", rumx73);
                editor.putInt(equipo + "74", rumx74);
                editor.putInt(equipo + "75", rumx75);
            }
            if (sum8 == 1) {
                editor.putString(equipo + "8", OUM8);
                editor.putString(equipo + "EE8", OUME8);
                editor.putInt(equipo + "81", rumx81);
                editor.putInt(equipo + "82", rumx82);
                editor.putInt(equipo + "83", rumx83);
                editor.putInt(equipo + "84", rumx84);
                editor.putInt(equipo + "85", rumx85);
            }
            if (sum9 == 1) {
                editor.putString(equipo + "9", OUM9);
                editor.putString(equipo + "EE9", OUME9);
                editor.putInt(equipo + "91", rumx91);
                editor.putInt(equipo + "92", rumx92);
                editor.putInt(equipo + "93", rumx93);
                editor.putInt(equipo + "94", rumx94);
                editor.putInt(equipo + "95", rumx95);
            }
            if (sum10 == 1) {
                editor.putString(equipo + "10", OUM10);
                editor.putString(equipo + "EE10", OUME10);
                editor.putInt(equipo + "101", rumx101);
                editor.putInt(equipo + "102", rumx102);
                editor.putInt(equipo + "103", rumx103);
                editor.putInt(equipo + "104", rumx104);
                editor.putInt(equipo + "105", rumx105);
            }
        }
        editor.apply();
    }
    public void creacionOncreate(){
        monitor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum1 = 1;
                    if (monitor1.isChecked()) {
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm1.setVisibility(View.VISIBLE);
                        monitor1.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum11 = (RadioButton) findViewById(R.id.rum11);
                        final RadioButton rum12 = (RadioButton) findViewById(R.id.rum12);
                        final RadioButton rum13 = (RadioButton) findViewById(R.id.rum13);
                        final RadioButton rum14 = (RadioButton) findViewById(R.id.rum14);
                        final RadioButton rum15 = (RadioButton) findViewById(R.id.rum15);
                        final RadioButton rum16 = (RadioButton) findViewById(R.id.rum16);
                        final RadioButton rum17 = (RadioButton) findViewById(R.id.rum17);
                        final RadioButton rum18 = (RadioButton) findViewById(R.id.rum18);
                        final RadioButton rum19 = (RadioButton) findViewById(R.id.rum19);
                        final RadioButton rum110 = (RadioButton) findViewById(R.id.rum110);

                        rum11.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum11.isChecked()) {
                                    rumx11 = 0;
                                    rum12.setChecked(false);
                                }
                            }
                        });
                        rum12.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum12.isChecked()) {
                                    rum11.setChecked(false);
                                    rumx11 = 1;
                                }
                            }
                        });

                        rum13.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum13.isChecked()) {
                                    rum14.setChecked(false);
                                    rumx12 = 0;
                                }
                            }
                        });
                        rum14.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum14.isChecked()) {
                                    rum13.setChecked(false);
                                    rumx12 = 1;
                                }
                            }
                        });
                        rum15.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum15.isChecked()) {
                                    rum16.setChecked(false);
                                    rumx13 = 0;
                                }
                            }
                        });
                        rum16.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum16.isChecked()) {
                                    rum15.setChecked(false);
                                    rumx13 = 1;
                                }
                            }
                        });
                        rum17.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum17.isChecked()) {
                                    rum18.setChecked(false);
                                    rumx14 = 0;
                                }
                            }
                        });
                        rum18.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum18.isChecked()) {
                                    rum17.setChecked(false);
                                    rumx14 = 1;
                                }
                            }
                        });
                        rum19.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum19.isChecked()) {
                                    rum110.setChecked(false);
                                    rumx15 = 0;
                                }
                            }
                        });
                        rum110.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum110.isChecked()) {
                                    rum19.setChecked(false);
                                    rumx15 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm1.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum2 = 1;
                    if (monitor2.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm2.setVisibility(View.VISIBLE);
                        monitor2.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum21 = (RadioButton) findViewById(R.id.rum21);
                        final RadioButton rum22 = (RadioButton) findViewById(R.id.rum22);
                        final RadioButton rum23 = (RadioButton) findViewById(R.id.rum23);
                        final RadioButton rum24 = (RadioButton) findViewById(R.id.rum24);
                        final RadioButton rum25 = (RadioButton) findViewById(R.id.rum25);
                        final RadioButton rum26 = (RadioButton) findViewById(R.id.rum26);
                        final RadioButton rum27 = (RadioButton) findViewById(R.id.rum27);
                        final RadioButton rum28 = (RadioButton) findViewById(R.id.rum28);
                        final RadioButton rum29 = (RadioButton) findViewById(R.id.rum29);
                        final RadioButton rum210 = (RadioButton) findViewById(R.id.rum210);

                        rum21.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum21.isChecked()) {
                                    rumx21 = 0;
                                    rum22.setChecked(false);
                                }
                            }
                        });
                        rum22.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum22.isChecked()) {
                                    rum21.setChecked(false);
                                    rumx21 = 1;
                                }
                            }
                        });

                        rum23.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum23.isChecked()) {
                                    rum24.setChecked(false);
                                    rumx22 = 0;
                                }
                            }
                        });
                        rum24.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum24.isChecked()) {
                                    rum23.setChecked(false);
                                    rumx22 = 1;
                                }
                            }
                        });
                        rum25.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum25.isChecked()) {
                                    rum26.setChecked(false);
                                    rumx23 = 0;
                                }
                            }
                        });
                        rum26.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum26.isChecked()) {
                                    rum25.setChecked(false);
                                    rumx23 = 1;
                                }
                            }
                        });
                        rum27.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum27.isChecked()) {
                                    rum28.setChecked(false);
                                    rumx24 = 0;
                                }
                            }
                        });
                        rum28.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum28.isChecked()) {
                                    rum27.setChecked(false);
                                    rumx24 = 1;
                                }
                            }
                        });
                        rum29.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum29.isChecked()) {
                                    rum210.setChecked(false);
                                    rumx25 = 0;
                                }
                            }
                        });
                        rum210.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum210.isChecked()) {
                                    rum29.setChecked(false);
                                    rumx25 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm2.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum3 = 1;
                    if (monitor3.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm3.setVisibility(View.VISIBLE);
                        monitor3.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum31 = (RadioButton) findViewById(R.id.rum31);
                        final RadioButton rum32 = (RadioButton) findViewById(R.id.rum32);
                        final RadioButton rum33 = (RadioButton) findViewById(R.id.rum33);
                        final RadioButton rum34 = (RadioButton) findViewById(R.id.rum34);
                        final RadioButton rum35 = (RadioButton) findViewById(R.id.rum35);
                        final RadioButton rum36 = (RadioButton) findViewById(R.id.rum36);
                        final RadioButton rum37 = (RadioButton) findViewById(R.id.rum37);
                        final RadioButton rum38 = (RadioButton) findViewById(R.id.rum38);
                        final RadioButton rum39 = (RadioButton) findViewById(R.id.rum39);
                        final RadioButton rum310 = (RadioButton) findViewById(R.id.rum310);

                        rum31.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum31.isChecked()) {
                                    rumx31 = 0;
                                    rum32.setChecked(false);
                                }
                            }
                        });
                        rum32.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum32.isChecked()) {
                                    rum31.setChecked(false);
                                    rumx31 = 1;
                                }
                            }
                        });

                        rum33.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum33.isChecked()) {
                                    rum34.setChecked(false);
                                    rumx32 = 0;
                                }
                            }
                        });
                        rum34.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum34.isChecked()) {
                                    rum33.setChecked(false);
                                    rumx32 = 1;
                                }
                            }
                        });
                        rum35.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum35.isChecked()) {
                                    rum36.setChecked(false);
                                    rumx33 = 0;
                                }
                            }
                        });
                        rum36.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum36.isChecked()) {
                                    rum35.setChecked(false);
                                    rumx33 = 1;
                                }
                            }
                        });
                        rum37.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum37.isChecked()) {
                                    rum38.setChecked(false);
                                    rumx34 = 0;
                                }
                            }
                        });
                        rum38.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum38.isChecked()) {
                                    rum37.setChecked(false);
                                    rumx34 = 1;
                                }
                            }
                        });
                        rum39.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum39.isChecked()) {
                                    rum310.setChecked(false);
                                    rumx35 = 0;
                                }
                            }
                        });
                        rum310.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum310.isChecked()) {
                                    rum39.setChecked(false);
                                    rumx35 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm3.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum4 = 1;
                    if (monitor4.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm4.setVisibility(View.VISIBLE);
                        monitor4.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum41 = (RadioButton) findViewById(R.id.rum41);
                        final RadioButton rum42 = (RadioButton) findViewById(R.id.rum42);
                        final RadioButton rum43 = (RadioButton) findViewById(R.id.rum43);
                        final RadioButton rum44 = (RadioButton) findViewById(R.id.rum44);
                        final RadioButton rum45 = (RadioButton) findViewById(R.id.rum45);
                        final RadioButton rum46 = (RadioButton) findViewById(R.id.rum46);
                        final RadioButton rum47 = (RadioButton) findViewById(R.id.rum47);
                        final RadioButton rum48 = (RadioButton) findViewById(R.id.rum48);
                        final RadioButton rum49 = (RadioButton) findViewById(R.id.rum49);
                        final RadioButton rum410 = (RadioButton) findViewById(R.id.rum410);

                        rum41.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum41.isChecked()) {
                                    rumx41 = 0;
                                    rum42.setChecked(false);
                                }
                            }
                        });
                        rum42.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum42.isChecked()) {
                                    rum41.setChecked(false);
                                    rumx41 = 1;
                                }
                            }
                        });

                        rum43.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum43.isChecked()) {
                                    rum44.setChecked(false);
                                    rumx42 = 0;
                                }
                            }
                        });
                        rum44.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum44.isChecked()) {
                                    rum43.setChecked(false);
                                    rumx42 = 1;
                                }
                            }
                        });
                        rum45.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum45.isChecked()) {
                                    rum46.setChecked(false);
                                    rumx43 = 0;
                                }
                            }
                        });
                        rum46.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum46.isChecked()) {
                                    rum45.setChecked(false);
                                    rumx43 = 1;
                                }
                            }
                        });
                        rum47.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum47.isChecked()) {
                                    rum48.setChecked(false);
                                    rumx44 = 0;
                                }
                            }
                        });
                        rum48.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum48.isChecked()) {
                                    rum47.setChecked(false);
                                    rumx44 = 1;
                                }
                            }
                        });
                        rum49.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum49.isChecked()) {
                                    rum410.setChecked(false);
                                    rumx45 = 0;
                                }
                            }
                        });
                        rum410.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum410.isChecked()) {
                                    rum49.setChecked(false);
                                    rumx45 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm4.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum5 = 1;
                    if (monitor5.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm5.setVisibility(View.VISIBLE);
                        monitor5.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum51 = (RadioButton) findViewById(R.id.rum51);
                        final RadioButton rum52 = (RadioButton) findViewById(R.id.rum52);
                        final RadioButton rum53 = (RadioButton) findViewById(R.id.rum53);
                        final RadioButton rum54 = (RadioButton) findViewById(R.id.rum54);
                        final RadioButton rum55 = (RadioButton) findViewById(R.id.rum55);
                        final RadioButton rum56 = (RadioButton) findViewById(R.id.rum56);
                        final RadioButton rum57 = (RadioButton) findViewById(R.id.rum57);
                        final RadioButton rum58 = (RadioButton) findViewById(R.id.rum58);
                        final RadioButton rum59 = (RadioButton) findViewById(R.id.rum59);
                        final RadioButton rum510 = (RadioButton) findViewById(R.id.rum510);

                        rum51.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum51.isChecked()) {
                                    rumx51 = 0;
                                    rum52.setChecked(false);
                                }
                            }
                        });
                        rum52.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum52.isChecked()) {
                                    rum51.setChecked(false);
                                    rumx51 = 1;
                                }
                            }
                        });

                        rum53.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum53.isChecked()) {
                                    rum54.setChecked(false);
                                    rumx52 = 0;
                                }
                            }
                        });
                        rum54.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum54.isChecked()) {
                                    rum53.setChecked(false);
                                    rumx52 = 1;
                                }
                            }
                        });
                        rum55.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum55.isChecked()) {
                                    rum56.setChecked(false);
                                    rumx53 = 0;
                                }
                            }
                        });
                        rum56.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum56.isChecked()) {
                                    rum55.setChecked(false);
                                    rumx53 = 1;
                                }
                            }
                        });
                        rum57.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum57.isChecked()) {
                                    rum58.setChecked(false);
                                    rumx54 = 0;
                                }
                            }
                        });
                        rum58.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum58.isChecked()) {
                                    rum57.setChecked(false);
                                    rumx54 = 1;
                                }
                            }
                        });
                        rum59.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum59.isChecked()) {
                                    rum510.setChecked(false);
                                    rumx55 = 0;
                                }
                            }
                        });
                        rum510.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum510.isChecked()) {
                                    rum59.setChecked(false);
                                    rumx55 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm5.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum6 = 1;
                    if (monitor6.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm6.setVisibility(View.VISIBLE);
                        monitor6.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum61 = (RadioButton) findViewById(R.id.rum61);
                        final RadioButton rum62 = (RadioButton) findViewById(R.id.rum62);
                        final RadioButton rum63 = (RadioButton) findViewById(R.id.rum63);
                        final RadioButton rum64 = (RadioButton) findViewById(R.id.rum64);
                        final RadioButton rum65 = (RadioButton) findViewById(R.id.rum65);
                        final RadioButton rum66 = (RadioButton) findViewById(R.id.rum66);
                        final RadioButton rum67 = (RadioButton) findViewById(R.id.rum67);
                        final RadioButton rum68 = (RadioButton) findViewById(R.id.rum68);
                        final RadioButton rum69 = (RadioButton) findViewById(R.id.rum69);
                        final RadioButton rum610 = (RadioButton) findViewById(R.id.rum610);

                        rum61.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum61.isChecked()) {
                                    rumx61 = 0;
                                    rum62.setChecked(false);
                                }
                            }
                        });
                        rum62.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum62.isChecked()) {
                                    rum61.setChecked(false);
                                    rumx61 = 1;
                                }
                            }
                        });

                        rum63.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum63.isChecked()) {
                                    rum64.setChecked(false);
                                    rumx62 = 0;
                                }
                            }
                        });
                        rum64.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum64.isChecked()) {
                                    rum63.setChecked(false);
                                    rumx62 = 1;
                                }
                            }
                        });
                        rum65.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum65.isChecked()) {
                                    rum66.setChecked(false);
                                    rumx63 = 0;
                                }
                            }
                        });
                        rum66.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum66.isChecked()) {
                                    rum65.setChecked(false);
                                    rumx63 = 1;
                                }
                            }
                        });
                        rum67.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum67.isChecked()) {
                                    rum68.setChecked(false);
                                    rumx64 = 0;
                                }
                            }
                        });
                        rum68.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum68.isChecked()) {
                                    rum67.setChecked(false);
                                    rumx64 = 1;
                                }
                            }
                        });
                        rum69.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum69.isChecked()) {
                                    rum610.setChecked(false);
                                    rumx65 = 0;
                                }
                            }
                        });
                        rum610.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum610.isChecked()) {
                                    rum69.setChecked(false);
                                    rumx65 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm6.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum7 = 1;
                    if (monitor7.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm7.setVisibility(View.VISIBLE);
                        monitor7.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum71 = (RadioButton) findViewById(R.id.rum71);
                        final RadioButton rum72 = (RadioButton) findViewById(R.id.rum72);
                        final RadioButton rum73 = (RadioButton) findViewById(R.id.rum73);
                        final RadioButton rum74 = (RadioButton) findViewById(R.id.rum74);
                        final RadioButton rum75 = (RadioButton) findViewById(R.id.rum75);
                        final RadioButton rum76 = (RadioButton) findViewById(R.id.rum76);
                        final RadioButton rum77 = (RadioButton) findViewById(R.id.rum77);
                        final RadioButton rum78 = (RadioButton) findViewById(R.id.rum78);
                        final RadioButton rum79 = (RadioButton) findViewById(R.id.rum79);
                        final RadioButton rum710 = (RadioButton) findViewById(R.id.rum710);

                        rum71.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum71.isChecked()) {
                                    rumx71 = 0;
                                    rum72.setChecked(false);
                                }
                            }
                        });
                        rum72.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum72.isChecked()) {
                                    rum71.setChecked(false);
                                    rumx71 = 1;
                                }
                            }
                        });

                        rum73.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum73.isChecked()) {
                                    rum74.setChecked(false);
                                    rumx72 = 0;
                                }
                            }
                        });
                        rum74.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum74.isChecked()) {
                                    rum73.setChecked(false);
                                    rumx72 = 1;
                                }
                            }
                        });
                        rum75.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum75.isChecked()) {
                                    rum76.setChecked(false);
                                    rumx73 = 0;
                                }
                            }
                        });
                        rum76.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum76.isChecked()) {
                                    rum75.setChecked(false);
                                    rumx73 = 1;
                                }
                            }
                        });
                        rum77.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum77.isChecked()) {
                                    rum78.setChecked(false);
                                    rumx74 = 0;
                                }
                            }
                        });
                        rum78.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum78.isChecked()) {
                                    rum77.setChecked(false);
                                    rumx74 = 1;
                                }
                            }
                        });
                        rum79.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum79.isChecked()) {
                                    rum710.setChecked(false);
                                    rumx75 = 0;
                                }
                            }
                        });
                        rum710.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum710.isChecked()) {
                                    rum79.setChecked(false);
                                    rumx75 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm7.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum8 = 1;
                    if (monitor8.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm8.setVisibility(View.VISIBLE);
                        monitor8.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum81 = (RadioButton) findViewById(R.id.rum81);
                        final RadioButton rum82 = (RadioButton) findViewById(R.id.rum82);
                        final RadioButton rum83 = (RadioButton) findViewById(R.id.rum83);
                        final RadioButton rum84 = (RadioButton) findViewById(R.id.rum84);
                        final RadioButton rum85 = (RadioButton) findViewById(R.id.rum85);
                        final RadioButton rum86 = (RadioButton) findViewById(R.id.rum86);
                        final RadioButton rum87 = (RadioButton) findViewById(R.id.rum87);
                        final RadioButton rum88 = (RadioButton) findViewById(R.id.rum88);
                        final RadioButton rum89 = (RadioButton) findViewById(R.id.rum89);
                        final RadioButton rum810 = (RadioButton) findViewById(R.id.rum810);

                        rum81.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum81.isChecked()) {
                                    rumx81 = 0;
                                    rum82.setChecked(false);
                                }
                            }
                        });
                        rum82.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum82.isChecked()) {
                                    rum81.setChecked(false);
                                    rumx81 = 1;
                                }
                            }
                        });

                        rum83.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum83.isChecked()) {
                                    rum84.setChecked(false);
                                    rumx82 = 0;
                                }
                            }
                        });
                        rum84.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum84.isChecked()) {
                                    rum83.setChecked(false);
                                    rumx82 = 1;
                                }
                            }
                        });
                        rum85.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum85.isChecked()) {
                                    rum86.setChecked(false);
                                    rumx83 = 0;
                                }
                            }
                        });
                        rum86.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum86.isChecked()) {
                                    rum85.setChecked(false);
                                    rumx83 = 1;
                                }
                            }
                        });
                        rum87.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum87.isChecked()) {
                                    rum88.setChecked(false);
                                    rumx84 = 0;
                                }
                            }
                        });
                        rum88.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum88.isChecked()) {
                                    rum87.setChecked(false);
                                    rumx84 = 1;
                                }
                            }
                        });
                        rum89.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum89.isChecked()) {
                                    rum810.setChecked(false);
                                    rumx85 = 0;
                                }
                            }
                        });
                        rum810.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum810.isChecked()) {
                                    rum89.setChecked(false);
                                    rumx85 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm8.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum9 = 1;
                    if (monitor9.isChecked()) {
                        monitor1.setEnabled(false);
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor10.setEnabled(false);
                        linearLayoutm1.setVisibility(View.GONE);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm10.setVisibility(View.GONE);

                        linearLayoutm9.setVisibility(View.VISIBLE);
                        monitor9.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum91 = (RadioButton) findViewById(R.id.rum91);
                        final RadioButton rum92 = (RadioButton) findViewById(R.id.rum92);
                        final RadioButton rum93 = (RadioButton) findViewById(R.id.rum93);
                        final RadioButton rum94 = (RadioButton) findViewById(R.id.rum94);
                        final RadioButton rum95 = (RadioButton) findViewById(R.id.rum95);
                        final RadioButton rum96 = (RadioButton) findViewById(R.id.rum96);
                        final RadioButton rum97 = (RadioButton) findViewById(R.id.rum97);
                        final RadioButton rum98 = (RadioButton) findViewById(R.id.rum98);
                        final RadioButton rum99 = (RadioButton) findViewById(R.id.rum99);
                        final RadioButton rum910 = (RadioButton) findViewById(R.id.rum910);

                        rum91.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum91.isChecked()) {
                                    rumx91 = 0;
                                    rum92.setChecked(false);
                                }
                            }
                        });
                        rum92.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum92.isChecked()) {
                                    rum91.setChecked(false);
                                    rumx91 = 1;
                                }
                            }
                        });

                        rum93.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum93.isChecked()) {
                                    rum94.setChecked(false);
                                    rumx92 = 0;
                                }
                            }
                        });
                        rum94.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum94.isChecked()) {
                                    rum93.setChecked(false);
                                    rumx92 = 1;
                                }
                            }
                        });
                        rum95.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum95.isChecked()) {
                                    rum96.setChecked(false);
                                    rumx93 = 0;
                                }
                            }
                        });
                        rum96.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum96.isChecked()) {
                                    rum95.setChecked(false);
                                    rumx93 = 1;
                                }
                            }
                        });
                        rum97.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum97.isChecked()) {
                                    rum98.setChecked(false);
                                    rumx94 = 0;
                                }
                            }
                        });
                        rum98.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum98.isChecked()) {
                                    rum97.setChecked(false);
                                    rumx94 = 1;
                                }
                            }
                        });
                        rum99.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum99.isChecked()) {
                                    rum910.setChecked(false);
                                    rumx95 = 0;
                                }
                            }
                        });
                        rum910.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum910.isChecked()) {
                                    rum99.setChecked(false);
                                    rumx95 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm9.setVisibility(View.GONE);
                    }
                }
            }
        });
        monitor10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    sum10 = 1;
                    if (monitor10.isChecked()) {
                        monitor2.setEnabled(false);
                        monitor3.setEnabled(false);
                        monitor4.setEnabled(false);
                        monitor5.setEnabled(false);
                        monitor6.setEnabled(false);
                        monitor7.setEnabled(false);
                        monitor8.setEnabled(false);
                        monitor9.setEnabled(false);
                        monitor1.setEnabled(false);
                        linearLayoutm2.setVisibility(View.GONE);
                        linearLayoutm3.setVisibility(View.GONE);
                        linearLayoutm4.setVisibility(View.GONE);
                        linearLayoutm5.setVisibility(View.GONE);
                        linearLayoutm6.setVisibility(View.GONE);
                        linearLayoutm7.setVisibility(View.GONE);
                        linearLayoutm8.setVisibility(View.GONE);
                        linearLayoutm9.setVisibility(View.GONE);
                        linearLayoutm1.setVisibility(View.GONE);

                        linearLayoutm10.setVisibility(View.VISIBLE);
                        monitor10.setTextColor(Color.parseColor(colorcheck));

                        final RadioButton rum101 = (RadioButton) findViewById(R.id.rum101);
                        final RadioButton rum102 = (RadioButton) findViewById(R.id.rum102);
                        final RadioButton rum103 = (RadioButton) findViewById(R.id.rum103);
                        final RadioButton rum104 = (RadioButton) findViewById(R.id.rum104);
                        final RadioButton rum105 = (RadioButton) findViewById(R.id.rum105);
                        final RadioButton rum106 = (RadioButton) findViewById(R.id.rum106);
                        final RadioButton rum107 = (RadioButton) findViewById(R.id.rum107);
                        final RadioButton rum108 = (RadioButton) findViewById(R.id.rum108);
                        final RadioButton rum109 = (RadioButton) findViewById(R.id.rum109);
                        final RadioButton rum1010 = (RadioButton) findViewById(R.id.rum1010);

                        rum101.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum101.isChecked()) {
                                    rumx101 = 0;
                                    rum102.setChecked(false);
                                }
                            }
                        });
                        rum102.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum102.isChecked()) {
                                    rum101.setChecked(false);
                                    rumx101 = 1;
                                }
                            }
                        });

                        rum103.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum103.isChecked()) {
                                    rum104.setChecked(false);
                                    rumx102 = 0;
                                }
                            }
                        });
                        rum104.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum104.isChecked()) {
                                    rum103.setChecked(false);
                                    rumx102 = 1;
                                }
                            }
                        });
                        rum105.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum105.isChecked()) {
                                    rum106.setChecked(false);
                                    rumx103 = 0;
                                }
                            }
                        });
                        rum106.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum106.isChecked()) {
                                    rum105.setChecked(false);
                                    rumx103 = 1;
                                }
                            }
                        });
                        rum107.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum107.isChecked()) {
                                    rum108.setChecked(false);
                                    rumx104 = 0;
                                }
                            }
                        });
                        rum108.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum108.isChecked()) {
                                    rum107.setChecked(false);
                                    rumx104 = 1;
                                }
                            }
                        });
                        rum109.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum109.isChecked()) {
                                    rum1010.setChecked(false);
                                    rumx105 = 0;
                                }
                            }
                        });
                        rum1010.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (rum1010.isChecked()) {
                                    rum109.setChecked(false);
                                    rumx105 = 1;
                                }
                            }
                        });
                    } else {
                        enableTrue();
                        linearLayoutm10.setVisibility(View.GONE);
                    }
                }
            }
        });

    }
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), area.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }


    public void asignarqrbuttons(){

        qr1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr1.setScaleX((float) 1.2);qr1.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr1.setScaleX((float) 1);qr1.setScaleY((float) 1);
                        scanear("qr1");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr2.setScaleX((float) 1.2);qr2.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr2.setScaleX((float) 1);qr2.setScaleY((float) 1);
                        scanear("qr2");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr3.setScaleX((float) 1.2);qr3.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr3.setScaleX((float) 1);qr3.setScaleY((float) 1);
                        scanear("qr3");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr4.setScaleX((float) 1.2);qr4.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr4.setScaleX((float) 1);qr4.setScaleY((float) 1);
                        scanear("qr4");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr5.setScaleX((float) 1.2);qr5.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr5.setScaleX((float) 1);qr5.setScaleY((float) 1);
                        scanear("qr5");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr6.setScaleX((float) 1.2);qr6.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr6.setScaleX((float) 1);qr6.setScaleY((float) 1);
                        scanear("qr6");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr7.setScaleX((float) 1.2);qr7.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr7.setScaleX((float) 1);qr1.setScaleY((float) 1);
                        scanear("qr7");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr8.setScaleX((float) 1.2);qr8.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr8.setScaleX((float) 1);qr8.setScaleY((float) 1);
                        scanear("qr8");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr9.setScaleX((float) 1.2);qr9.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr9.setScaleX((float) 1);qr9.setScaleY((float) 1);
                        scanear("qr9");
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        qr10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            qr10.setScaleX((float) 1.2);qr10.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        qr10.setScaleX((float) 1);qr10.setScaleY((float) 1);
                        scanear("qr10");
                        presionado = false;
                        break;
                }
                return true;
            }
        });


    }
    private void scanear(String qr) {
        qrid=qr;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(scaner.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("SCANEANDO CODIGO");
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){

                checarqrs(String.valueOf(result.getContents()));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("RESULTADO DEL SCANER");
                builder.setPositiveButton("SCANEAR DE NUEVO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanear(qrid);
                    }
                }).setNegativeButton("FINALIZAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }else{
                Toast.makeText(this, "NO SE PUDO LEER CODIGO",Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
    public void checarqrs(String r){
        if(qrid.equals("qr1")){
            monitor1.setText(r);
        }
        if(qrid.equals("qr2")){
            monitor2.setText(r);
        }
        if(qrid.equals("qr3")){
            monitor3.setText(r);
        }
        if(qrid.equals("qr4")){
            monitor4.setText(r);
        }
        if(qrid.equals("qr5")){
            monitor5.setText(r);
        }
        if(qrid.equals("qr6")){
            monitor6.setText(r);
        }
        if(qrid.equals("qr7")){
            monitor7.setText(r);
        }
        if(qrid.equals("qr8")){
            monitor8.setText(r);
        }
        if(qrid.equals("qr9")){
            monitor9.setText(r);
        }
        if(qrid.equals("qr10")){
            monitor10.setText(r);
        }
    }
}
