package com.example.flukepc.test01;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by FlukePc on 3/2/2560.
 */

public class user {
    String name ;
    String id_fb ;
    String email;


    public String getId() {
        return preferences.getString("id",null);
    }

    public void setId(String id) {
        preferences.edit().putString("id",id).commit();
    }

    String id;
    public String getEmail() {
        return preferences.getString("email",null);
    }

    public void setEmail(String email) {
        preferences.edit().putString("email",email).commit();
    }


    private SharedPreferences preferences;

    public user(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getId_fb() {
        String id_fb = preferences.getString("id_fb",null);
        return  id_fb;
    }

    public void setId_fb(String id_fb) {
        preferences.edit().putString("id_fb",id_fb).commit();
    }

    public String getName() {
        String name = preferences.getString("name",null);
        return  name;

    }

    public void setName(String name) {
        preferences.edit().putString("name",name).commit();
    }


public void logout(){
preferences.edit().clear().commit();



}


}
