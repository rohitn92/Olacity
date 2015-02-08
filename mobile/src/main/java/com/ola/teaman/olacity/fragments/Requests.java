package com.ola.teaman.olacity.fragments;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ola.teaman.olacity.LetsRide;
import com.ola.teaman.olacity.R;


public class Requests extends Activity {
    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    //URL to get JSON Array
    private static String url = "http://mitrevels.in/app_roll/gcm_server_php/request_json.php";

    //JSON Node Names
    private static final String TAG_OS = "devices";
    private static final String TAG_VER = "device_id";
    private static final String TAG_NAME = "event_id";
    private static final String TAG_API = "flag";

    JSONArray android = null;

    DialogInterface.OnClickListener dialogClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_requests);
        oslist = new ArrayList<HashMap<String, String>>();

        new JSONParse().execute();


    }



    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Requests.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();



        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }


        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                android = json.getJSONArray(TAG_OS);
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String ver = c.getString(TAG_VER);
                    String name = c.getString(TAG_NAME);
                    String api = c.getString(TAG_API);




                    // Adding value HashMap key => value


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_VER, ""+ver);
                    map.put(TAG_NAME,""+ name);
                    map.put(TAG_API, ""+api);

                    oslist.add(map);
                    list=(ListView)findViewById(R.id.list2);





                    ListAdapter adapter = new SimpleAdapter(Requests.this, oslist,
                            R.layout.list_v,
                            new String[] { TAG_VER,TAG_NAME, TAG_API }, new int[] {
                            R.id.vers, R.id.name, R.id.api});

                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {



                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    Requests.this );

                            // set title
                            alertDialogBuilder.setTitle("Choose Font Size");

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage("Click yes to exit!")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            new Accept_Request().execute(""+oslist.get(+position).get("device_id"),""+oslist.get(+position).get("event_id"));
                                            Toast.makeText(Requests.this, "You Clicked at yeuyughs "+""+oslist.get(+position).get("device_id"), Toast.LENGTH_SHORT).show();
                                            // if this button is clicked, close
                                            // current activity




                                        }
                                    })
                                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            Toast.makeText(Requests.this, "You Clicked at no", Toast.LENGTH_SHORT).show();
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();

                        }

                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    //now the class that changes the request flag


    class Accept_Request extends AsyncTask<String, Void,String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                // Creating HTTP client
                HttpClient httpClient = new DefaultHttpClient();
                // Creating HTTP Post
                HttpPost httpPost = new HttpPost(
                        "http://mitrevels.in/app_roll/gcm_server_php/change_request.php");

                // Building post parameters
                // key and value pair
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

                nameValuePair.add(new BasicNameValuePair("device_id", ""+urls[0]));
                nameValuePair.add(new BasicNameValuePair("event_id", ""+urls[1]));
                Log.d("hi","here finally"+urls[1]);
                //Toast.makeText(Requests.this, "You Clicked at "+urls[0]+" "+urls[1], Toast.LENGTH_LONG).show();
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

                    // writing response to log
                    Log.d("Http Response:", response.toString());
                    return response.toString();
                } catch (ClientProtocolException e) {
                    // writing exception to log
                    e.printStackTrace();
                } catch (IOException e) {
                    // writing exception to log
                    e.printStackTrace();

                }

            } catch (Exception e) {
                this.exception = e;
                return null;
            }
            return null;

        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception 
            // TODO: do something with the feed
        }
    }

}
