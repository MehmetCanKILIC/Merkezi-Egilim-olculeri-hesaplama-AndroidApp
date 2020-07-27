package com.example.merkeziegilim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.text.NumberFormat;
import java.util.List;

public class OzelAdaptor extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<veriler> list;
    Activity activity;
    public OzelAdaptor(Activity activity, List<veriler> mList){
        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list=mList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View satirView;
        satirView=layoutInflater.inflate(R.layout.satir, null);

        TextView tv2=(TextView)satirView.findViewById(R.id.textViewFiyat);
        Button silbuton=(Button)satirView.findViewById(R.id.silbuton);
        final veriler verim=list.get(position);
        // NumberFormat.getCurrencyInstance().format(0.5); --> 0.5 TL
        tv2.setText(NumberFormat.getNumberInstance().format(verim.getVeri()));
        silbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(list.get(position));
                Toast.makeText(activity.getApplicationContext(),String.valueOf(verim.getVeri())+" verisi silindi.'Verileri Güncelle' butonuna bastıktan sonra veri kaybolacak.",Toast.LENGTH_LONG).show();
            }
        });
        return satirView;
    }
}
