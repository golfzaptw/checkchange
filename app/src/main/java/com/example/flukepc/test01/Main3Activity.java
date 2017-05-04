package com.example.flukepc.test01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {
Bundle ex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ex = getIntent().getExtras();
        ArrayList<HashMap<String,String>> rent = (ArrayList<HashMap<String, String>>) ex.get("urlList");
        ListView lv = (ListView) findViewById(R.id.lv);
        show(rent,lv,Main3Activity.this);
        Button b = (Button) findViewById(R.id.btnBack);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent b = new Intent(Main3Activity.this,DetailofDrom.class);
                b.putExtra("iddrom",ex.getString("iddrom"));
                b.putExtra("id",ex.getString("id"));
                b.putExtra("search",ex.getString("search"));
                b.putExtra("username",ex.getString("username"));
                b.putExtra("email",ex.getString("email"));
                b.putExtra("id_fb",ex.getString("id_fb"));
                startActivity(b);
            }
        });

    }
    public void show(ArrayList<HashMap<String,String>> data , ListView lv, Context context){
        Custmpic cus = new Custmpic(data,context);
        lv.setAdapter(cus);

    }
}
