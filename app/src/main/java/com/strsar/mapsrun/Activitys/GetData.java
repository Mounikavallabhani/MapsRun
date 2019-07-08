package com.strsar.mapsrun.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.strsar.mapsrun.MapsActivity;
import com.strsar.mapsrun.MarkerModel;
import com.strsar.mapsrun.R;
import com.strsar.mapsrun.directionhelpers.FetchURL;
import com.strsar.mapsrun.directionhelpers.TaskLoadedCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetData extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private MarkerOptions place1, place2,place3,place4,place5,place6;
    Button getDirection;
    private Polyline currentPolyline;

    List<MarkerModel> markerModelList;
    Double seller_log,seller_lat,celler_log,celler_lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);

        getDirection = findViewById(R.id.button_click);

      /*  seller_lat=Double.parseDouble(getIntent().getStringExtra("seller_lat"));
        seller_log=Double.parseDouble(getIntent().getStringExtra("seller_log"));
        celler_lat=Double.parseDouble(getIntent().getStringExtra("celler_lat"));
        celler_log=Double.parseDouble(getIntent().getStringExtra("celler_log"));
        seller=getIntent().getStringExtra("seller");
        celler=getIntent().getStringExtra("celler");*/

     /*  System.out.println("seller_lat_seller_log"+seller_lat+", "+seller_log+", "+celler_lat+" , "+celler_lat);

        System.out.println();*/

        //27.658143,85.3199503
        //27.667491,85.3208583


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapgetdata);
        mapFragment.getMapAsync(this);


        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_other_activity);


        ArrayList<MounikaModel> mounikaModels = getIntent().getParcelableArrayListExtra("Property");

        if (mounikaModels != null) {
            for (MounikaModel mounikaModel : mounikaModels) {

                    seller_lat = Double.parseDouble(mounikaModel.getSeller_lat());
                    seller_log = Double.parseDouble(mounikaModel.getSeller_log());
                    celler_lat = Double.parseDouble(mounikaModel.getUser_lat());
                    celler_log = Double.parseDouble(mounikaModel.getUser_log());
                    System.out.println("seller_lat_seller_log" + seller_lat + ", " + seller_log + ", " + celler_lat + " , " + celler_lat);
                    System.out.println("get_size_dealsFragment" + mounikaModels.size());

            }


        }






    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");

      /*  mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.zoomIn());*/

       CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 13);

       // mMap.animateCamera(yourLocation);

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(seller_lat,seller_log), 20);
                mMap.animateCamera(yourLocation);

            }
        });

        /*  markerModelList=new ArrayList<>();
        markerModelList.add(new MarkerModel(17.464985,78.450228));
        markerModelList.add(new MarkerModel(17.5291783, 78.4481956));
        markerModelList.add(new MarkerModel(17.4934335,78.3488296));


        for(int i=0;i<markerModelList.size();i++){
            place1 = new MarkerOptions().position(new LatLng(markerModelList.get(i).getLatitude(),markerModelList.get(i).getLatitude())).title("Hyderabad");
          System.out.println("position1"+markerModelList.get(i).getLatitude());

            mMap.addMarker(place1);
        }*/



        place1 = new MarkerOptions().position(new LatLng(seller_lat,seller_log)).title("seller") .icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));
        place2 = new MarkerOptions().position(new LatLng(celler_lat,celler_log)).title("seller").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));
        place3 = new MarkerOptions().position(new LatLng(17.1883,79.2000)).title("Nalgonda").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));
        place4 = new MarkerOptions().position(new LatLng(17.3850,78.4867)).title("hyd").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));
        place5 = new MarkerOptions().position(new LatLng(16.4852,79.8901)).title("Pidugu").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));
        place6 = new MarkerOptions().position(new LatLng(17.1315,79.6336  )).title("Suryapet").icon(BitmapDescriptorFactory.fromBitmap(
                createCustomMarker(GetData.this,R.drawable.start_blue,"Narender")));

        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.addMarker(place3);
        mMap.addMarker(place4);
        mMap.addMarker(place5);
        mMap.addMarker(place6);
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
        new FetchURL(GetData.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

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

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin =origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        //  String url="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&avoid=highways&mode=bicycling &key=AIzaSyDmEDfzkfIgCHlBSMS8WG_yqDAeo0O_7kM";
        // String url="https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination="+str_origin+"&avoid="+str_dest+"&mode=bicycling&key=AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g";
        //  String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        //  https://maps.googleapis.com/maps/api/directions/json?location=27.667491,%2085.3208583&radius=5000&key=AIzaSyDmEDfzkfIgCHlBSMS8WG_yqDAeo0O_7kM&sensor=true
        //String url="https://maps.googleapis.com/maps/api/directions/json?origin="+str_origin+"&destination="+str_dest+"&key=AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g";
        String lat="17.4933456,78.3485884";
        String lon="16.30665267,80.4365402";
      /*  String lat=;
        String lon="16.30665267,80.4365402";*/

        //   String url="https://maps.googleapis.com/maps/api/directions/json?origin="+str_origin+"&destination="+str_dest+"&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";
        String url="https://maps.googleapis.com/maps/api/directions/json?origin=Guntur,IN&destination=Hyderabad,IN&waypoints=piduguralla,IN|Suryapet,IN|Nalgonda,IN&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";
        https://maps.googleapis.com/maps/api/directions/json?origin=17.4933456,78.3485884&destination=16.30665267,80.4365402&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";

        //  String url="https://maps.googleapis.com/maps/api/directions/json?"+str_origin+"&"+str_dest+"&key=AIzaSyDX_p77FEE2ltWFmn7G4lainWvLs7sB8TU";

        System.out.println("url_data"+url);
        return url;
    }
    //  https://maps.googleapis.com/maps/api/directions/json?origin=27.658143,85.3199503&destination=27.667491, 85.3208583&mode=driving&key=AIzaSyAR_K-JtmjlTNlvlbAbKc6qy-_ldJibUTc
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
/*
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(src.latitude, src.longitude),
                        new LatLng(dest.latitude, dest.longitude))
                .width(2).color(Color.RED).geodesic(true));*/
    }
}




