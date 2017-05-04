package com.example.flukepc.test01;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailofDrom extends AppCompatActivity {

    ProgressDialog dialog;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private user p ;
    ArrayList<HashMap<String,String>> rent = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> room = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> idaccroom = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> accroom = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> urlimg = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> urlacc = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> urlbooking = new ArrayList<HashMap<String, String>>();
    Bundle ex;
    ListView lv;
    static LocationManager locationManager;
    static LocationListener listener;
    String tmp_idroom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ex = getIntent().getExtras();
        p = new user(DetailofDrom.this);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");

        String username = p.getName();

        TextView name_fb = (TextView) findViewById(R.id.namefb);
        name_fb.setText(username);

        Log.e("dddd",p.getEmail()+p.getName()+p.getId_fb()+p.getId());
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(DetailofDrom.this, location.getLongitude() + "|" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                MapsActivity.lat = location.getLatitude();
                MapsActivity.lng = location.getLongitude();
                if (ActivityCompat.checkSelfPermission(DetailofDrom.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DetailofDrom.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.removeUpdates(listener);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        Button showmappath = (Button) findViewById(R.id.showmappath);
        showmappath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(DetailofDrom.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DetailofDrom.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.INTERNET}
                                ,10);
                    }
                    return;
                }
                locationManager.requestLocationUpdates("gps", 0, 0, listener);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(DetailofDrom.this,MapsActivity.class);
                intent.putExtra("id",tmp_idroom);
                startActivity(intent);
            }
        });

        new TASK().execute();

    }
    public class TASK extends AsyncTask<String , Void, Boolean>{
        String url ="http://www.uphub.ml/rent/get-rent";
        HashMap<String,String> map;

        String url2 ="http://www.uphub.ml/room/get-room";
        HashMap<String,String> map2;

        String url3 ="http://www.uphub.ml/room/get-ab";
        HashMap<String,String> map3;

        String url4 ="http://www.uphub.ml/room/get-ac";
        HashMap<String,String> map4;

        String url5 = "http://www.uphub.ml/uploads/get-pic";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
          JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJsonurl(url);
// json 1 ชุด-----------------------------------------------------------------------------------
            for (int i =0 ; i< jsonArray.length() ; i++){
                JSONObject jsonObject = null;
                try {


                   jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                    String intentid = ex.getString("iddrom");
                    String id  = jsonObject.get("id").toString();
                    String name = jsonObject.get("name").toString();
                    String near = jsonObject.getString("near");
                    String intendant = jsonObject.getString("intendant");
                    String tel1 = jsonObject.get("tel1").toString();
                    String cost_elec = jsonObject.get("cost_elec").toString();
                    String cost_water = jsonObject.getString("cost_water").toString();
                    String web = jsonObject.getString("web");
                    if (intentid.equals(id)) {
                        tmp_idroom = id;
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("name", name);
                        map.put("near",near);
                        map.put("tel1", tel1);
                        map.put("intendant",intendant);
                        map.put("cost_elec", cost_elec);
                        map.put("cost_water", cost_water);
                        map.put("web",web);
                        rent.add(map);
                    }

                    Log.e("id drom",rent.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
//-----------------------------------------------------------------------------------------
            //-------------------GET-ROOM----------------------------------------------
            JSONParser jsonParser2 = new JSONParser();
            JSONArray jsonArray2 = jsonParser2.getJsonurl(url2);

            for (int i=0 ;i< jsonArray2.length();i++){
                JSONObject jsonObject = null;

                try {
                    jsonObject = (JSONObject) jsonArray2.get(i);
                    HashMap<String, String> map2 = new HashMap<String, String>();
                    map2.put("rent_id",jsonObject.get("rent_id").toString());
                    room.add(map2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            //----------------------------------------------------------------------------
            //-------------------GET-ชื่ออุปกรณ์----------------------------------------------
            JSONParser jsonParser3 = new JSONParser();
            JSONArray jsonArray3 = jsonParser3.getJsonurl(url3);

            for (int i=0 ;i< jsonArray3.length();i++){
                JSONObject jsonObject = null;
                String iddrom = ex.getString("iddrom");

                try {
                    jsonObject = (JSONObject) jsonArray3.get(i);
                    HashMap<String, String> map3 = new HashMap<String, String>();
                    if (iddrom.equals(jsonObject.getString("rent_id"))) {
                        map3.put("accessories_id", jsonObject.get("accessories_id").toString());
                        idaccroom.add(map3);
                        Log.e("ACCESSORIES :",jsonObject.get("rent_id").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            //----------------------------------------------------------------------------
            //-------------------GET-ห้องนั้นมีอะไรบ้าง----------------------------------------------
            JSONParser jsonParser4 = new JSONParser();
            JSONArray jsonArray4 = jsonParser4.getJsonurl(url4);

            for (int i=0 ;i< jsonArray4.length();i++){
                JSONObject jsonObject = null;

                try {
                    jsonObject = (JSONObject) jsonArray4.get(i);
                    HashMap<String, String> map4 = new HashMap<String, String>();
                    map4.put("id",jsonObject.getString("id"));
                    map4.put("name",jsonObject.get("name").toString());
                    accroom.add(map4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            //----------------------------------------------------------------------------
            //------------------------------GET pic---------------------------------------
            JSONParser jsonParser5 = new JSONParser();
            JSONArray jsonArray5 = jsonParser5.getJsonurl(url5);
            String picdrom = ex.getString("iddrom");
            int j=0;
            for (int i=0;i<jsonArray5.length();i++){

                try {
                    JSONObject jsonObject = jsonArray5.getJSONObject(i);
                    HashMap<String,String> map5 = new HashMap<String, String>();
                    if(picdrom.equals(jsonObject.getString("rent_id"))) {
                        map5.put("rent_id", jsonObject.getString("rent_id"));
                        map5.put("real_filename", jsonObject.getString("real_filename"));
                        urlimg.add(map5);
                        j++;
                        Log.e("GGGGGGGGGGG",j+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //----------------------------------------------------------------------------
            Log.e("rent   :", rent.toString());
            Log.e("room   :", room.toString());
            Log.e("jsonlist   :", idaccroom.toString());
            Log.e("jsonlist   :", accroom.toString());
            //-----------------------Booking------------------------------------
            String urlempty = "http://uphub.ml/room/get-rm?iddr="+rent.get(0).get("id");
            JSONParser jsonParserbook = new JSONParser();
            JSONArray jsonArraybook = jsonParserbook.getJsonurl(urlempty);
            JSONObject jsonObject = null;
            for (int i = 0;i<jsonArraybook.length();i++){
                try {
                    jsonObject = jsonArraybook.getJSONObject(i);
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("room",jsonObject.getString("room"));
                    map.put("cost",jsonObject.getString("cost"));

                    urlbooking.add(map);
                    Log.e("fdfdfd :",jsonObject.getString("room"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //-------------------------View ACC-------------------------------
            String urlac = "http://uphub.ml/room/get-acroom?id="+rent.get(0).get("id");
            JSONParser jsonParseracc = new JSONParser();
            JSONArray jsonArrayacc = jsonParseracc.getJsonurl(urlac);
            JSONObject jsonObjectacc = null;
            for (int i = 0;i<jsonArraybook.length();i++){
                try {
                    jsonObjectacc = jsonArrayacc.getJSONObject(i);
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("name",jsonObjectacc.getString("name"));
                    urlacc.add(map);
                    Log.e("fdfdfd :",jsonObject.getString("room"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();




            Button logout = (Button) findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginManager.getInstance().logOut();
                    p.logout();
                    Intent intent  = new Intent(DetailofDrom.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            // show textview on device
            Button btnBack = (Button) findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent b = new Intent(DetailofDrom.this,Main_Search.class);
                    b.putExtra("username",p.getName());
                    b.putExtra("id_fb",p.getId_fb());
                    b.putExtra("iddrom",ex.getString("iddrom"));
                    b.putExtra("id",p.getId());
                    b.putExtra("email",p.getEmail());
                    b.putExtra("search","");
                    startActivity(b);
                }
            });

            TextView tv = (TextView) findViewById(R.id.tv1);
            tv.setText(rent.get(0).get("name"));

            Button mDetail = (Button) findViewById(R.id.btnDetail);
            mDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailofDrom.this);
                    View mView = getLayoutInflater().inflate(R.layout.popwindow,null);
                    TextView namerent = (TextView) mView.findViewById(R.id.namer);
                    namerent.setText("ชื่อหอพัก :"+" "+rent.get(0).get("name"));
                    TextView near = (TextView) mView.findViewById(R.id.near);
                    near.setText("สถานที่ใกล้เคียง :"+" "+rent.get(0).get("near"));
                    TextView cost_elec = (TextView) mView.findViewById(R.id.cost_elec);
                    cost_elec.setText("ค่าไฟ :"+" "+rent.get(0).get("cost_elec")+" "+"บาทต่อหน่วย");
                    TextView cost_water = (TextView) mView.findViewById(R.id.cost_water);
                    cost_water.setText("ค่าน้ำ :"+" "+rent.get(0).get("cost_water")+" "+"ต่อคน");
                    TextView intendant = (TextView) mView.findViewById(R.id.intendant);
                    intendant.setText("ผู้ดูแลหอพัก :"+" "+rent.get(0).get("intendant"));
                    TextView tel1 = (TextView) mView.findViewById(R.id.tel1);
                    tel1.setText("เบอร์โทร :"+" "+rent.get(0).get("tel1"));
                    TextView web = (TextView) mView.findViewById(R.id.web);
                    web.setText("เว็บไซต์ :"+" "+rent.get(0).get("web"));



                        TextView nameac = (TextView) mView.findViewById(R.id.nameac);

                    String vq ="";
                        for (int vv = 0; vv < urlacc.size(); vv++) {
                            vq += urlacc.get(vv).get("name") + "\n";
                        }
                    Log.e("dddddddddddddd",vq);
                    nameac.setText(vq);

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }

            });
            Button btnBooking = (Button) findViewById(R.id.btnBooking);
            btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailofDrom.this);
                    View mView = getLayoutInflater().inflate(R.layout.popbooking,null);

                    lv = (ListView) mView.findViewById(R.id.lv);
                    show(urlbooking,lv,DetailofDrom.this,ex,rent);

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
                public void show(ArrayList<HashMap<String, String>> data, ListView lv, Context context, Bundle d, ArrayList<HashMap<String, String>> rent ){
                    Custroom cus = new Custroom(data,context,d,rent);
                    lv.setAdapter(cus);
                }
            });

            TASKPIC pic = new TASKPIC();
            try{
            pic.execute("http://uphub.ml/PhotoDorm/"+urlimg.get(0).get("rent_id")+"/"+urlimg.get(0).
                    get("real_filename"));
        }catch (Exception e){

            }
            Button mPic = (Button) findViewById(R.id.btnImg);
            mPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent url = new Intent(DetailofDrom.this,Main3Activity.class);
                    String intentid = ex.getString("iddrom");
                    String id = p.getId();
                    String id_fb = p.getId_fb();
                    String email = p.getEmail();
                    String username = p.getName();
                    url.putExtra("urlList",urlimg);
                    url.putExtra("id",id);
                    url.putExtra("iddrom",intentid);
                    url.putExtra("username",username);
                    url.putExtra("id_fb",id_fb);
                    url.putExtra("email",email);


                    startActivity(url);
                }

            });

        }



    }
    public  class TASKPIC extends AsyncTask<String,Void,Bitmap>{
        public void show(ArrayList<HashMap<String,String>> data , ListView lv, Context context){
            Custmpic cus = new Custmpic(data,context);
            lv.setAdapter(cus);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Getpic g = new Getpic();
            return g.getSomepicBit(params[0]);
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {

            super.onPostExecute(bitmap);
            ImageView gg = (ImageView) findViewById(R.id.urlImg);
            gg.setImageBitmap(bitmap);



            gg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(DetailofDrom.this,"gg",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}
