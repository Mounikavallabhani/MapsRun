package com.strsar.mapsrun;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.strsar.mapsrun.Activitys.DeliveryBoyAdapter;
import com.strsar.mapsrun.Activitys.DeliveryBoyModel;
import com.strsar.mapsrun.Activitys.GetData;
import com.strsar.mapsrun.directionhelpers.FetchURL;
import com.strsar.mapsrun.directionhelpers.TaskLoadedCallback;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.internal.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
    private GoogleMap mMap;
    private MarkerOptions place1, place2, place3, place4, place5, place6;
    public static Button getDirection, distance_click, time_click;
    private Polyline currentPolyline;

    List<MarkerModel> markerModelList;
    Double seller_log, seller_lat, celler_log, celler_lat;


    String seller, celler,delivery_status;
    LocationManager locationManager;
    double latitude, longitude;
    private Handler handler;
    private Runnable handlerTask;
    String result;
    Marker marker;
    String url;
    String destination;
    String edittext,booking_id;
    Dialog dialog;
    Dialog dialog1;
    EditText text;
    String newDateStr;
    Date currentTime;
    String time,time1;
    public  static  String complete_date;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getDirection = findViewById(R.id.button_click);
        time_click = findViewById(R.id.time_click);
        distance_click = findViewById(R.id.distance_click);

        seller_lat = Double.parseDouble(getIntent().getStringExtra("seller_lat"));
        seller_log = Double.parseDouble(getIntent().getStringExtra("seller_log"));
        celler_lat = Double.parseDouble(getIntent().getStringExtra("celler_lat"));
        celler_log = Double.parseDouble(getIntent().getStringExtra("celler_log"));
        seller = getIntent().getStringExtra("seller");
        celler = getIntent().getStringExtra("celler");
        delivery_status=getIntent().getStringExtra("delivery_status");
        booking_id=getIntent().getStringExtra("booking_id");

    //    Toast.makeText(MapsActivity.this, "" + seller_log, Toast.LENGTH_LONG).show();
        System.out.println("seller_lat_seller_log" + seller_lat + ", " + seller_log + ", " + celler_lat + " , " + celler_lat+" ,"+seller+", "+celler);

        System.out.println();

        //27.658143,85.3199503
        //27.667491,85.3208583

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");

        newDateStr = postFormater.format(c);
        currentTime = Calendar.getInstance().getTime();
        time=String.valueOf(currentTime);
        time1=time.substring(11,19);

        complete_date=newDateStr+" "+time1;




        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        getLocation();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");



/*
         CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 13);
                mMap.animateCamera(yourLocation);
*/

/*
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    System.out.println("adresses" + addresses);

                    Address address = addresses.get(0);
                    result = address.getAddressLine(0);
                    //       Toast.makeText(MapsActivity.this,"ohhh       "+result,Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                place1 = new MarkerOptions().position(new LatLng(latitude,longitude)).title(result) ;//.icon(Utils.getMarkerBitmapFromView(MapsActivity.this, R.drawable.ic_home_black_24dp));
*/
//        new FetchURL(MapsActivity.this).execute(getUrl(place2.getPosition(), place3.getPosition(), "driving"), "driving");

  /*              getDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 20);
                        mMap.animateCamera(yourLocation);
                        //          marker.remove();
                    }
                });
*/


    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        String lat = "seller_lat,seller_log";
        String lon = "celler_lat,celler_log";

        System.out.println("get_details" + seller_lat + "," + seller_log + "," + celler_lat + "," + celler_log);
    /*    String current = String.valueOf(latitude) + String.valueOf(longitude);
        String source = String.valueOf(seller_lat) + String.valueOf(seller_log);
        String destination = String.valueOf(celler_lat) + String.valueOf(celler_log);*/
    //    Toast.makeText(this, "ert"+destination, Toast.LENGTH_SHORT).show();
   //     url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitude + "," + longitude + "&destination=" + celler_lat + "," + celler_log + "&waypoints=" + seller_lat + "," + seller_log + "&key=AIzaSyCjWlk_wQCNP_ilLRYr3cHGWF7nRDFzhLk";
       // url = "https://maps.googleapis.com/maps/api/directions/json?origin= 17.5169 , 78.3428 &destination= 17.5169,78.3428 &key=AIzaSyCjWlk_wQCNP_ilLRYr3cHGWF7nRDFzhLk";
         if(delivery_status.equals("Accepted-Delivery")){

             url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitude + "," + longitude + "&destination=" + celler_lat + "," + celler_log + "&waypoints=" + seller_lat + "," + seller_log + "&key=AIzaSyCjWlk_wQCNP_ilLRYr3cHGWF7nRDFzhLk";

           /*  Location startpoint=new Location("");
             startpoint.setLatitude(latitude);
             startpoint.setLongitude(longitude);

             Location forgetlocation=new Location("");
             forgetlocation.setLatitude(seller_lat);
             forgetlocation.setLongitude(seller_log);
             float distance=startpoint.distanceTo(forgetlocation);
             distance_click.setText(""+Math.round((distance/1000)*100.0f)/100.0+" Km");
             double d=Double.parseDouble(distance_click.getText().toString());
             Toast.makeText(MapsActivity.this,""+d,Toast.LENGTH_LONG).show();
             double d1=d-2;*/
             double d=Double.parseDouble(distance_click.getText().toString());
      //       Toast.makeText(MapsActivity.this,""+d,Toast.LENGTH_LONG).show();

          /* if(d<=0.30){



               dialog = new Dialog(MapsActivity.this);
                 dialog.setContentView(R.layout.custom_dailog_box);
                 dialog.setTitle("Title...");

                 // set the custom dialog components - text, image and button
                  text = (EditText) dialog.findViewById(R.id.editText);
                 //edittext=text.getText().toString();
                  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                 // if button is clicked, close the custom dialog
                 dialogButton.setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         getserverData();

                     }
                 });
                 dialog.show();
           }else {

           }*/
           /*  */

         }else  if(delivery_status.equals("Shipped")){
             Toast.makeText(MapsActivity.this, " Mounika okey", Toast.LENGTH_SHORT).show();
      //       url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitude + "," + longitude + "&destination=" + celler_lat + "," + celler_log +"&key=AIzaSyCjWlk_wQCNP_ilLRYr3cHGWF7nRDFzhLk";
             url="https://maps.googleapis.com/maps/api/directions/json?origin= 17.5169 , 78.3428 &destination= 17.5169,78.3428 &key=AIzaSyDcNHxLEzgtQ07FJLQD3Jt4M8i7qwatCUc";
/*
            double d=Double.parseDouble(destination);
            Toast.makeText(MapsActivity.this,""+d,Toast.LENGTH_LONG).show();

            if(d<=0.30){

                 dialog1 = new Dialog(MapsActivity.this);
                 dialog1.setContentView(R.layout.custom_dailog_box);
                 dialog1.setTitle("Title...");

                 // set the custom dialog components - text, image and button
                  text = (EditText) dialog1.findViewById(R.id.editText);
                  text.setHint(R.string.order_id_data);

                 Button dialogButton = (Button) dialog1.findViewById(R.id.dialogButtonOK);
                 // if button is clicked, close the custom dialog
                 dialogButton.setOnClickListener(new OnClickListener() {
                     @Override

                     public void onClick(View v) {
                         getDeliveryPicked();
                     }
                 });

                 dialog1.show();
             }else {

             }
*/
         }else {

         }




        //    String url="https://maps.googleapis.com/maps/api/directions/json?origin="+seller_lat+","+seller_log+"&destination="+celler_lat+","+celler_log+"&key=AIzaSyCjWlk_wQCNP_ilLRYr3cHGWF7nRDFzhLk";//"&waypoints="+seller_lat+","+seller_log+

        System.out.println("url_data" + url);
        return url;
    }

    //https://maps.googleapis.com/maps/api/directions/json?origin=27.658143,85.3199503&destination=27.667491, 85.3208583&mode=driving&key=AIzaSyAR_K-JtmjlTNlvlbAbKc6qy-_ldJibUTc
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    void StartTimer() {
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 13);
        mMap.animateCamera(yourLocation);


     /*   handler = new Handler();
        handlerTask = new Runnable()
        {
            @Override
            public void run() {
*/
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    System.out.println("adresses" + addresses);

                    Address address = addresses.get(0);
                    result = address.getAddressLine(0);
                    //       Toast.makeText(MapsActivity.this,"ohhh       "+result,Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                place1 = new MarkerOptions().position(new LatLng(latitude,longitude)).title(result).icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.deliveryboy1,"Narender")));//.icon(Utils.getMarkerBitmapFromView(MapsActivity.this, R.drawable.ic_home_black_24dp))
                place2 = new MarkerOptions().position(new LatLng(seller_lat, seller_log)).title(seller).icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.p1,"Narender")));
                place3 = new MarkerOptions().position(new LatLng(celler_lat, celler_log)).title(celler).icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.d1,"Narender")));

                //    marker=mMap.addMarker(place1);
                mMap.addMarker(place1);
                mMap.addMarker(place2);
                mMap.addMarker(place3);

                new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place3.getPosition(), "driving"), "driving");

                getDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 20);
                        mMap.animateCamera(yourLocation);
                        //          marker.remove();
                    }
                });

           /*     handler.postDelayed(handlerTask, 50000);
            }
        };
        handlerTask.run();
*/
    }
    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageResource(resource);
       /*  TextView txt_name = (TextView)marker.findViewById(R.id.name);
        txt_name.setText(_name);*/

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    @Override
    public void onLocationChanged(Location location) {
      //  mMap.clear();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Location forgetlocation=new Location("");
        forgetlocation.setLatitude(seller_lat);
        forgetlocation.setLongitude(seller_log);
        float distance=location.distanceTo(forgetlocation);
        distance_click.setText(""+Math.round((distance/1000)*100.0f)/100.0);
        Location distinationlocation=new Location("");
        distinationlocation.setLatitude(celler_lat);
        distinationlocation.setLongitude(celler_log);
        float distance1=location.distanceTo(distinationlocation);
        destination=""+Math.round((distance1/1000)*100.0f)/100.0;

        StartTimer();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);



       /* latitude = myLocation.getLatitude();
        longitude = myLocation.getLongitude();
        Toast.makeText(MapsActivity.this,"assasasasas"+latitude+longitude,Toast.LENGTH_LONG).show();
*/



        //   mMap.clear();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void getserverData(){
        System.out.println("mapadata"+text.getText().toString()+", "+booking_id);
       /* progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/

        RequestQueue rq = Volley.newRequestQueue(MapsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/order_pick",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadata"+response);
// Toast.makeText(getActivity(), "data"+response, Toast.LENGTH_SHORT).show();

                        try {


                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("order picked")) {
                             //   progressDialog.dismiss();
                                dialog.dismiss();

                                Toast.makeText(MapsActivity.this, "order picked", Toast.LENGTH_SHORT).show();
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
                params.put("book_id", text.getText().toString());
                params.put("pick_status","Shipped");
                params.put("order_piced",complete_date);


                return params;
            }
        };
        rq.add(stringRequest);
    }
    public void getDeliveryPicked(){

       /* progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();*/
        RequestQueue rq = Volley.newRequestQueue(MapsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://mezotint.com/index.php/api/pincheck",
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        System.out.println("mounikadata"+response);
                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if(message.equals("Curret Pin")) {
                          //      progressDialog.dismiss();
                                dialog1.dismiss();
                                Toast.makeText(MapsActivity.this, "Task Completed", Toast.LENGTH_SHORT).show();
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
                params.put("book_id",booking_id);
                params.put("order_comp_date",complete_date);
                return params;
            }
        };
        rq.add(stringRequest);
    }
}





/*
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude),
                        new LatLng(dest.latitude, dest.longitude))
                .width(2).color(Color.RED).geodesic(true));*/

// String url="https://maps.googleapis.com/maps/api/directions/json?origin=4-121, jlframes, balanagar, main road4-121, jlframes, balanagar, main road,IN&destination=hyderabad,IN&waypoints=piduguralla,IN|Suryapet,IN|Nalgonda,IN&key=AIzaSyAQVAY-NPqya0WVfPwhngHu98Lsrx8-xtk";
//    String url="https://maps.googleapis.com/maps/api/directions/json?origin="+lat+"&destination="+lon+"&waypoints=piduguralla&key=AIzaSyAQVAY-NPqya0WVfPwhngHu98Lsrx8-xtk";

// https://maps.googleapis.com/maps/api/directions/json?origin=17.4933456,78.3485884&destination=16.30665267,80.4365402&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";

//     String url="https://maps.googleapis.com/maps/api/directions/json?origin=17.4933456,78.3485884&destination=16.30665267,80.4365402&key=AIzaSyCzNqCVzgq-RgxUGnMwChB8YrRVxLm1yik";
  /*      // Building the url to the web service
    //     String url="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&avoid=highways&mode=bicycling &key=AIzaSyDmEDfzkfIgCHlBSMS8WG_yqDAeo0O_7kM";
       // String url="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination="+str_origin+"&avoid="+str_dest+"&mode=bicycling&key=AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g";
        //  String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
   //     String url = "https://maps.googleapis.com/maps/api/directions/json?location=27.667491,%2085.3208583&radius=5000&key=AIzaSyDmEDfzkfIgCHlBSMS8WG_yqDAeo0O_7kM&sensor=true";
   //    String url="https://maps.googleapis.com/maps/api/directions/json?origin="+str_origin+"&destination="+str_dest+"&key=AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g";
  */
      /* String lat=;
        String lon="16.30665267,80.4365402";*/

// String url="https://maps.googleapis.com/maps/api/directions/json?origin=17.4933456,78.3485884&destination=16.30665267,80.4365402&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";





  /*  mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomIn());*/

/*
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 13);

        mMap.animateCamera(yourLocation);
        place1 = new MarkerOptions().position(new LatLng(17.4933456,78.3485884 )).title(seller);// .icon(BitmapDescriptorFactory.fromBitmap(
        //  createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
        place2 = new MarkerOptions().position(new LatLng(16.30665267,80.4365402)).title(celler);//.icon(BitmapDescriptorFactory.fromBitmap(
        place3 = new MarkerOptions().position(new LatLng(17.4933345,78.3485854)).title(celler);//.icon(BitmapDescriptorFactory.fromBitmap(

        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addMarker(place3);

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 20);
                mMap.animateCamera(yourLocation);

            }
        });
        new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
*/


        /*  markerModelList=new ArrayList<>();
        markerModelList.add(new MarkerModel(17.464985,78.450228));
        markerModelList.add(new MarkerModel(17.5291783, 78.4481956));
        markerModelList.add(new MarkerModel(17.4934335,78.3488296));


        for(int i=0;i<markerModelList.size();i++){
            place1 = new MarkerOptions().position(new LatLng(markerModelList.get(i).getLatitude(),markerModelList.get(i).getLatitude())).title("Hyderabad");
          System.out.println("position1"+markerModelList.get(i).getLatitude());

            mMap.addMarker(place1);
        }*/



//  createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
/*
        place3 = new MarkerOptions().position(new LatLng(17.1883,79.2000)).title("Nalgonda").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
        place4 = new MarkerOptions().position(new LatLng(17.3850,78.4867)).title("hyd").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
        place5 = new MarkerOptions().position(new LatLng(16.4852,79.8901)).title("Pidugu").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
        place6 = new MarkerOptions().position(new LatLng(17.1315,79.6336  )).title("Suryapet").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")));
*/

/*
        mMap.addMarker(place3);
        mMap.addMarker(place4);
        mMap.addMarker(place5);
        mMap.addMarker(place6);
*/

        /*
        .icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(MapsActivity.this,R.drawable.ic_bookmark_border_black_24dp,"Narender"))).setTitle("Hotel Nirulas Noida");
*/
    /*  LatLng customMarkerLocationOne = new LatLng(seller_lat,seller_log);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationOne).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
*/
/*        LatLng customMarkerLocationTwo = new LatLng(28.583078, 77.313744);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationTwo).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
        LatLng customMarkerLocationThree = new LatLng(28.583078, 77.313744);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationThree).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
        LatLng customMarkerLocationFour = new LatLng(28.583078, 77.313744);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationFour).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
        LatLng customMarkerLocationFIve = new LatLng(28.583078, 77.313744);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationFIve).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
        LatLng customMarkerLocationSix = new LatLng(28.583078, 77.313744);
        mMap.addMarker(new MarkerOptions().position(customMarkerLocationSix).
                icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(MapsActivity.this,R.drawable.start_blue,"Narender")))).setTitle("Hotel Nirulas Noida");
*/
//   mMap.moveCamera(CameraUpdateFactory.newLatLng(place1));

        /*handler = new Handler();
                handlerTask = new Runnable()
                {
@Override
public void run() {


        handler.postDelayed(handlerTask, 5000);
        }
        };
        handlerTask.run();
*/

