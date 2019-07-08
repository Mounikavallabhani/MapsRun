package com.strsar.mapsrun.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.strsar.mapsrun.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Forgetpassword extends AppCompatActivity {
    Button flogin,fgetpwd;
    EditText fmobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        flogin=findViewById(R.id.flogin);
        fgetpwd=findViewById(R.id.fgetpwd);
        fmobile=findViewById(R.id.fmobile);
        flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        fgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getserverData();

            }
        });
    }
    public void getserverData(){

        RequestQueue rq = Volley.newRequestQueue(Forgetpassword.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/forget_password",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("response_get_magsine"+response);

                        try {


                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("Valid Mobile required"));
                            {
                                Toast.makeText(Forgetpassword.this, "Valid Mobile required", Toast.LENGTH_SHORT).show();
                            }
                            if(message.equals("Password sent to your registerd mobile number.")){
                                Toast.makeText(Forgetpassword.this, "Password sent to your registerd mobile number.", Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(getApplicationContext(),Login.class);
                                   startActivity(intent);
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
                params.put("mobile",fmobile.getText().toString());


                return params;
            }
        };
        rq.add(stringRequest);
    }
}
