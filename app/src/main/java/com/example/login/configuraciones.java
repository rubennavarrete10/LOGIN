package com.example.login;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class configuraciones extends AppCompatActivity {

    String SHARED_PREFS = "sharedPrefsL";
    private boolean presionado;
    String EMPRESAS;
    ImageView imagen;
    Uri path;
    EditText EMPRESA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        File path1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        File CARPETA = new File(path1.getAbsolutePath(), "VERIFICACIONES EQUIPO MEDICO");
        if (!CARPETA.exists()) {
            CARPETA.mkdirs();
        }

        EMPRESA = (EditText) findViewById(R.id.editText4);
        final ImageButton IMGGALERIA = (ImageButton) findViewById(R.id.button61);
        final ImageButton SAVE = (ImageButton) findViewById(R.id.button52);
        imagen = (ImageView) findViewById(R.id.imageView2);

        IMGGALERIA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            IMGGALERIA.setScaleX((float) 1.2);
                            IMGGALERIA.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        IMGGALERIA.setScaleX((float) 1);
                        IMGGALERIA.setScaleY((float) 1);
                        cargarImagen();
                        presionado = false;
                        break;
                }
                return true;
            }
        });

        SAVE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!presionado) {
                            presionado = true;
                            SAVE.setScaleX((float) 1.2);
                            SAVE.setScaleY((float) 1.2);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        SAVE.setScaleX((float) 1);
                        SAVE.setScaleY((float) 1);
                        EMPRESAS = EMPRESA.getText().toString().toUpperCase();
                        try {
                            savedata();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        presionado = false;
                        //Toast.makeText(getApplicationContext(), "CAMBIOS GUARDADOS", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        loadData();
        loadImage();
    }

    public void loadData() {
        SharedPreferences sharedPreferencesL = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String HOSPITAL = sharedPreferencesL.getString("EMPRESA", "SINHOSPITAL");
        if (!HOSPITAL.equals("SINHOSPITAL")) {
            EMPRESA.setText(HOSPITAL);
        }
    }

    public void loadImage() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VERIFICACIONES EQUIPO MEDICO/logo.jpg");
        Uri myUri = (Uri.parse(String.valueOf(file)));
        if(file.exists()) {
            imagen.setImageURI(myUri);
        }
    }

    public void savedata() throws IOException {
        SharedPreferences sharedPreferencesL = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesL.edit();
        editor.putString("EMPRESA", EMPRESAS);
        editor.apply();

                try {
                    View view = findViewById(R.id.imageView2);
                    View content = view;
                    view.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
                    view.setDrawingCacheEnabled(false);

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VERIFICACIONES EQUIPO MEDICO/logo.jpg");
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.flush();
                    ostream.close();
                    Toast.makeText(getApplicationContext(), "CONFUGURACION GUARDADA", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "ERROR"+e, Toast.LENGTH_SHORT).show();
                }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
    }
    public void cargarImagen(){
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            path = data.getData();
            String selectedPath = path.getPath();

            if (selectedPath != null) {
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(path);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                imagen.setImageBitmap(bmp);
            }
        }
    }
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, 0);
        finishAndRemoveTask();
    }
}
