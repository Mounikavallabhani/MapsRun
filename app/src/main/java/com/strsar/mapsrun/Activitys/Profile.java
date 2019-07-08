package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.strsar.mapsrun.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Profile extends Fragment {
    SharedPreferences sharedPreferences;
    TextView username,mnuber,mailid,drivinglicence,dbid;
    View view ;
    Button logout;
    ImageView accountImage;
    Date currentTime;
    String time,time1;
    String newDateStr;
    String deliboy_id,devli_name,devli_email,deliv_driveing,devli_phone,image,complete_date,delivery_mid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_profile, container, false);
        username=view.findViewById(R.id.username);
        mnuber=view.findViewById(R.id.mailid);
        mailid=view.findViewById(R.id.mnuber);
        accountImage=view.findViewById(R.id.accountImage);

        drivinglicence=view.findViewById(R.id.drivinglicence);
        dbid=view.findViewById(R.id.dbid);

        sharedPreferences=getActivity().getSharedPreferences("logindetails", Context.MODE_PRIVATE);

         deliboy_id=sharedPreferences.getString("deliboy_id",null);
         delivery_mid=sharedPreferences.getString("delivery_mid",null);
         devli_name=sharedPreferences.getString("devli_name",null);
         devli_email=sharedPreferences.getString("devli_email",null);
         deliv_driveing=sharedPreferences.getString("deliv_driveing",null);
         devli_phone=sharedPreferences.getString("devli_phone",null);
         image=sharedPreferences.getString("accountImage",null);
         Date c = Calendar.getInstance().getTime();
         SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);
        complete_date=newDateStr+" "+time1;

        Picasso.with(getContext())

                .load("https://mezotint.com/delivery_boys/"+image)
                .into(accountImage);


        username.setText(devli_name);
        mnuber.setText(devli_phone);
        mailid.setText(devli_email);
        drivinglicence.setText(deliv_driveing);
        dbid.setText(delivery_mid);
        logout=view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();

            }
        });



        return view;

    }
    public void logout(){







        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/deliveryboy_logout_time",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadata"+response);
// Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();

                        try {



                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("Log out Successfully"))

                            {
                                Toast.makeText(getActivity(), "Log out Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Login.class);
                                getActivity().getApplicationContext().getSharedPreferences("logindetails", 0).edit().clear().commit();


                                startActivity(intent);
                            }

// progressdialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    };
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
// TODO Auto-generated method stub
// pd.hide();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("deliboy_id",deliboy_id);
                params.put("logout_time",complete_date);
                return params;
            }
        };
        rq.add(stringRequest);
    }
}
