package com.example.flukepc.test01;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by FlukePC on 15/1/2560.
 */

public class JSONParser {
  static InputStream inputStream =null ;
    static JSONArray jsonArray=null;
    static String json="";

    public JSONArray getJsonurl(String url){
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();

            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = null;

            if (httpURLConnection.getResponseCode()==httpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                String line="";

                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jsonArray = new JSONArray(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Log.e("name " ,jsonArray.toString());
        return  jsonArray;
    }





}
