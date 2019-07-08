package com.strsar.mapsrun.Activitys;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DealsFragment extends Fragment {
    RecyclerView myorders;
    ArrayList<OrderModel> orderModels;
    ArrayList<MounikaModel>mounikaModels;
    MyordersAdapter myordersAdapter;
    GridLayoutManager gridLayoutManager;
    String sellers,users;
    String lattitude,longitude,Range;
    String seller_lat,seller_log,user_log,user_lat,book_id,order_paymentid,seller_phone_number,celler_phone_number;
    SharedPreferences sharedPreferences;
    String id;
    CustomProgressDialog progressdialog;
    TextView getalldata;
    String order_prepaed,payment_type,pat_amount;
    private Handler handler;
    private Runnable handlerTask;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String newDateStr;
    Date currentTime;
    String time,time1;
    String complete_date;
    public DealsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_deals, container, false);
        myorders=view.findViewById(R.id.myorders);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        getalldata=(TextView)view.findViewById(R.id.getalldata);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);

        complete_date=newDateStr+" "+time1;

        sharedPreferences=getActivity().getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        id=sharedPreferences.getString("deliboy_id",null);
        progressdialog = new CustomProgressDialog(getContext());
        progressdialog.show();

        getalldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), GetData.class);
                intent.putExtra("Property", mounikaModels);
                startActivity(intent);

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Acceptingorders();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Acceptingorders();
        return view;
    }


    public void Acceptingorders(){

        System.out.println("deliboy_id"+id);
       // Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();

        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/delivery_boy_accepted_orders",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("response_get_magsine"+response);
                       // Toast.makeText(getActivity(), "narasimha"+response, Toast.LENGTH_SHORT).show();


                        try {

                            gridLayoutManager=new GridLayoutManager(getActivity(),1);
                            myorders.setLayoutManager(gridLayoutManager);
                            //orderModels=new ArrayList<>();
                            mounikaModels=new ArrayList<>();

                            JSONObject jsonObject=new JSONObject(response);

                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                           // Toast.makeText(getActivity(),  ""+jsonArray, Toast.LENGTH_SHORT).show();

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                book_id=jsonObject1.getString("book_id");
                                order_paymentid=jsonObject1.getString("order_paymentid");
                                sellers=""+jsonObject1.getString("sellers");
                                users=""+jsonObject1.getString("users");
                                seller_lat=""+jsonObject1.getString("seller_lat");
                                seller_log=""+jsonObject1.getString("seller_log");
                                user_lat=""+jsonObject1.getString("user_lat");
                                user_log=""+jsonObject1.getString("user_log");
                                seller_phone_number=jsonObject1.getString("seller_phone");
                                celler_phone_number=jsonObject1.getString("user_phone");
                                order_prepaed=jsonObject1.optString("order_prepaed");
                                payment_type=jsonObject1.optString("payment_type");
                                pat_amount=jsonObject1.optString("pat_amount");
                                System.out.println("response_get_magsine1111"+sellers);

                               // orderModels.add(new OrderModel("110011",""+sellers,""+users,""+seller_lat,""+seller_log,""+user_lat,""+user_log,book_id,order_paymentid));
                                mounikaModels.add(new MounikaModel(book_id,""+sellers,""+users,""+seller_lat,""+seller_log,""+user_lat,""+user_log,book_id,order_paymentid,seller_phone_number,celler_phone_number,order_prepaed,payment_type,pat_amount));
                            }
                             System.out.println("get_size_dealsFragment"+mounikaModels.size());
                           // myordersAdapter=new MyordersAdapter(getActivity(),orderModels);
                            myordersAdapter=new MyordersAdapter(getActivity(),mounikaModels,complete_date,id);
                            myorders.setAdapter(myordersAdapter);
                            progressdialog.dismiss();


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
                params.put("deliboy_id",id);

                return params;
            }
        };
        rq.add(stringRequest);
    }

}

