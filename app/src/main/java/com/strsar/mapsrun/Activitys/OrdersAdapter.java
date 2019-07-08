package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.strsar.mapsrun.MapsActivity;
import com.strsar.mapsrun.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyView> {
    Context context;
    ArrayList<OrderModel>orderModel;
    View view;
    SharedPreferences sharedPreferences;
    String id;
    String complete_date;
    int position;
    OrderModel orderModels;
    int i=0;
    public OrdersAdapter(Context context, ArrayList<OrderModel> orderModels,String id,String complete_date) {
        this.context = context;
        this.orderModel = orderModels;
        this.id=id;
        this.complete_date=complete_date;

    }


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
         view=layoutInflater.inflate(R.layout.ordersadapter,viewGroup,false);
        MyView myView=new MyView(view,context,orderModel);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
        OrderModel orderModels=orderModel.get(i);
      //  myView.orderid.setText(orderModel.orderid);
        System.out.print("order_details"+orderModels.getSeller_id());
        myView.caller_address.setText(""+orderModels.getUser_id());
        myView.seller_address.setText(""+orderModels.getSeller_id());
        myView.orderid.setText(""+orderModels.getOrderid());
    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }

    public class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView orderid,seller_address,caller_address;
        TextView accept,rejected;
        Context context;
        ArrayList<OrderModel>orderModel;
        public MyView(@NonNull View itemView,final  Context context,ArrayList<OrderModel>orderModel) {
            super(itemView);
            this.context=context;
            this.orderModel=orderModel;
            orderid=itemView.findViewById(R.id.orderid);
            seller_address=itemView.findViewById(R.id.seller_address);
            caller_address=itemView.findViewById(R.id.caller_address);

            accept=itemView.findViewById(R.id.accept);
            //rejected=itemView.findViewById(R.id.rejected);
            itemView.setClickable(true);
            accept.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
            orderModels = orderModel.get(position);
            System.out.println("order_accept_url" + id + ", " + orderModels.getBooking_id() + "," + orderModels.getOrder_paymentid()+" , "+complete_date);

            if (v == accept) {

                System.out.println("order_accept_url_accept" + id + ", " + orderModels.getBooking_id() + "," + orderModels.getOrder_paymentid()+" , "+complete_date);


                    RequestQueue rq = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "https://mezotint.com/api/order_accept",
                            new Response.Listener<String>() {

                                public void onResponse(String response) {

                                    // Toast.makeText(context," "+response,Toast.LENGTH_SHORT).show();
                                    System.out.println("response_orderAdopter" + response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String User_Valid = jsonObject.getString("message");
                                        if (User_Valid.equals("order accepted")) {
                                            Toast.makeText(context, "Order Has Been Acepted", Toast.LENGTH_SHORT).show();
                                            orderModel.remove(position);
                                            notifyItemRemoved(position);

                                        } else if(User_Valid.equals("order Already accepted")){
                                            Toast.makeText(context, "Order Already accepted", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(context, "Wrong Details", Toast.LENGTH_SHORT).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                ;
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError arg0) {
                            // TODO Auto-generated method stub
                            //   pd.hide();
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("deliboy_id", id);
                            params.put("book_id", orderModels.getBooking_id());
                            params.put("order_paymentid", orderModels.getOrder_paymentid());
                            params.put("accept_status", "Accepted-Delivery");
                            params.put("order_acc_date", complete_date);

                            return params;
                        }
                    };
                    rq.add(stringRequest);



            }
        }
     }
}
