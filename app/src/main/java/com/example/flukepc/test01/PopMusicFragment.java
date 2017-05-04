package com.example.flukepc.test01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PopMusicFragment extends Fragment {
private View view;
    ArrayList<HashMap<String,String >> rentlist = new ArrayList<>();
    private View root;
    private user p;
    private Button btnsearch;
    private EditText editText;
    public PopMusicFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pop_music, container, false);
        root = inflater.inflate(R.layout.search_main,container,false);
        new setTask("http://uphub.ml/rent/get-rent",PopMusicFragment.this).execute();
        p = new user(view.getContext());
        final String email = p.getEmail();
        final String username = p.getName();
        final String id = p.getId();
        final String id_fb = p.getId_fb();
        Log.e("dddd",p.getEmail()+p.getName()+p.getId_fb()+p.getId());
        return view;
    }



    public class setTask extends AsyncTask<String ,Void ,Boolean> {
        List<ItemObject> objects =new ArrayList<>();
        String url;
        PopMusicFragment context;

        public setTask(String url,  PopMusicFragment mContext){
            this.url = url;

            this.context = mContext;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = jsonParser.getJsonurl(url);
            ItemObject s;
            for (int i=0 ; i<jsonArray.length();i++){
                try {
                    JSONObject c = jsonArray.getJSONObject(i);
                    s= new ItemObject(c.getString("name"),c.getString("near"));
                    objects.add(s);
                    HashMap<String ,String > map = new HashMap<>();
                    map.put("iddrom",c.getString("id"));
                    rentlist.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //-----------------------------------------------------------------



            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


    ListView gridview = (ListView) view.findViewById(R.id.gridview);

    CustomAdapter customAdapter = new CustomAdapter(getActivity(), objects,rentlist);
            gridview.setAdapter(customAdapter);

}

    }

}
