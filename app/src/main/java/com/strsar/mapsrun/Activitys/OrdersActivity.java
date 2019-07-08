package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.strsar.mapsrun.Activitys.SearchFragment.complete_date;

public class OrdersActivity extends AppCompatActivity {
    RecyclerView orders;
    ArrayList<OrderModel>orderModels;
    OrdersAdapter ordersAdapter;
    GridLayoutManager gridLayoutManager;
    String sellers,users;
    String lattitude,longitude,Range;
    String seller_lat,seller_log,user_log,user_lat,book_id,order_paymentid;
    SharedPreferences sharedPreferences;
    String id;
    CustomProgressDialog progressdialog;
    TextView no_data_details;
    private Handler handler;
    private Runnable handlerTask;
    Date currentTime;
    String newDateStr,time,time1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        orders=findViewById(R.id.orders);
        no_data_details=findViewById(R.id.no_data_details);


       // gridLayoutManager=new GridLayoutManager(getApplication(),1);
       // orders.setLayoutManager(gridLayoutManager);
        lattitude=getIntent().getStringExtra("latitude");
        longitude=getIntent().getStringExtra("longitude");
        Range=getIntent().getStringExtra("rang");
        System.out.println("intent_data_getting"+Range+","+lattitude+" ,"+longitude);
        sharedPreferences=getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        id=sharedPreferences.getString("deliboy_id",null);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);
        complete_date=newDateStr+" "+time1;
        Toast.makeText(OrdersActivity.this, "cx"+complete_date, Toast.LENGTH_SHORT).show();

        progressdialog = new CustomProgressDialog(this);
        progressdialog.show();


        getserverData();
    }


    public void getserverData(){

        RequestQueue rq = Volley.newRequestQueue(OrdersActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/deliveryaddress",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("response_get_magsine"+response);

                        try {

                            gridLayoutManager=new GridLayoutManager(getApplication(),1);
                            orders.setLayoutManager(gridLayoutManager);
                            orderModels=new ArrayList<>();

                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equals("400")){
                                JSONArray jsonArray=jsonObject.getJSONArray("data");

                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    book_id=jsonObject1.getString("book_id");
                                    order_paymentid=jsonObject1.getString("order_paymentid");
                                    sellers=jsonObject1.optString("sellers");
                                    users=jsonObject1.optString("users");
                                    seller_lat=jsonObject1.optString("seller_lat");
                                    seller_log=jsonObject1.optString("seller_log");
                                    user_lat=jsonObject1.optString("user_lat");
                                    user_log=jsonObject1.optString("user_log");
                                    System.out.println("response_get_magsine1111"+sellers);

                                    orderModels.add(new OrderModel(book_id,""+sellers,""+users,""+seller_lat,""+seller_log,""+user_lat,""+user_log,book_id,order_paymentid));
                                }
                                System.out.println("orders_size_url"+orderModels.size());
                                ordersAdapter=new OrdersAdapter(getApplicationContext(),orderModels,id,complete_date);
                                orders.setAdapter(ordersAdapter);
                                progressdialog.dismiss();
                                no_data_details.setVisibility(View.GONE);
                                orders.setVisibility(View.VISIBLE);

                            }else {
                                String message=jsonObject.getString("message");

                                orders.setVisibility(View.GONE);
                                no_data_details.setVisibility(View.VISIBLE);
                                no_data_details.setText(message);
                                progressdialog.dismiss();

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
                params.put("latitute",lattitude);
                params.put("longitude",longitude);
                params.put("rang",Range);
                params.put("deliboy_id",id);
                return params;
            }
        };
        rq.add(stringRequest);
    }
}
