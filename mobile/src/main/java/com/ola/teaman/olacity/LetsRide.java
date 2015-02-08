package com.ola.teaman.olacity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LetsRide extends FragmentActivity implements onTaskComplete{

    //variables for json parsing
    String coord[][];
    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://mitrevels.in/app_roll/gcm_server_php/add_cod.php";

    //JSON Node Names
    private static final String TAG_OS = "devices";
    private static final String TAG_VER = "id";
    private static final String TAG_NAME = "lat";
    private static final String TAG_API = "long";

    JSONArray android = null;

    DialogInterface.OnClickListener dialogClickListener;

    //end

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    LatLng place;
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;
    GPSTracker gps;
    double lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coord=new String[100][3];

        gps = new GPSTracker(getApplicationContext());
        lat = gps.getLatitude();
        lon = gps.getLongitude();
        Log.e("Lat", lat+"");
        Log.e("Lon", lon+"");
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map))
                .getMap();
        if(map != null) {
            map.setMyLocationEnabled(true);
            LatLng place = new LatLng(lat, lon);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
            Marker hamburg = map.addMarker(new MarkerOptions().position(place)
                    .title("My Place"));
            callAsynchronousTask();
            // Move the camera instantly to hamburg with a zoom of 15.
            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }

    }

    public void UpadteLocation() {
        if(gps.canGetLocation()){
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            Log.e("lat", lat+"");
            Log.e("lon", lon+"");
            Log.e("LAt", lat+"");
            Log.e("Tage", lon+"");
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(10, 10))
                    .title("Hello world"));
        }
    }

    @Override
    public void onTaskCompleted(String out) {
        JSONArray devicearray;
        try {
            JSONObject ob = new JSONObject(out);
            map.clear();
            devicearray = ob.getJSONArray("devices");
            for(int i = 0; i < devicearray.length(); i++) {
                JSONObject o = devicearray.getJSONObject(i);
                String id = o.getString("id");
                String lat = o.getString("lat");
                String lon = o.getString("long");
                map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat),
                        Double.parseDouble(lon))).title(id));
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }

        UpadteLocation();


    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            SendCoordinates performBackgroundTask  = new SendCoordinates();
                            performBackgroundTask.onComplete = LetsRide.this;
                            HashMap<String, String> data = new HashMap<>();
                            data.put("id", "1");
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            performBackgroundTask.execute("http://mitrevels.in/app_roll/gcm_server_php/add_cod.php");
                            //performBackgroundTask.onComplete = MainActivity.this;
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }

    class SendCoordinates extends AsyncTask<String, Void,String> {

        private Exception exception;
        public onTaskComplete onComplete = null;
        protected String doInBackground(String... urls) {
            StringBuilder builder = new StringBuilder();
            try {
                place = new LatLng(lat, lon);

                // Creating HTTP client
                HttpClient httpClient = new DefaultHttpClient();
                // Creating HTTP Post

                HttpPost httpPost = new HttpPost(
                        "http://mitrevels.in/app_roll/gcm_server_php/add_cod.php");

                // Building post parameters
                // key and value pair
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
                nameValuePair.add(new BasicNameValuePair("id", "1"));
                nameValuePair.add(new BasicNameValuePair("lat", ""+lat));
                nameValuePair.add(new BasicNameValuePair("long", ""+lon));
                nameValuePair.add(new BasicNameValuePair("message", "hi"));
                nameValuePair.add(new BasicNameValuePair("header", "Hi"));

                // Url Encoding the POST parameters
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } catch (UnsupportedEncodingException e) {
                    // writing error to Log
                    e.printStackTrace();
                }

                // Making HTTP Request
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                        String line;
                        //Log.d("hi","here");
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        //Log.d("hi",""+builder);
                    } else {
                        Log.e("Failed", "Failed to download file");
                    }
                }
                catch (ClientProtocolException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch(Exception e){

            }
            return builder.toString();
        }
        protected void onPostExecute(String feed) {
            onComplete.onTaskCompleted(feed);
        }
    }



}