package com.example.flukepc.test01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FlukePC on 15/1/2560.
 */

public class Custroom extends BaseAdapter  {
    private user p ;
   ArrayList<HashMap<String,String>> mData;
    ArrayList<HashMap<String,String>> rent;

    Context mContext ;
    Bundle ex;

    public Custroom(ArrayList<HashMap<String, String>> list, Context mContext, Bundle d, ArrayList<HashMap<String, String>> rent) {
        this.mData = list;
        this.mContext=mContext;
        this.rent = rent;
        this.ex = d;
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
        p = new user(mContext);
       LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.rowroom,parent,false);
        TextView tx = (TextView) row.findViewById(R.id.room);
        String [] a = mData.get(position).get("room").toString().split("");

        if (a[0].equals("0")){
            tx.setText(mData.get(position).get("room").toString()+" (แอร์)");
        }else {
            tx.setText(mData.get(position).get("room").toString()+" (พัดลม)");
        }


        final  String d =  mData.get(position).get("room").toString();
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,DetailBooking.class);
                i.putExtra("id",d);
                String intentid = ex.getString("iddrom");
                String id = p.getId();
                String id_fb = p.getId_fb();
                String email = p.getEmail();
                String username = ex.getString("username");
                i.putExtra("iddrom",intentid);
                i.putExtra("iduser",id);
                i.putExtra("username",username);
                i.putExtra("room",d);
                i.putExtra("id_fb",id_fb);
                i.putExtra("email",email);
                i.putExtra("namedrom",rent.get(0).get("name"));
                i.putExtra("cost",mData.get(position).get("cost"));
                Log.e("dddd",p.getEmail()+p.getName()+p.getId_fb()+p.getId());
                mContext.startActivity(i);

            }
        });
        return  row;
    }
}
