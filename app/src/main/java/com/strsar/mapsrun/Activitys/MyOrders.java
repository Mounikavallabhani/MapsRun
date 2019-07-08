package com.strsar.mapsrun.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class MyOrders extends AppCompatActivity {
    RecyclerView myorders;
    ArrayList<OrderModel> orderModels;
    MyordersAdapter myordersAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        myorders = findViewById(R.id.myorders);

        getserverData();
    }
    public void getserverData(){

        RequestQueue rq = Volley.newRequestQueue(MyOrders.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/deliveryaddress",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("response_get_magsine"+response);

                        try {
                            gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
                            myorders.setLayoutManager(gridLayoutManager);
                            orderModels=new ArrayList<>();

                            JSONObject jsonObject=new JSONObject(response);

                       /*   JSONArray jsonArray=jsonObject.getJSONArray("data");
                          for(int i=1;i<jsonArray.length();i++){
                             JSONObject jsonObject1=jsonArray.getJSONObject(i);
                              String sellers=jsonObject1.getString("sellers");
                              String users=jsonObject1.getString("users");
                              orderModels.add(new OrderModel("110011",sellers," TS_23",users,"Hyd TS_23"));
                          }
                            myordersAdapter=new MyordersAdapter(getApplicationContext(),orderModels);
                            myorders.setAdapter(myordersAdapter);

                       */ } catch (JSONException e) {
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

                return params;
            }
        };
        rq.add(stringRequest);

    }
}
