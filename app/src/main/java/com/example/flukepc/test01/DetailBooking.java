package com.example.flukepc.test01;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DetailBooking extends AppCompatActivity {
Bundle ex;
    TextView d,n,ty;
    View go,back;
    user oo;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);
        oo  = new user(DetailBooking.this);
        ex = getIntent().getExtras();
        d = (TextView) findViewById(R.id.dataill);
        n= (TextView) findViewById(R.id.name);
        go =  findViewById(R.id.go);
        back = findViewById(R.id.backk);
        Log.e("dddd",oo.getEmail()+oo.getName()+oo.getId_fb()+oo.getId());
        n.setText(ex.getString("namedrom"));
        d.setText("หมายเลขห้อง: "+ex.getString("id")+"\n\n"+"ราคาต่อเดือน: "+ex.getString("cost")+" บาท"+
                "\n\n"+"ชื่อผู้จอง: "+oo.getName());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailBooking.this,DetailofDrom.class);
                i.putExtra("id",ex.getString("id"));
                String intentid = ex.getString("iddrom");
                String id = ex.getString("id");
                String id_fb = ex.getString("id_fb");
                String email = ex.getString("email");
                String username = ex.getString("username");
                i.putExtra("iddrom",intentid);
                i.putExtra("iduser",id);

                i.putExtra("username",username);
                i.putExtra("id_fb",id_fb);
                i.putExtra("email",email);
                startActivity(i);

            }
        });
            final  String name=oo.getName();
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int timeout = 1500;

                new getMelink().sendmeurl("http://uphub.ml/room/get-bkand?iddr="+ex.getString("iddrom")+"&&userid="+oo.getId()+"&&room="+ex.getString("room"));
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailBooking.this);
                //Log.e("abc",ex.getString("iddrom")+oo.getId()+ex.getString("room"));
                View mView = getLayoutInflater().inflate(R.layout.finalpop,null);
                ty = (TextView) mView.findViewById(R.id.ty);
                ty.setText("\n\n\nท่านได้กระทำการเสร็จสิ้นแล้ว \n ขอบคุณ "+name+"ที่มาใช้บริการของเรา\n\n\n");

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                        Intent homepage = new Intent(DetailBooking.this,Main_Search.class);
                        dialog.dismiss();
                        homepage.putExtra("id"," ");
                        startActivity(homepage);
                    }
                },timeout);

            }
        });


    }
}
