package com.example.flukepc.test01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fluke narhee on 27/1/2560.
 */

public class Getpic {
    public  Bitmap getSomepicBit(String url){
        try {
            URL url1 = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream  = httpURLConnection.getInputStream();
            Bitmap bit = BitmapFactory.decodeStream(inputStream);
          Bitmap s = Bitmap.createScaledBitmap(bit,150,150,true);

            Log.e("bit ",bit.toString());
            return s;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;

    }

}

