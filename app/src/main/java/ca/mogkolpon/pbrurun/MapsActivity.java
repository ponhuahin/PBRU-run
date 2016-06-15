package ca.mogkolpon.pbrurun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double userLatADouble, userLngADouble;
    private LocationManager locationManager;
    private Criteria criteria;
    private String[] userLoginStrings;
    private MyData myData;
    private static final String urlEditLocation = "http://swiftcodingthai.com/pbru3/edit_location.php";
    private static final String urlUser = "http://swiftcodingthai.com/pbru3/get_user.php";
    private  int[] avataint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        userLoginStrings = getIntent().getStringArrayExtra("Login");

        myData = new MyData();
        avataint =myData.getAvataInts();

        //Setup Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setAltitudeRequired(false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } //Main Method

    private class ConnectedLocation extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

                try {
                    OkHttpClient okHttpClient =new OkHttpClient();
                    Request.Builder builder =new Request.Builder();
                    Request request =builder.url(urlUser).build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response.body().string();

                } catch (Exception e) {
                    return null;
                }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("pbruV6", "s ==>"+s);
            try {
                JSONArray jsonArray=new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    double douLat = Double.parseDouble(jsonObject.getString("Lat"));
                    double douLng = Double.parseDouble(jsonObject.getString("Lng"));
                    LatLng latLng=new LatLng(douLat,douLng);
                    String strTitle = jsonObject.getString("Name");
                    int intIndex = Integer.parseInt(jsonObject.getString("Avata"));
                    mMap.addMarker(new MarkerOptions());

                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }//onPost
    }


    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);

        Location fromISPLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (fromISPLocation != null) {

            userLatADouble = fromISPLocation.getLatitude();
            userLngADouble = fromISPLocation.getLongitude();

        }

        Location fromGPSLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (fromGPSLocation != null) {
            userLatADouble = fromGPSLocation.getLatitude();
            userLngADouble = fromGPSLocation.getLongitude();
        }

        Log.d("pbruV5", "userLat ==> " + userLatADouble);
        Log.d("pbruV5", "userLng ==> " + userLngADouble);

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("pbruV4", "Cannot find Location");
        }


        return location;
    } //myFindLocaton

    public final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            userLatADouble = location.getLatitude();
            userLngADouble = location.getLongitude();

        } //onLocationChange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        } //onStatusChange

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup Center map
        LatLng latLng = new LatLng(myData.getLatADouble(), myData.getLngADouble());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //Create My Loop
        createMyLoop();

    } //onMapReady

    private void createMyLoop(){
        editUserLocationToServer();
        makeAllMarker();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                createMyLoop();
            }
        }, 3000);
    }

    private void makeAllMarker() {
        mMap.clear();
        ConnectedLocation connectedLocation=new ConnectedLocation();
        connectedLocation.execute();
    }

    private void editUserLocationToServer() {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", userLoginStrings[0])
                .add("Lat", Double.toString(userLatADouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEditLocation).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }
} // Main Class