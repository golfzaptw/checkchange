package com.example.flukepc.test01.lib;

import android.os.Build;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Curl {
    private String DATA = null;
    public Curl(){
        if(Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void ActionGET(String url){
        try {
            URL _url = new URL(url);
            URLConnection _Connection = _url.openConnection();
            _Connection.setRequestProperty("charset", "utf-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(_Connection.getInputStream()));
            String inputLine = in.readLine();
            DATA = inputLine;
        } catch (Exception e) {
            DATA = null;
        }
    }

    public void ActionPOST(String url, String parameters){
        try {
            byte[] postData = parameters.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postData.length;
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("User-Agent", "");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine = in.readLine();
            DATA = inputLine;
        }catch (Exception e) {
            DATA = null;
        }
    }

    public String getDATA(){
        return DATA;
    }
}
