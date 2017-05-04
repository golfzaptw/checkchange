package com.example.flukepc.test01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class History extends AppCompatActivity {
    Bundle extra ;
    static final String id = "65" ;

    static String picurl  = "http://www.uphub.ml/uploads/get-pic";
    static String Grent = "http://uphub.ml/rent/get-rent";

    ArrayList<HashMap<String , String>> list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String , String>> pic = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String , String>> rennnnt = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me);
        extra = getIntent().getExtras();
        new Taskhere().execute();

        Button Bre = (Button) findViewById(R.id.Bre);
        Bre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(History.this ,Main_Search.class);
                i.putExtra("id",extra.getString("id"));
                startActivity(i);
            }
        });
    }
    class  Taskhere extends AsyncTask<String , Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            //////////////getrent////////////////////////////
            JSONParser jp = new JSONParser();
            JSONArray jaa  = jp.getJsonurl(Grent);

            for (int i = 0 ; i<jaa.length();i++){
                JSONObject job;
                try {
                    job = jaa.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id",job.getString("id"));
                    map.put("name",job.getString("name"));
                    rennnnt.add(map);
                    Log.e("dd",rennnnt.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ///////////////////get room as id /////////////////
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJsonurl("http://uphub.ml/room/get-viewroom?id="+extra.getString("id"));

            for (int i =0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject =null;

                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String ,String > map = new HashMap<String, String>();

                    //Log.e("Hello new world",jsonObject.getString("rent_id").toString());
                    for (int j = 0 ; j<rennnnt.size();j++){
                        if (jsonObject.getString("rent_id").equals(rennnnt.get(j).get("id"))){
                            map.put("id",jsonObject.getString("id"));
                            map.put("status",jsonObject.getString("status"));
                            map.put("rent_id",jsonObject.getString("rent_id"));
                            map.put("userid",jsonObject.getString("user_id"));
                            map.put("name", rennnnt.get(j).get("name"));
                            list.add(map);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




            /////////////////////for pic///////////////////////
            JSONParser d = new JSONParser();
            JSONArray ja = d.getJsonurl(picurl);

            for (int i =0;i<ja.length();i++){
                JSONObject j =null;

                try {
                    j = ja.getJSONObject(i);
                    HashMap<String,String> map= new HashMap<String, String>();
                    for (int k = 0; k <list.size();k++){
                        //Log.e("renttttt",list.get(k).toString());
                        if (j.getString("rent_id").equals(list.get(k).get("rent_id"))) {
                            map.put("rent_id", j.getString("rent_id"));
                            map.put("real_filename", j.getString("real_filename"));

                            pic.add(map);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("dddddddddd ",pic.toString());
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ListView lv = (ListView) findViewById(R.id.lv);
            TaskCus(list,pic,extra.getString("id"),History.this,lv);

        }

        public void TaskCus(ArrayList<HashMap<String,String>> list, ArrayList<HashMap<String,String>> pic, String id, Context context, ListView lv){
            CusforMe d = new CusforMe(list,context,id,pic);
            lv.setAdapter(d);
        }

    }

}
