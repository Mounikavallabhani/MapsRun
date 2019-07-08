package com.strsar.mapsrun.Activitys;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.strsar.mapsrun.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Orders extends Fragment {
    LinearLayout startdate, enddate;
    public DatePickerDialog fromDatePickerDialog;
    public DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Button confirmsbs;
    TextView startdatetext, enddatetext;
    CheckBox alternativeday,everyday;
    TextView applycoupon;
    TextView dailogtext;
    ImageView location;
    TextView locationname;
    SharedPreferences sharedPreferences,sharedPreferences1,sharedPreferences2;
    SharedPreferences.Editor editor,editor1,editor2;
    String daysforrecharge;
    private TextView change;

    private String latitude, longitude;

    Geocoder geocoder;
    List<Address> addresses;
    public static String result;

    public static String subcribe="";
    Date startDatefinal,endDatefinal;
    String Dayfinding;
    public long days;
    GridLayoutManager gridLayoutManager;
    DeliveryBoyAdapter deliveryBoyAdapter;
    ArrayList<DeliveryBoyModel>deliveryBoyModels;


    Date milliTime1;
    Calendar newCalendar1;
    int dateintime1;

    Date milliTime123;
    Calendar newCalendar123;
    int dateintime123;
    View view;
    RecyclerView deliveryboy;
    String id,delivery_create;
    String book_id,sellers,users;
    LinearLayout l1,l2;
    CustomProgressDialog progressdialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public Orders() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        deliveryboy=view.findViewById(R.id.deliveryboy);

        sharedPreferences=getActivity().getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        id=sharedPreferences.getString("deliboy_id",null);
        System.out.println("delivery_id_data"+id);

        delivery_create=sharedPreferences.getString("delivery_create",null);
        System.out.println("date"+delivery_create);
        System.out.println("id"+id);
        l1=view.findViewById(R.id.l1);
        l2=view.findViewById(R.id.l2);

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);



        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        startdatetext = view.findViewById(R.id.startdatetext);
        enddatetext = view.findViewById(R.id.enddatetext);
        startdate=view.findViewById(R.id.startdate);
        enddate=view.findViewById(R.id.enddate);
        getStartDate();
        getEndDate();
     //   Toast.makeText(getActivity(), ""+startdatetext.getText().toString(), Toast.LENGTH_SHORT).show();
        if(startdatetext.getText().toString().equals(""))
        {
       //     Toast.makeText(getActivity(), "asdfghj"+startdatetext.getText().toString(), Toast.LENGTH_SHORT).show();
            String date=delivery_create.substring(0,11);
            startdatetext.setText(date);
            System.out.println("startdatetext"+startdatetext.getText().toString());
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

            String newDateStr = postFormater.format(c);
            enddatetext.setText(newDateStr);
            System.out.println("enddate"+enddatetext.getText().toString());

            progressdialog = new CustomProgressDialog(getActivity());
            progressdialog.show();

            getserverData();

        }

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromDatePickerDialog.show();
// fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


/* if(dateintime1>=21){

fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+ 2*24*60*60*1000l);

}else {
fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+ 1*24*60*60*1000l);

}*/
/* fromDatePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
fromDatePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
*/
                fromDatePickerDialog.getDatePicker().setSelected(false);

                fromDatePickerDialog.show();


                System.out.println("JDDKJ" + System.currentTimeMillis());
// System.out.printf( fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()));
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/* if(dateintime123>=21){

toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+ 3*24*60*60*1000l);

}else {
toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+ 2*24*60*60*1000l);

}
*/
                toDatePickerDialog.getDatePicker().setSelected(false);

                toDatePickerDialog.show();



            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getserverData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }







    private void getStartDate() {
        newCalendar1 = Calendar.getInstance();
// newCalendar1.add(Calendar.DAY_OF_YEAR, 1);



        milliTime1 = newCalendar1.getTime();
// Toast.makeText(Subscription.this,""+milliTime1,Toast.LENGTH_LONG).show();
        System.out.println("sdsss"+milliTime1);
        String time=""+milliTime1;
        String times=time.substring(11,13);
        dateintime1=Integer.parseInt(times);
        System.out.print("fordatemine"+dateintime1);
// Toast.makeText(Subscription.this,"one "+times,Toast.LENGTH_LONG).show();
        System.out.println("sdsssdffdsdf"+times);
        if(dateintime1>=21){
            newCalendar1.add(Calendar.DAY_OF_YEAR, 2);

        }else {
            newCalendar1.add(Calendar.DAY_OF_YEAR, 1);

        }
        fromDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startdatetext.setText(dateFormatter.format(newDate.getTime()));

            }
        }, newCalendar1.get(Calendar.YEAR), newCalendar1.get(Calendar.MONTH), newCalendar1.get(Calendar.DAY_OF_MONTH));

    }




    public void getEndDate() {
        newCalendar123 = Calendar.getInstance();
// newCalendar.add(Calendar.DAY_OF_YEAR, 1);

        newCalendar123 = Calendar.getInstance();
        milliTime123 = newCalendar123.getTime();
// Toast.makeText(Subscription.this,""+milliTime123,Toast.LENGTH_LONG).show();
        System.out.println("sdsss"+milliTime123);
        String time=""+milliTime123;
        String times=time.substring(11,13);
        dateintime123=Integer.parseInt(times);
// Toast.makeText(Subscription.this,"one "+times,Toast.LENGTH_LONG).show();
        System.out.println("sdsssdffdsdf"+times);
        if(dateintime123>=21){
            newCalendar123.add(Calendar.DAY_OF_YEAR, 2);

        }else {
            newCalendar123.add(Calendar.DAY_OF_YEAR, 1);

        }
        toDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                enddatetext.setText(dateFormatter.format(newDate.getTime()));
                getserverData();

            }
        }, newCalendar123.get(Calendar.YEAR), newCalendar123.get(Calendar.MONTH), newCalendar123.get(Calendar.DAY_OF_MONTH));


    }

    public void getserverData(){







        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/api/deliveryboyorders",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("orders_history_data"+response);
// Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();

                        try {

                            gridLayoutManager=new GridLayoutManager(getActivity(),1);
                            deliveryboy.setLayoutManager(gridLayoutManager);
                            deliveryBoyModels=new ArrayList<>();

                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("NO orders"))
                            {
                                l2.setVisibility(View.VISIBLE);
                                l1.setVisibility(View.GONE);
                                progressdialog.dismiss();

                            }
                            else {
                              /*  l1.setVisibility(View.VISIBLE);
                                l2.setVisibility(View.GONE);
*/
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                System.out.println("orders_history_data_narasimha_data"+jsonArray);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    book_id = jsonObject1.optString("book_id");
// Toast.makeText(getActivity(), ""+book_id, Toast.LENGTH_SHORT).show();
                                    sellers = jsonObject1.optString("sellers");
// Toast.makeText(getActivity(), ""+sellers, Toast.LENGTH_SHORT).show();
                                    users = jsonObject1.optString("users");
                // Toast.makeText(getActivity(), ""+users, Toast.LENGTH_SHORT).show();

                                    System.out.println("response_get_magsine1111" + sellers);

                                    deliveryBoyModels.add(new DeliveryBoyModel(book_id, sellers, users));
                                }
                                System.out.println("get_size" + deliveryBoyModels.size());
                                deliveryBoyAdapter = new DeliveryBoyAdapter(getActivity(), deliveryBoyModels);
                                deliveryboy.setAdapter(deliveryBoyAdapter);
                                progressdialog.dismiss();

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
                params.put("from date",startdatetext.getText().toString());
                params.put("to date",enddatetext.getText().toString());
                params.put("db_id",id);

                return params;
            }
        };
        rq.add(stringRequest);
    }

}