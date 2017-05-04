package com.example.flukepc.test01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flukepc.test01.datahup.DataHup;
import com.example.flukepc.test01.lib.Curl;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private user p;
    Bundle ex;
    EditText editText;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        p = new user(Search.this);
        getMelink getLink = new getMelink();
        ex = getIntent().getExtras();
        final String email = p.getEmail();
        final String username = p.getName();
        final String id = ex.getString("id");
        final String id_fb = p.getId_fb();

        Log.e("dddd",p.getEmail()+p.getName()+p.getId_fb()+p.getId());
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView name_fb = (TextView) findViewById(R.id.namefb);
        name_fb.setText(username);
        Button button = (Button) findViewById(R.id.btnLogout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                p.logout();
                Intent intent  = new Intent(Search.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        editText = (EditText) findViewById(R.id.editText);

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gg = editText.getText().toString();
                Intent intent = new Intent(Search.this,index.class);
                intent.putExtra("search",gg);
                intent.putExtra("email",email);
                intent.putExtra("username",username);
                intent.putExtra("id",id);
                intent.putExtra("id_fb",id_fb);
                startActivity(intent);
            }
        });

        Button btnAll = (Button) findViewById(R.id.btnAll);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this,index.class);
                intent.putExtra("search","");
                intent.putExtra("email",email);
                intent.putExtra("username",username);
                intent.putExtra("id",id);
                intent.putExtra("id_fb",id_fb);
                startActivity(intent);
            }
        });


        Button btnMe = (Button) findViewById(R.id.me);
        btnMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Search.this,History.class);
                i.putExtra("id",p.getId());
                startActivity(i);
            }
        });

        getDataHup();
        Button showmapall = (Button) findViewById(R.id.showmapall);
        showmapall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsActivity.lat = 19.030885;
                MapsActivity.lng = 99.926184;

                Intent intent = new Intent(Search.this,MapsActivity.class);
                intent.putExtra("id","NULL");
                startActivity(intent);
            }
        });

    }

    DataHup dataHup;
    static ArrayList<DataHup> Array_DataHup;
    private void getDataHup() {
        Curl curl = new Curl();
        Array_DataHup = new ArrayList<DataHup>();
        try {
            curl.ActionGET("http://www.uphub.ml/rent/get-rent");
            String data = curl.getDATA();
            JSONArray J_array = new JSONArray(data);
            for (int i = 0; i < J_array.length(); i++) {
                JSONObject Obj_tmp = J_array.getJSONObject(i);
                dataHup = new DataHup();
                dataHup.setId(Obj_tmp.getString("id"));
                dataHup.setName(Obj_tmp.getString("name"));
                dataHup.setNear(Obj_tmp.getString("near"));
                dataHup.setIntendant(Obj_tmp.getString("intendant"));
                dataHup.setCost_water(Obj_tmp.getString("cost_water"));
                dataHup.setCost_elec(Obj_tmp.getString("cost_elec"));
                dataHup.setTel1(Obj_tmp.getString("tel1"));
                dataHup.setTel2(Obj_tmp.getString("tel2"));
                dataHup.setWeb(Obj_tmp.getString("web"));
                dataHup.setType_gen(Obj_tmp.getString("type_gen"));
                dataHup.setType_rent(Obj_tmp.getString("type_rent"));
                dataHup.setUser_id(Obj_tmp.getString("user_id"));
                dataHup.setCondition(Obj_tmp.getString("condition"));
                dataHup.setEdited(Obj_tmp.getString("edited"));
                dataHup.setVisible(Obj_tmp.getString("visible"));
                dataHup.setAddress_id(Obj_tmp.getString("address_id"));
                dataHup.setView(Obj_tmp.getString("view"));
                dataHup.setLat(Obj_tmp.getString("lat"));
                dataHup.setLng(Obj_tmp.getString("long"));
                Array_DataHup.add(dataHup);
            }

        } catch (Exception e) {
            Toast.makeText(this,"Error API",Toast.LENGTH_SHORT).show();
        }


    }

}
