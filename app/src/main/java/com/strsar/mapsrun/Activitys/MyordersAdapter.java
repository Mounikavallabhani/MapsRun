package com.strsar.mapsrun.Activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import static com.strsar.mapsrun.Activitys.SearchFragment.complete_date;

public class MyordersAdapter extends RecyclerView.Adapter<MyordersAdapter.MyView> {
    Context context;
   // ArrayList<OrderModel> orderModels;
    ArrayList<MounikaModel>mounikaModels;
    View view;
    Dialog dialog1;
    EditText text;
    TextView text_indecation;
    ImageView image_indication;
    String complete_date;
    String booking;
    Button dialogButton;
    String id;
    public MyordersAdapter(Context context, ArrayList<MounikaModel> mounikaModels,String complete_date,String id) {
        this.context = context;
        this.mounikaModels = mounikaModels;
        this.complete_date=complete_date;
        this.id=id;
    }


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.myordersadapter,viewGroup,false);
        MyView myView=new MyView(view);
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {
        final MounikaModel orderModel=mounikaModels.get(i);
        myView.orderid.setText(orderModel.getOrderid());
        myView.celler_phone_number.setText(orderModel.getCeller_phone_number());
        myView.seller_phone_number.setText(orderModel.getSeller_phone_number());
        myView.fromtext.setText(orderModel.getSeller_id());
        myView.totext.setText(orderModel.getUser_id());
        myView.delivery_status.setText(orderModel.getOrder_prepaed());
        System.out.println("order_payment_type"+orderModel.getPayment_type());
        if(orderModel.getPayment_type().equals("COD")){

            myView.bill_amount.setText(""+orderModel.getPat_amount());

        }  myView.call_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+orderModel.getSeller_phone_number()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent chooser  = Intent.createChooser(intent, "Complete Action using..");
                context.startActivity(chooser);

            }
        });


        myView.view_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapsActivity.class);

                intent.putExtra("seller_lat",orderModel.getSeller_lat());
                intent.putExtra("seller_log",orderModel.getSeller_log());
                intent.putExtra("celler_lat",orderModel.getUser_lat());
                intent.putExtra("celler_log",orderModel.getUser_log());
                intent.putExtra("seller",orderModel.getSeller_id());
                intent.putExtra("celler",orderModel.getUser_id());
                intent.putExtra("delivery_status",orderModel.getOrder_prepaed());
                intent.putExtra("booking_id",orderModel.getBooking_id());

                System.out.println("seller_lat"+orderModel.getSeller_lat());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        // bill amount
       myView.linear_bill_amount.setVisibility(View.VISIBLE);
       myView.to_call_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:"+orderModel.getCeller_phone_number()));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               Intent chooser  = Intent.createChooser(intent, "Complete Action using..");
               context.startActivity(chooser);
           }
       });
       myView.booking_id.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog1 = new Dialog(context);
               dialog1.setContentView(R.layout.custom_dailog_box);


              booking= orderModel.getBooking_id();
               // set the custom dialog components - text, image and button
               text = (EditText) dialog1.findViewById(R.id.enter_text);
               image_indication=(ImageView) dialog1.findViewById(R.id.image_indication);
               text_indecation=(TextView)dialog1.findViewById(R.id.text_indecation);
               text.setHint(R.string.order_id);


               dialogButton = (Button) dialog1.findViewById(R.id.buttonOk);
               // if button is clicked, close the custom dialog
               dialogButton.setOnClickListener(new View.OnClickListener() {
                   @Override

                   public void onClick(View v) {
             //          Toast.makeText(context, " nara "+orderModel.getOrder_prepaed(), Toast.LENGTH_SHORT).show();

                       System.out.println("order_prepaed_title"+orderModel.getOrder_prepaed());
                       if(orderModel.getOrder_prepaed().equals("Accepted-Delivery")){
//                           Toast.makeText(context, " nara "+orderModel.getOrder_prepaed(), Toast.LENGTH_SHORT).show();

                           text_indecation.setVisibility(View.GONE);
                           getserverData();

                       }else /*f(orderModel.getOrder_prepaed().equals("Shipped"))*/{
    //                       Toast.makeText(context, " mounika "+orderModel.getOrder_prepaed(), Toast.LENGTH_SHORT).show();
                        //   dialogButton.setVisibility(View.VISIBLE);
                           text.setHint(R.string.order_id_data);
                           text.setVisibility(View.VISIBLE);

                           text.setVisibility(View.VISIBLE);
                           getDeliveryPicked();
                       }
                   }
               });

               dialog1.show();

           }
       });

    }

    @Override
    public int getItemCount() {
        return mounikaModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView orderid,fromtext,fromtext1,totext,totext1,delivery_status,bill_amount;
        TextView view_orders,celler_phone_number,seller_phone_number;
        ImageView call_image,to_call_image ;
        LinearLayout linear_bill_amount;
        TextView booking_id;
        public MyView(@NonNull View itemView) {
            super(itemView);
            orderid=itemView.findViewById(R.id.orderid);
            fromtext=itemView.findViewById(R.id.fromtext);
            fromtext1=itemView.findViewById(R.id.fromtext1);
            totext=itemView.findViewById(R.id.totext);
            totext1=itemView.findViewById(R.id.totext1);
            view_orders=itemView.findViewById(R.id.view_orders);
            delivery_status=itemView.findViewById(R.id.delivery_status);
            seller_phone_number=itemView.findViewById(R.id.seller_phone_number);
            celler_phone_number=itemView.findViewById(R.id.celler_phone_number);
            bill_amount=itemView.findViewById(R.id.bill_amount);
            linear_bill_amount=itemView.findViewById(R.id.linear_bill_amount);
            call_image=itemView.findViewById(R.id.call_image);
            to_call_image=itemView.findViewById(R.id.to_call_image);
            booking_id=itemView.findViewById(R.id.booking_id);
         }
    }

    public void getserverData(){

        System.out.println("complete_date_text "+complete_date+" , "+id+" , "+text.getText().toString());

      //  Toast.makeText(context, "nara complete "+complete_date+"  , "+text.getText().toString(), Toast.LENGTH_SHORT).show();
        RequestQueue rq = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/order_pick",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("order_picked_data"+response);
        //         Toast.makeText(context, "nara response"+response, Toast.LENGTH_SHORT).show();

                        try {


                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("order picked")){

                                text.setVisibility(View.GONE);
                                image_indication.setImageResource(R.drawable.ic_success);
                                text_indecation.setVisibility(View.VISIBLE);
                                dialogButton.setVisibility(View.INVISIBLE);
                                text_indecation.setText("Success");

                         //       Thread.sleep(1000);
                            //    dialog1.dismiss();
                            }else {
                                text_indecation.setVisibility(View.VISIBLE);
                                text_indecation.setText("Failed");
                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    };
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
     //           Toast.makeText(context, "Server Error  "+arg0, Toast.LENGTH_SHORT).show();
                System.out.println("server_Error"+arg0);

// TODO Auto-generated method stub
// pd.hide();
            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("book_id",text.getText().toString());
                params.put("del_boy_id",id);
                params.put("pick_status","Shipped");
                params.put("order_piced",complete_date);


                return params;
            }
        };
        rq.add(stringRequest);
    }


    public void getDeliveryPicked(){

       System.out.println("booking_id_means_order_id_here"+booking);

        RequestQueue rq = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/pincheck",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadata"+response);
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");

                            Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();

                            if(message.equals("Curret Pin")) {
                                //      progressDialog.dismiss();

                                Toast.makeText(context, "Task Completed", Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                            }else{
                                Toast.makeText(context, "Wrong pin", Toast.LENGTH_SHORT).show();

                            }
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
                params.put("order_pin", text.getText().toString());
                params.put("book_id",booking);
                params.put("order_comp_date",complete_date);
                return params;
            }
        };
        rq.add(stringRequest);
    }
}