package com.example.merkeziegilim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class BaslangicActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    Button ellegir,dosyadangir;
    String yol="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baslangic);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        ellegir=(Button)findViewById(R.id.ellegir);
        ellegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BaslangicActivity.this,MainActivity.class);
                yol="";
                intent.putExtra("yol",yol);
                startActivity(intent);
            }
        });

        dosyadangir=(Button)findViewById(R.id.dosyadangir);
        dosyadangir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_REQUEST_STORAGE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"izin verildi",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"İzin Verilmedi!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void performFileSearch(){
        Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();
                yol=path;
                Intent intent=new Intent(BaslangicActivity.this,dosyadanVeri.class);
                intent.putExtra("yol",yol);
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
