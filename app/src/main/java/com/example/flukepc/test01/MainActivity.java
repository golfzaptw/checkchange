package com.example.flukepc.test01;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends Activity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private user p ;
    ArrayList<HashMap<String,String>> listprofile = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        final ImageView a = (ImageView) findViewById(R.id.imagenik);



//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.flukepc.test01",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));


        callbackManager = CallbackManager.Factory.create();

        p = new user(MainActivity.this);



        if (p.getName()!=null){
            Intent intent =new Intent(MainActivity.this,Main_Search.class);
            intent.putExtra("username",p.getName());
            startActivity(intent);
            finish();
        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken() , new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
                            String firstname[] = object.getString("name").split(" ");
                            HashMap<String , String> map = new HashMap<String, String>();
                            map.put("firstname",firstname[0]);
                            map.put("lastname",firstname[1]);
                            map.put("id_fb",object.getString("id"));
                            map.put("email",object.getString("email"));
                            listprofile.add(map);
                            getMelink d = new getMelink() ;
                            String url = "http://www.uphub.ml/user/get-android?id="+object.getString("id")+"&username="+firstname[0]+"%20"+firstname[1]+"&email="+object.getString("email");
                            d.sendmeurl(url);

                            Thread.sleep(1500);



                            new Task("http://uphub.ml/rent/get-usersess?fbid="+listprofile.get(0).get("id_fb")).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                graphRequest.setParameters(parameters);

                graphRequest.executeAsync();
                a.setImageResource(R.drawable.nikkpnerroe);

//                ProfileTracker dd = new ProfileTracker() {
//                    @Override
//                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//
//                        p.setId(currentProfile.getId());
//                        p.setName(currentProfile.getName());
//                    }
//                };
//                dd.startTracking();



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

public class Task extends AsyncTask<String, Void, Boolean> {
String url ="";
    public Task(String url) {
        this.url=url;
    }

    @Override
        protected Boolean doInBackground(String... params) {
        JSONParser jsonParserDB = new JSONParser();
        JSONArray jsonArrayDB = jsonParserDB.getJsonurl(url);;




        for (int i=0;i<jsonArrayDB.length();i++){
            JSONObject jsonObjectDB = null;
            try {
                jsonObjectDB = jsonArrayDB.getJSONObject(i);
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("id",jsonObjectDB.getString("id"));
                map.put("id_fb",jsonObjectDB.getString("id_fb"));
                map.put("username",jsonObjectDB.getString("username"));
                map.put("email",jsonObjectDB.getString("email"));
                map.put("role",jsonObjectDB.getString("role"));

                arrayList.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
            return null;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (arrayList.get(0).get("role").toString().equals("10")){
            p.setId(arrayList.get(0).get("id"));
            p.setId_fb(arrayList.get(0).get("id_fb"));
            p.setName(arrayList.get(0).get("username"));
            p.setEmail(arrayList.get(0).get("email"));
                Log.e("ขี้หีหน้าแรก",p.getEmail()+p.getName()+p.getId_fb()+p.getId());
            Intent intent =new Intent(MainActivity.this,Main_Search.class);
            intent.putExtra("username",p.getName());
            intent.putExtra("email",p.getEmail());
            intent.putExtra("id",p.getId());
            intent.putExtra("id_fb",p.getId_fb());
            startActivity(intent);}else{
                p.logout();
                LoginManager.getInstance().logOut();
            }


        }
    }
}
