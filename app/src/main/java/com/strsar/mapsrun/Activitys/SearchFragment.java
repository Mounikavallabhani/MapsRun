package com.strsar.mapsrun.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.strsar.mapsrun.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment  {
  LinearLayout view_orders;
  public  static  TextView display_current_location;
    LocationManager locationManager;

    // location
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private LocationAddressResultReceiver addressResultReceiver;

    private Location currentLocation;
    private LocationCallback locationCallback;
    Button change_address;
    TextView changeadress,changeadress1,changeadress2,changeadress3,changeadress4;
    TextView chngesubmit;
    AlertDialog.Builder alert;
    AlertDialog dialog;
    String latitude="";
    String longitude="";
    SeekBar customSeekBar;
    int progressChangedValue=0;
    Switch onoff;
    String newDateStr;
    Date currentTime;
    String time,time1;
    SharedPreferences sharedPreferences,sharedPreferences1,sharedPreferences2;
    SharedPreferences.Editor editor,editor2;
    String loginok;
    String profilevalue,id;
    int seekbarvalue;
    public  static  String complete_date;
    public SearchFragment() {
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view_orders=(LinearLayout) view.findViewById(R.id.view_orders);
        change_address=(Button)view.findViewById(R.id.change_address);
      display_current_location=(TextView)view.findViewById(R.id.display_current_location);

        customSeekBar =(SeekBar)view.findViewById(R.id.customSeekBar);

        sharedPreferences2=getActivity().getSharedPreferences("seekbar",Context.MODE_PRIVATE);
        progressChangedValue=sharedPreferences2.getInt("seekbarvalue",0);
        customSeekBar.setProgress(progressChangedValue);//seekbarvalue
        //Toast.makeText(getActivity(), "etwte"+seekbarvalue, Toast.LENGTH_SHORT).show();




        onoff = (Switch)view.findViewById(R.id.onoff);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);

        complete_date=newDateStr+time1;

        sharedPreferences1=getActivity().getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        id=sharedPreferences1.getString("deliboy_id",null);


        sharedPreferences=getActivity().getSharedPreferences("forlogin", Context.MODE_PRIVATE);
        loginok=sharedPreferences.getString("login",null);
        //Toast.makeText(getActivity(), ""+loginok, Toast.LENGTH_SHORT).show();
        System.out.println("loginok"+loginok);


        if(loginok == null || loginok == "")
        {
           // Toast.makeText(getActivity(), "first", Toast.LENGTH_SHORT).show();
            onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
// The toggle is enabledo
                        //Toast.makeText(getActivity(), "haii", Toast.LENGTH_SHORT).show();
                        Logindeliveryboy();

                    } else {
                        Logoutdeliveryboy();
                       // Toast.makeText(getActivity(), "off", Toast.LENGTH_SHORT).show();
// The toggle is disabled

                    }

                }
            });


        }
        else if(loginok.equals("ok") )
        {
           // Toast.makeText(getActivity(), "Second", Toast.LENGTH_SHORT).show();
            onoff.setChecked(true);


        }
        else
        {
           // Toast.makeText(getActivity(), "Third", Toast.LENGTH_SHORT).show();
            onoff.setChecked(false);

        }

        onoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
// The toggle is enabledo
                   // Toast.makeText(getActivity(), "haii", Toast.LENGTH_SHORT).show();
                    Logindeliveryboy();





                } else {
// The toggle is disabled
                    Logoutdeliveryboy();
                   // Toast.makeText(getActivity(), "off", Toast.LENGTH_SHORT).show();

                }
            }
        });

// perform seek bar change listener event used for getting the progress value
        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
// TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                sharedPreferences2=getActivity().getSharedPreferences("seekbar",Context.MODE_PRIVATE);
                editor2=sharedPreferences2.edit();
                editor2.putInt("seekbarvalue",progressChangedValue);
                editor2.apply();
                editor2.commit();
                Toast.makeText(getActivity(), "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });



      view_orders.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(!(latitude.equals("")&&longitude.equals(""))){
                  System.out.println("Range"+progressChangedValue);
                  Intent intent=new Intent(getActivity(),OrdersActivity.class);
                      intent.putExtra("latitude",latitude);
                      intent.putExtra("longitude",longitude);
                      intent.putExtra("rang",""+progressChangedValue);
                  startActivity(intent);
              }else {
                  Toast.makeText(getActivity(),"Internet Connection is slow  please wait",Toast.LENGTH_SHORT).show();
              }

          }
      });

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        startLocationUpdates();

        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.changelocation, null);
                changeadress = alertLayout.findViewById(R.id.flatno);
                changeadress1 = alertLayout.findViewById(R.id.area);
                changeadress2 = alertLayout.findViewById(R.id.city);
                changeadress3=alertLayout.findViewById(R.id.state);
                changeadress4=alertLayout.findViewById(R.id.landmark);
                chngesubmit=alertLayout.findViewById(R.id.chngesubmit);


                alert = new AlertDialog.Builder(getActivity());

                alert.setView(alertLayout);
                // alert.setCancelable(false);
                dialog = alert.create();

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                chngesubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(changeadress.getText().toString().isEmpty())
                        {
                            Toast.makeText(getActivity(), "Please provide flat no ", Toast.LENGTH_SHORT).show();
                        }
                        else if(changeadress1.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please provide area ", Toast.LENGTH_SHORT).show();


                        }else if(changeadress2.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please provide city ", Toast.LENGTH_SHORT).show();

                        }
                        else if(changeadress3.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please provide state ", Toast.LENGTH_SHORT).show();

                        }
                        else if(changeadress4.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please provide landmark ", Toast.LENGTH_SHORT).show();

                        } else {
                            display_current_location.setText(changeadress.getText().toString()+" , "+changeadress1.getText().toString()+" , "+changeadress2.getText().toString()+" , "+changeadress3.getText().toString()+" , "+changeadress4.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return view;
    }

    public void Logindeliveryboy(){

        System.out.println("dateprofile"+newDateStr);
        System.out.println("timeprofile"+time1);






        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/profile_active",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadataprofile"+response);
// Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");

                            if(message.equals("data active"))
                            {

                                Toast.makeText(getActivity(), "data inserted", Toast.LENGTH_SHORT).show();
                                String login="ok";
                                profilevalue=jsonObject.getString("Profile_active_id");

                                sharedPreferences=getActivity().getSharedPreferences("forlogin", Context.MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                editor.putString("profilevalue",profilevalue);
                                editor.putString("login",login);
                                editor.apply();
                                editor.commit();


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
                params.put("profile_active",newDateStr+ time1);
                params.put("deliboy_id",id);


                return params;
            }
        };
        rq.add(stringRequest);
    }
    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
    }

    public void Logoutdeliveryboy(){

        System.out.println("dateprofile"+newDateStr);
        System.out.println("timeprofile"+time1);
        System.out.println("id"+id);


        sharedPreferences=getActivity().getSharedPreferences("forlogin", Context.MODE_PRIVATE);
        profilevalue=sharedPreferences.getString("profilevalue",null);
        System.out.println("profilevalue"+profilevalue);




        RequestQueue rq = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/profile_inactive",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadataprofile"+response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("data Inactived"))
                            {
                                Toast.makeText(getActivity(), "data updated", Toast.LENGTH_SHORT).show();
                                String login="cancel";
                                sharedPreferences=getActivity().getSharedPreferences("forlogin", Context.MODE_PRIVATE);
                                editor=sharedPreferences.edit();
                                editor.putString("login",login);
                                editor.apply();
                                editor.commit();
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
                params.put("profile_inactive",newDateStr+ time1);
                params.put("deliboy_id",id);
                params.put("Profile_active_id",profilevalue);


                return params;
            }
        };
        rq.add(stringRequest);
    }
    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(getActivity(),
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(getActivity(), GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        getActivity().startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else {
                    Toast.makeText(getActivity(), "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                //Last Location can be null for various reasons
                //for example the api is called first time
                //so retry till location is set
                //since intent service runs on background thread, it doesn't block main thread
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(getActivity(),
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");
             latitude=resultData.getString("latitude");
             longitude=resultData.getString("langutude");
            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
     //   Toast.makeText(getActivity(),""+currentAdd,Toast.LENGTH_SHORT).show();
        display_current_location.setText(""+currentAdd);
        sharedPreferences1=getActivity().getSharedPreferences("complete_address", Context.MODE_PRIVATE);
        editor2=sharedPreferences1.edit();
        editor2.putString("complete_address",display_current_location.getText().toString());
        editor2.apply();
        editor2.commit();
    }


    @Override
    public void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
