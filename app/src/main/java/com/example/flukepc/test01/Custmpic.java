package com.example.flukepc.test01;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Patawee on 27/1/2560.
 */

public class Custmpic extends BaseAdapter{
    ImageView ar;
    ArrayList<HashMap<String,String>> mData;
    Context mContext ;
    // #TODO add some id facebook on ur session

    public Custmpic(ArrayList<HashMap<String,String>> list, Context mContext) {
        this.mData = list;
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
        if (mData.size()>5){
            return 5;
        }else{
        return mData.size();
    }}

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View row = inflater.inflate(R.layout.ar,parent,false);
        new AsyncTask<String, Void, Bitmap> (){
            @Override
            protected Bitmap doInBackground(String... params) {
                Getpic g = new Getpic();
                return g.getSomepicBit(params[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
            Log.e("gfgfgfgfgfgfgf",bitmap.toString());
                ar = (ImageView) row.findViewById(R.id.imageView);
                ar.setImageBitmap(bitmap);

            }}.execute("http://uphub.ml/PhotoDorm/"+mData.get(position).get("rent_id")+"/"+mData.get(position).
                get("real_filename"));
        return row;
    }
}
