package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.strsar.mapsrun.MapsActivity;
import com.strsar.mapsrun.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String mprovider;

    Button signin;
    String user,pass;
    TextInputEditText user_id,password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String usernames,passwords;
    String deliboy_id,delivery_mid,delivery_create,deliv_image;
    TextView forgotpassword;
    String devli_name,devli_email,devli_phone,deliv_driveing;
    String newDateStr,time,time1;
            Date currentTime;
    String complete_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    //    setSupportActionBar(toolbar);
        signin=(Button)findViewById(R.id.signin);
        user_id=(TextInputEditText)findViewById(R.id.user_login);
        password=(TextInputEditText)findViewById(R.id.password);
        forgotpassword=findViewById(R.id.forgotpassword);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);
        complete_date=newDateStr+" "+time1;

        //Toast.makeText(this, "cx"+complete_date, Toast.LENGTH_SHORT).show();
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Forgetpassword.class);
                startActivity(intent);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             usernames=user_id.getText().toString();
             passwords=password.getText().toString();
             System.out.println("username"+usernames+","+passwords);
                 LoginValidation();
            }
        });
        sharedPreferences = getSharedPreferences("logindetails", Context.MODE_PRIVATE);

        String uname = sharedPreferences.getString("devli_name", null);
        if (uname != null) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void  LoginValidation(){

        RequestQueue rq = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/login",
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        System.out.println("response"+response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String User_Valid=jsonObject.getString("message");

                            if(User_Valid.equals("User Valid")){


                                JSONArray jsonArray=jsonObject.getJSONArray("data");
                             //   System.out.println("User_Valid"+jsonArray.length());

                                for(int i=0;i<=jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                     deliboy_id=jsonObject1.optString("deliboy_id");
                                    delivery_mid=jsonObject1.optString("delivery_mid");

                                    System.out.println("User_Valid"+deliboy_id);
                                    devli_name=jsonObject1.optString("devli_name");
                                    devli_email=jsonObject1.optString("devli_email");
                                    devli_phone=jsonObject1.optString("devli_phone");
                                    deliv_driveing=jsonObject1.optString("deliv_driveing");
                                    delivery_create=jsonObject1.optString("delivery_create");
                                    deliv_image=jsonObject1.optString("deliv_image");


                                    sharedPreferences=getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                                    editor=sharedPreferences.edit();
                                    editor.putString("deliboy_id",deliboy_id);
                                    editor.putString("delivery_mid",delivery_mid);
                                    editor.putString("devli_name",devli_name);
                                    editor.putString("devli_email",devli_email);
                                    editor.putString("deliv_driveing",deliv_driveing);
                                    editor.putString("devli_phone",devli_phone);
                                    editor.putString("delivery_create",delivery_create);
                                    editor.putString("accountImage",deliv_image);
                                    editor.putString("complete_date",complete_date);
                                    editor.apply();
                                    editor.commit();
                     /*             String devli_name=jsonObject.optString("devli_name");
                                    String devli_email=jsonObject.optString("devli_email");
                                    String devli_phone=jsonObject.optString("devli_phone");
                                    String devli_address=jsonObject.optString("devli_address");
                                    String deliv_image=jsonObject.optString("deliv_image");
                                    String devli_aadharno=jsonObject.optString("devli_aadharno");
                                    String devli_aadhaimage=jsonObject.optString("devli_aadhaimage");
                                    String deliv_driveing=jsonObject.optString("deliv_driveing");
                                    String deliv_drivingimage=jsonObject.optString("deliv_drivingimage");
                                    String deliver_status=jsonObject.optString("deliver_status");
                                    String delivery_create=jsonObject.optString("delivery_create");
                                    String delivery_username=jsonObject.optString("delivery_username");
                                    String delivery_password=jsonObject.optString("delivery_password");
*/
                                    Intent intent=new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }else {
                                Toast.makeText(Login.this,"Enter Correct Details",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    };
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                //   pd.hide();
            }
        })

        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               params.put("username",usernames);
               params.put("password",passwords);
               params.put("login_time",complete_date);
                return params;
            }
        };
        rq.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}



