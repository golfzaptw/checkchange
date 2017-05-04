package com.example.flukepc.test01;

import android.content.Context;
import android.content.Intent;
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

public class Custm extends BaseAdapter  {
   ArrayList<HashMap<String,String>> mData;
    Context mContext ;
    String id;
    String username;
    String search;
    String id_fb;
    String email;
    // #TODO add some id facebook on ur session

    public Custm(ArrayList<HashMap<String,String>> list, Context mContext, String id , String username,String search,String id_fb,String email) {
        this.mData = list;
        this.mContext=mContext;
        this.id = id;
        this.username = username;
        this.search = search;
        this.email = email;
        this.id_fb = id_fb;

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
        View row = inflater.inflate(R.layout.row,parent,false);
        TextView tv = (TextView)row.findViewById(R.id.tv);
        //ImageView im = (ImageView) row.findViewById(R.id.imageIcon);
        tv.setText(mData.get(position).get("name")+"");
        //im.setImageDrawable(R.drawable.ic_launcher);
       final String tvv = mData.get(position).get("name")+"";
        final String idd= mData.get(position).get("id").toString();
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,DetailofDrom.class);
                i.putExtra("iddrom",idd);
                i.putExtra("id",id);
                i.putExtra("username",username);
                i.putExtra("email",email);
                i.putExtra("id_fb",id_fb);
                i.putExtra("search",search);
                mContext.startActivity(i);
            }
        });



        return  row;



    }


}
