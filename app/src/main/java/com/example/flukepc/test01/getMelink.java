package com.example.flukepc.test01;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FlukePc on 3/2/2560.
 */

public class getMelink {
    public String sendmeurl(final String url){
        final String[] responceRes = {""};
        new Thread() {
            @Override
            public void run() {
                URL u = null;
                try {
                    u = new URL(url);
                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                    c.setRequestMethod("GET");
                    c.connect();
                    InputStream in = c.getInputStream();

                    char[] buf = new char[2048];
                    Reader r = new InputStreamReader(in,"UTF-8");
                    StringBuilder stringBuilder =new StringBuilder();
                    while (true){
                        int countbuff = r.read(buf);
                        if (countbuff<0) break;
                        stringBuilder.append(buf,0,countbuff);
                    }
                    //Log.e("heyyyy",stringBuilder.toString());
                   responceRes[0] = stringBuilder.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return responceRes[0];
    }
}
