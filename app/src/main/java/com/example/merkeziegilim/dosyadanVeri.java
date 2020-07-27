package com.example.merkeziegilim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class dosyadanVeri extends AppCompatActivity {

    Button verioku,verigetir;
    EditText sutunnumarası;
    ListView listView;
    String veriyolu,data;
    List<String> resultList = new ArrayList<String>();
    int sutun=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosyadan_veri);
        sutunnumarası=(EditText) findViewById(R.id.sutunnumara);

        veriyolu=bundle.getString("yol");
        verioku=(Button)findViewById(R.id.dosyadanhesapla);
        verioku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sutun==0){
                    sutunnumarası.setError("Lütfen Boş Bırakmayınız...");
                }else{
                Intent intent=new Intent(dosyadanVeri.this,MainActivity.class);
                intent.putExtra("yol",veriyolu);
                intent.putExtra("sutun",sutun);
                startActivity(intent);
            }}
        });
        listView=(ListView)findViewById(R.id.tumveriler);
        verigetir=(Button)findViewById(R.id.dosyadangetir);
        verigetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultList.clear();
                sutun = Integer.parseInt(sutunnumarası.getText().toString());
                sutunnumarası.getText().clear();
                loadData(veriyolu);
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(dosyadanVeri.this,R.layout.listtumveriler,R.id.a,resultList);
                listView.setAdapter(arrayAdapter);
            }
        });





    }

    private void loadData(String input) {
        File file = new File(input);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                resultList.add(row[sutun+1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
