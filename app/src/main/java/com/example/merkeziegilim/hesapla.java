package com.example.merkeziegilim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.StringWriter;
import java.util.ArrayList;

public class hesapla extends AppCompatActivity {
    TextView texttoplameleman,mod,carpikliktext,textelemantoplam,ssapma,varyans,enbuyuk,enkucuk,aritmetik,harmonik,geometrik,medyansonuc,modtekrar;
    double tekrarsayisi=0.0,aritmetikorth=0.0,modha=0.0,medyanha=0.0;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesapla);

        texttoplameleman=(TextView)findViewById(R.id.elemansayisi);
        texttoplameleman.setText(String.valueOf(bundle.getInt("value")));

        textelemantoplam=(TextView)findViewById(R.id.elemantoplami);
        textelemantoplam.setText(String.valueOf(bundle.getDouble("elemantoplam")));

        ssapma=(TextView)findViewById(R.id.ssapma);
        ssapma.setText(String.valueOf(bundle.getDouble("standartsapma")));

        varyans=(TextView)findViewById(R.id.varyans);
        varyans.setText(String.valueOf(bundle.getDouble("varyans")));

        enbuyuk=(TextView)findViewById(R.id.enbuyukeleman);
        enbuyuk.setText(String.valueOf(bundle.getDouble("enbuyukeleman")));

        enkucuk=(TextView)findViewById(R.id.enkucukeleman);
        enkucuk.setText(String.valueOf(bundle.getDouble("enkucukeleman")));

        aritmetik=(TextView)findViewById(R.id.aritmetikort);
        aritmetikorth=bundle.getDouble("aritmetikort");
        aritmetik.setText(String.valueOf(aritmetikorth));

        harmonik=(TextView)findViewById(R.id.harmonikort);
        harmonik.setText(String.valueOf(bundle.getDouble("harmonikort")));

        geometrik=(TextView)findViewById(R.id.geometrikort);
        geometrik.setText(String.valueOf(bundle.getDouble("geometrikort")));

        medyansonuc=(TextView)findViewById(R.id.medyan);
        medyansonuc.setText(String.valueOf(bundle.getStringArrayList("medyan")));

        modtekrar=(TextView)findViewById(R.id.modtekrar);
        tekrarsayisi=bundle.getDouble("modtekrar");
        modtekrar.setText(String.valueOf(Math.round(tekrarsayisi)));

        mod=(TextView)findViewById(R.id.mod);
        if (tekrarsayisi>1){
            mod.setText(String.valueOf(bundle.getStringArrayList("mod")));
        }else {
            mod.setText("Mod tekrar eden veri yok)");
        }

        imageView=(ImageView)findViewById(R.id.carpiklikresim);
        carpikliktext=(TextView)findViewById(R.id.carpikliktext);
        if (aritmetikorth<bundle.getDouble("medyancarpiklik") && bundle.getDouble("medyancarpiklik")<bundle.getDouble("modcarpiklik")){
            carpikliktext.setText("A.O < Medyan < Mod ");
            imageView.setBackgroundResource(R.drawable.sagacarpik);

        }
        else if(bundle.getDouble("modcarpiklik")<bundle.getDouble("medyancarpiklik") && bundle.getDouble("medyancarpiklik")<aritmetikorth){
            carpikliktext.setText("Mod < Medyan < A.O ");
            imageView.setBackgroundResource(R.drawable.solacarpik);
        }
        else if (aritmetikorth==bundle.getDouble("medyancarpiklik") && bundle.getDouble("medyancarpiklik")==bundle.getDouble("modcarpiklik")){
            carpikliktext.setText("A.O = Medyan = Mod");
            imageView.setBackgroundResource(R.drawable.simetrik);
        }


    }

}
