package com.example.flukepc.test01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;
    private  ArrayList<HashMap<String, String>> rentlist = new ArrayList<>();


    public CustomAdapter(Context context, List<ItemObject> customizedListView, ArrayList<HashMap<String, String>> rentlist) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        this.rentlist = rentlist;

    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.pop_music_list, parent, false);

            listViewHolder.musicName = (TextView)convertView.findViewById(R.id.name);
            listViewHolder.cost = (TextView)convertView.findViewById(R.id.detail);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

         listViewHolder.musicName.setText(listStorage.get(position).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(context,DetailofDrom.class);
                s.putExtra("iddrom",rentlist.get(position).get("iddrom").toString());
                context.startActivity(s);
            }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView musicName;
        TextView cost;

    }


}
