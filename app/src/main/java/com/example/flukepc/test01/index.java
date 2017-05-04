package com.example.flukepc.test01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class index extends Activity {
    private  String email,username,id_fb,id,search;
    private  Bundle extars;
    private TextView namefb,idfb,emailfb ;
    private user p;
    HashMap<String ,String> map;

    private static final String  NAME = "name";
    private static final String ID = "id";

    ArrayList<HashMap<String,String>> jsonlist = new ArrayList<>();
    ListView lv;
    EditText inputSearch;
    private Button button;
    private LoginManager loginManager;
    private Profile profile;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_index);
        p = new user(index.this);

        extars= getIntent().getExtras();
        email = p.getEmail();
        username = p.getName();
        id_fb = p.getId_fb();
        id = p.getId();
        search = extars.getString("search");


        namefb = (TextView) findViewById(R.id.namefb);
        namefb.setText(username);
        button = (Button) findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                p.logout();
                Intent intent  = new Intent(index.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this,Search.class);
                intent.putExtra("username",username);
                intent.putExtra("id_fb",id_fb);
                intent.putExtra("id",id);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        new Task().execute();
    }
    public class Task extends AsyncTask<Bundle, Void, Boolean> {
        private ProgressDialog progressDialog;
        String url = "http://www.uphub.ml/rent/dd?name="+extars.getString("search") ;
        @Override
        protected Boolean doInBackground(Bundle... params) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJsonurl(url);

            for (int i = 0; i<jsonArray.length() ; i++){
                JSONObject c = null;
                try {
                    c = jsonArray.getJSONObject(i);
                    String name = c.getString("name");
                    String idd = c.getString("id");
                    map = new HashMap<String, String>();
                    map.put("index",i+"");
                    map.put(NAME,name);
                    map.put(ID,idd);
                    jsonlist.add(map);
                    Log.e("list",jsonlist.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            Log.e("listtttt", String.valueOf(jsonlist.size()));
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(index.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            progressDialog.show();
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            lv = (ListView) findViewById(R.id.lv);
            String id_fb = extars.getString("id_fb");
            String username = extars.getString("username");
            String email = extars.getString("email");
            String id = extars.getString("id");
            String search = extars.getString("search");
            show(jsonlist,lv,index.this,id_fb,username,search,email,id);


        }




        public void show(ArrayList<HashMap<String,String>> data , ListView lv, Context context, String id , String username,String search,String id_fb,String email){
            Custm cus = new Custm(data,context,id,username,search,id_fb,email);
            lv.setAdapter(cus);

        }

    }

}
