package com.example.merkeziegilim;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    //dosya oku
    ListView listView;
    Button veriget,verigir,verihesapla;
    EditText verigirme;
    TextView deneme;
    String path;
    int sutunnumara;
    public ArrayList<veriler> verims=new ArrayList<veriler>();
    ArrayList<Double> veriliste=new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }
        sutunnumara=bundle.getInt("sutun");
        path=bundle.getString("yol");
        if (path.trim().equals("")){
            verilistelefunction();
        }
        else {
            loadData(path);
            verilistelefunction();
        }
        deneme=(TextView)findViewById(R.id.deneme);
        verigirme=(EditText)findViewById(R.id.verigirme);
        verigir=(Button)findViewById(R.id.verigir);
        verigir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verigirme.getText().toString().trim().equals("")){
                    verigirme.setError("Lütfen Boş Bırakmayınız...");
                }
                else {
                    verigirfunction();
                    verilistelefunction();
                }

            }
        });

        veriget=(Button)findViewById(R.id.verigetir);
        veriget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verilistelefunction();
                //performFileSearch();
            }
        });

        verihesapla=(Button)findViewById(R.id.verihesapla);
        verihesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verims.size()>1){
                    verihesaplafunction();
                }else {
                    Toast.makeText(getApplicationContext(),"Lütfen Veri Giriniz.",Toast.LENGTH_LONG).show();
                }

            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==PERMISSION_REQUEST_STORAGE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"izin verildi",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"İzin Verilmedi!",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void verigirfunction(){
        verigirme=(EditText)findViewById(R.id.verigirme);
        double value=Double.parseDouble(verigirme.getText().toString());
        verims.add(new veriler(value));
        verigirme.getText().clear();
        Toast.makeText(getApplicationContext(),String.valueOf(value)+" Verisi Eklendi",Toast.LENGTH_LONG).show();
    }

    private void verilistelefunction(){
            listView=(ListView)findViewById(R.id.listView);
            OzelAdaptor adaptor=new OzelAdaptor(this, verims);
            listView.setAdapter(adaptor);
    }

    private void verihesaplafunction(){
        Intent intent = new Intent(MainActivity.this,hesapla.class);
        int elemansayisi=verims.size();
        double elemantoplam=0.0;
        double aritmetikOrt=0.0;
        long   geometrikOrt = 1;
        double geometrik=0.0;
        double harmonikOrt=0.0;
        ArrayList<Double> medyansonuc=new ArrayList<Double>();
        double enbuyuk=0.0;
        double enkucuk=0.0;
        double varyans=0.0;
        double standartsapma=0.0;

        for (veriler veri:verims) {
            elemantoplam+=veri.getVeri();
            geometrikOrt*=veri.getVeri();
            harmonikOrt+=1.0/veri.getVeri();
        }

        //ARİTMETİK ORTALAMA HESABI
        aritmetikOrt=elemantoplam/elemansayisi;
        for (veriler veri:verims) {
            standartsapma+=Math.pow((veri.getVeri()-aritmetikOrt),2);
        }
        veriliste.clear();
        for (veriler veri:verims) {
            veriliste.add(veri.getVeri());
        }

        //MOD HESABI
        double modtekrar=0.0;
        double[] tekrarverisi=new double[veriliste.size()];
        double[] tekrar=new double[veriliste.size()];
        ArrayList<Double> tekrareden=new ArrayList<Double>();
        double mod=0.0;
        for (int k=0;k<veriliste.size();k++){
            tekrarverisi[k]=veriliste.get(k);
            tekrar[k]=veriliste.get(k);
        }
        int flag=0;
        for (int i=0;i<tekrarverisi.length;i++){
            flag=0;
            for (int j=0;j<veriliste.size();j++){
                if (tekrarverisi[i]==veriliste.get(j)){
                    flag++;
                }
            }
            tekrarverisi[i]=flag;
        }
        double temp=0.0,temp2=0.0;
        for (int i=0;i<tekrarverisi.length;i++)
        {
            for (int j=0;j<tekrarverisi.length-1;j++){
                if (tekrarverisi[j]>tekrarverisi[j+1]){
                    temp=tekrarverisi[j];
                    temp2=tekrar[j];
                    tekrarverisi[j]=tekrarverisi[j+1];
                    tekrar[j]=tekrar[j+1];
                    tekrarverisi[j+1]=temp;
                    tekrar[j+1]=temp2;
                }
            }

        }
        tekrareden.clear();
        double max=0;
        max=tekrarverisi[tekrarverisi.length-1];
        for (int i=0;i<tekrarverisi.length;i++)
        {
            for (int j=0;j<tekrarverisi.length-1;j++){
                if (tekrarverisi[j]==max && tekrarverisi[j]==tekrarverisi[j+1] && tekrarverisi[j]>1 && !tekrareden.contains(tekrar[j])){
                    tekrareden.add(tekrar[j]);
                }
            }

        }
        double modcarpiklik=0.0;
        for (Double veri:tekrareden) {
            modcarpiklik+=veri;
        }
        modtekrar=tekrarverisi[tekrarverisi.length-1];


        //EN BÜYÜK VE EN KÜÇÜK VERİ HESABI
        Collections.sort(veriliste);
        int verielemansayisi=veriliste.size();
        enbuyuk=veriliste.get(verielemansayisi-1);
        enkucuk=veriliste.get(0);

        //HARMONİK , GEOMETRİK ORTALAMALAR HESABI
        harmonikOrt=elemansayisi/harmonikOrt;
        geometrik=Math.pow(geometrikOrt , (1.0/elemansayisi));

        //STANDART SAPMA HESABI
        standartsapma=standartsapma/(elemansayisi-1);
        standartsapma=Math.sqrt(standartsapma);

        //VARYANS HESABI
        varyans=Math.pow(standartsapma,2);

        //ACTİVİTY ARASI VERİ GÖNDERİMİ
        intent.putExtra("value", elemansayisi);
        intent.putExtra("elemantoplam",elemantoplam);
        intent.putExtra("standartsapma",standartsapma);
        intent.putExtra("varyans",varyans);
        intent.putExtra("enbuyukeleman",enbuyuk);
        intent.putExtra("enkucukeleman",enkucuk);
        intent.putExtra("aritmetikort",aritmetikOrt);
        intent.putExtra("geometrikort",geometrik);
        intent.putExtra("harmonikort",harmonikOrt);
        intent.putExtra("medyan",medyan());
        intent.putExtra("modtekrar",modtekrar);
        intent.putExtra("mod",tekrareden);
        intent.putExtra("medyancarpiklik",medyancarpiklik(medyan()));
        Log.i("denii",String.valueOf(medyancarpiklik(medyan())));
        Log.i("denii",String.valueOf(modcarpiklik));
        intent.putExtra("modcarpiklik",modcarpiklik);
        startActivity(intent);
    }

    private  ArrayList<Double> medyan(){
        Collections.sort(veriliste);
        int verielemansayisi=veriliste.size();
        ArrayList<Double> medyan=new ArrayList<Double>();
        if(verielemansayisi%2==0){
            double d=(veriliste.get((verielemansayisi/2)-1)+veriliste.get(verielemansayisi/2))/2;
            medyan.add(d);
        }
        else {
            medyan.add(veriliste.get((verielemansayisi)/2));
        }
        return medyan;
    }
    private  Double medyancarpiklik(ArrayList<Double> med){
        double medyan=0.0;
        for (Double veri:med) {
            medyan+=veri;
        }
        return medyan;
    }

    private void loadData(String input) {
        File file = new File(input);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                double d = Double.parseDouble(row[sutunnumara+1]); //sayıları double cevır yaz
                verims.add(new veriler(d));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

