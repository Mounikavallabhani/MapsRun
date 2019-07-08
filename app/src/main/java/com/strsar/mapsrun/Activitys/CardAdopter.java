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

public class CardAdopter extends RecyclerView.Adapter<CardAdopter.MyViewData> {
    Context context;
    ArrayList<CardModel> orderModel;
    View view;
    SharedPreferences sharedPreferences;
    String id;
    String complete_date;
    String booking_id,payment_id;
    int i;
    public CardAdopter(Context context, ArrayList<CardModel> orderModel, String id,String complete_date) {
        this.context = context;
        this.orderModel = orderModel;
        this.sharedPreferences = sharedPreferences;
        this.id = id;
        this.complete_date=complete_date;
        System.out.println("deliboy_id_narasimha"+id);
    }

    @NonNull
    @Override
    public CardAdopter.MyViewData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(R.layout.ordersadapter,viewGroup,false);
        CardAdopter.MyViewData myView=new CardAdopter.MyViewData(view,context,orderModel);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdopter.MyViewData myViewData,final int i) {
     final    CardModel orderModels=orderModel.get(i);
        //  myView.orderid.setText(orderModel.orderid);
        System.out.print("order_details"+orderModels.getSeller_id());
        myViewData.caller_address.setText(""+orderModels.getUser_id());
        myViewData.seller_address.setText(""+orderModels.getSeller_id());
        myViewData.orderid.setText(""+orderModels.getOrderid());
        myViewData.viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapsActivity.class);
                intent.putExtra("seller_lat",orderModels.getSeller_lat());
                intent.putExtra("seller_log",orderModels.getSeller_log());
                intent.putExtra("celler_lat",orderModels.getUser_lat());
                intent.putExtra("celler_log",orderModels.getUser_log());
                intent.putExtra("seller",orderModels.getSeller_id());
                intent.putExtra("celler",orderModels.getUser_id());
                intent.putExtra("booking_id",orderModels.getBooking_id());
                context.startActivity(intent);
            }
        });

     /*   myViewData.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             //   Toast.makeText(context,"Wrong Details",Toast.LENGTH_SHORT).show();



            }
        });*/
    }

    @Override
    public int getItemCount() {
        return orderModel.size();
    }
    public class MyViewData extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView orderid,seller_address,caller_address;
        TextView accept,viewdata;
        Context context;
        ArrayList<CardModel>orderModel;
        public MyViewData(@NonNull View itemView,final  Context context,ArrayList<CardModel>orderModel) {
            super(itemView);
            this.context=context;
            this.orderModel=orderModel;
            orderid=itemView.findViewById(R.id.orderid);
            seller_address=itemView.findViewById(R.id.seller_address);
            caller_address=itemView.findViewById(R.id.caller_address);

            accept=itemView.findViewById(R.id.accept);
           // viewdata=itemView.findViewById(R.id.view);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
                     i=getAdapterPosition();
              CardModel orderModels=orderModel.get(i);
            booking_id=  orderModels.getBooking_id();
            payment_id=  orderModels.getOrder_paymentid();

            //      System.out.println("order_final_data"+orderModels.getOrder_paymentid()+","+orderModels.getBooking_id());
            System.out.println("order_final_data"+id);

            RequestQueue rq = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    "https://mezotint.com/api/order_accept",
                    new Response.Listener<String>() {

                        public void onResponse(String response) {

                           // Toast.makeText(context," "+response,Toast.LENGTH_SHORT).show();
                            System.out.println("response_orderAdopter"+response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String User_Valid=jsonObject.getString("message");
                                if(User_Valid.equals("order accepted")){
                                    Toast.makeText(context,"Order Has Been Acepted",Toast.LENGTH_SHORT).show();
                                    orderModel.remove(i);
                                    notifyItemRemoved(i);
                                }else {
                                    Toast.makeText(context,"Wrong Details",Toast.LENGTH_SHORT).show();
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
                    params.put("deliboy_id",id);
                    params.put("book_id",booking_id);
                    params.put("order_paymentid",payment_id);
                    params.put("accept_status","delivery acepted");
                    params.put("order_acc_date",complete_date);
                    return params;
                }
            };
            rq.add(stringRequest);




        }
    }
}
