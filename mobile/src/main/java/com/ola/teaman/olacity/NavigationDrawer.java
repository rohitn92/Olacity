package com.ola.teaman.olacity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ola.teaman.olacity.fragments.HomeActivity;
import com.ola.teaman.olacity.fragments.Requests;
import com.ola.teaman.olacity.fragments.SelectMeetUpContacts;
import com.ola.teaman.olacity.fragments.dummy.gogogo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NavigationDrawer extends ActionBarActivity implements HomeActivity.OnFragmentInteractionListener, SelectMeetUpContacts.OnFragmentInteractionListener, gogogo.OnFragmentInteractionListener {

    // declare properties
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        // for proper titles
        mTitle = mDrawerTitle = getTitle();

        // initialize properties
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // list the drawer items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[7];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_place, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_person, "My Account");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_edit, "Bookings");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_action_group, "Friends");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.ic_action_expand, "Wallet");
        drawerItem[5] = new ObjectDrawerItem(R.drawable.ic_action_settings, "Settings");
        drawerItem[6] = new ObjectDrawerItem(R.drawable.ic_action_about, "Contact Us");

        // Pass the folderData to our ListView adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.menu_detail_fragment, drawerItem);

        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);

        // set the item click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());





        // for app icon control for nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
             // R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        if (savedInstanceState == null) {
            // on first time display view for first nav item
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent req = new Intent(this, Requests.class);
                startActivity(req);

                return true;
            case R.id.action_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // to change up caret
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }


    // navigation drawer click listener
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        // update the main content by replacing fragments

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeActivity();
                break;
           case 1:
                fragment = new gogogo();
                break;
          /*  case 2:
                fragment = new BookingsActivity();
                break;
            case 3:
                fragment = new FriendsActivity();
                break;
            case 4:
                fragment = new WalletActivity();
                break;
            case 5:
                fragment = new SettingsActivity();
                break;
            case 6:
                fragment = new ContactFragment();
                break;

*/




            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    public void meetUp(View v)
    {
        Fragment fragment = new SelectMeetUpContacts();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        getFragmentManager().popBackStackImmediate();
    }



    public void SendData(View v)
    {
        String deviceID="7";
        String eventID= ""+ Calendar.getInstance().get(Calendar.SECOND);
        String ID= TextUtils.join(",", SelectMeetUpContacts.final_numbers);

        Log.d("ROHIT","called");

        new RetrieveFeedTask().execute(deviceID,eventID,ID);

        Intent i = new Intent(this, LetsRide.class);
            startActivity(i);


    }
    class RetrieveFeedTask extends AsyncTask<String, Void,String> {

        private Exception exception;

        protected String doInBackground(String... params) {
            String device = params[0];
            String event = params[1];
            String userID = params[2];
            Log.d ("Rohit", device + event + userID);

            try {
                // Creating HTTP client
                HttpClient httpClient = new DefaultHttpClient();
                // Creating HTTP Post
                HttpPost httpPost = new HttpPost(
                        "http://mitrevels.in/app_roll/gcm_server_php/add_request.php");

                // Building post parameters
                // key and value pair
                Log.d("ROHIT", device + event + userID);
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
                nameValuePair.add(new BasicNameValuePair("device_id", device));
                nameValuePair.add(new BasicNameValuePair("event_id", event));
                nameValuePair.add(new BasicNameValuePair("ids", userID));

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
