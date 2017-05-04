package com.example.flukepc.test01;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import com.google.android.gms.maps.model.LatLng;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FlukePc on 11/2/2560.
 */

public class CusforMe  extends BaseAdapter{
ArrayList<HashMap<String,String>> mData;
    static LocationManager locationManager;
    static LocationListener listener;
    ArrayList<HashMap<String,String>> pic;
    String id ;

    Context mContext;
    user users;

    public CusforMe(ArrayList<HashMap<String, String>> list, Context context, String id, ArrayList<HashMap<String, String>> pic){
      this.mData = list;
        this.mContext= context;
        this.id= id;
        this.pic = pic;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.membercus,parent,false);
        ImageView im = null;
        TextView ss = (TextView) row.findViewById(R.id.very);
        TextView  name = (TextView) row.findViewById(R.id.namedr);
        Button btnMap = (Button) row.findViewById(R.id.btnMap);
        final TextView  status = (TextView) row.findViewById(R.id.statusdr);
        users = new user(mContext);

        locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(mContext, location.getLongitude() + "|" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                MapsActivity.lat = location.getLatitude();
                MapsActivity.lng = location.getLongitude();
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                mContext.startActivity(i);
            }
        };

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      Toast.makeText(mContext,"กรุณาเปิด GPS และ Internet ของท่านด้วย",Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                locationManager.requestLocationUpdates("gps", 0, 0, listener);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(mContext,MapsActivity.class);
                intent.putExtra("id",mData.get(position).get("rent_id"));
                mContext.startActivity(intent);


            }
        });



        name.setText(mData.get(position).get("name"));
        ss.setText(""+"Userid: "+users.getId()+"\n"+"RoomId: "+mData.get(position).get("id"));
        String ssss =mData.get(position).get("status");
        Log.e("qwqwqwqwqw",ssss);
        if (mData.get(position).get("status").equals("1")){
            status.setText("จองแล้ว");
        }else
        if (mData.get(position).get("status").equals("2")){
            status.setText("กำลังเข้าพักในขณะนี้");
        }else{
            status.setText("เกิดข้อผิดพลาดบางประการ");
        }
if (pic.isEmpty()) {
}else{new Somepic(row).execute("http://uphub.ml/PhotoDorm/" + mData.get(position).get("rent_id") + "/" + pic.get(0).get("real_filename")); }
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.getText().equals("จองแล้ว")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("คุณแน่ในหรือไม่ที่จะยกเลิกการจอง").setTitle("Confirm");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getMelink d = new getMelink();
                            d.sendmeurl("http://uphub.ml/room/get-someret?user_id="+id+"&room_id="+mData.get(position).get("id")+"&rent_id="+mData.get(position).get("rent_id"));

                            int timeout = 100;
                           Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent re = new Intent(mContext,History.class);
                                    re.putExtra("id",id);
                                    mContext.startActivity(re);
                                }
                            },timeout);


                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(mContext,"ไม่สามารถทำอะไรได้เลย หรือ หากต้องการย้ายออกจากหอกรุณาติดต่อเจ้าของหอครับ",Toast.LENGTH_SHORT).show();}
            }
        });
        return row;
    }


}

class Somepic extends AsyncTask<String ,Void,Bitmap>{

    ImageView im;
    View row;
    public Somepic( View im) {

        this.row=im;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        Getpic g = new Getpic();
        return g.getSomepicBit(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        im = (ImageView) row.findViewById(R.id.imageView);
        im.setImageBitmap(bitmap);

    }
}