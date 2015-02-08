package com.ola.teaman.olacity.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ola.teaman.olacity.AsyncPost;
import com.ola.teaman.olacity.NavigationDrawer;
import com.ola.teaman.olacity.R;

import com.ola.teaman.olacity.fragments.dummy.DummyContent;
import com.ola.teaman.olacity.onTaskComplete;

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
import java.util.HashMap;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SelectMeetUpContacts extends Fragment implements AbsListView.OnItemClickListener, onTaskComplete {


    private static final int CONTACT_PICKER_RESULT = 1001;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String URL = "http://mitrevels.in/app_roll/gcm_server_php/send_users.php";
    HashMap<String, String> data = new HashMap<String, String>();

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static SelectMeetUpContacts newInstance(String param1, String param2) {
        SelectMeetUpContacts fragment = new SelectMeetUpContacts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectMeetUpContacts() {
    }

    String items[];

    AsyncPost post;
    //Contacts contact;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, name);

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("id", "123");
        data.put("number", "123456789");
      //  contact = new Contacts(this);
        post = new AsyncPost(data);
        post.onComplete = this;
        post.execute(URL);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        items = new String[]{"Vegetables", "Fruits", "Flower Buds", "Legumes", "Bulbs", "Tubers"};

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        //  ArrayAdapter<String> mAdapter = ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dumb);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(items[position]);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

   public static  ArrayList<String> final_names= new ArrayList<>();
    public static  ArrayList<String> final_numbers= new ArrayList<>();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    @Override
    public void onTaskCompleted(String out) {
        Log.e("OUTPUT", out);
        //TextView t = (TextView) findViewById(R.id.text);
        String result[] = out.split(",");
        Log.d("ROHIT", result.toString());

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Log.d("ROHIT", "name : " + name + ", ID : " + id);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("ROHIT", "phone" + phone);
                        data.put(phone,name);
                    }
                    pCur.close();
                }
            }
        }

        int j=0;

     for (int i=0; i<result.length; ++i)
     {
         if (data.get(result[i])!=null)
         {
             final_names.add(j,data.get(result[i]));
             final_numbers.add(j,result[i]);


         ++j;
         }

     }
        Log.d("ROHIT","So result set =" + final_names);


        mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1,final_names);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

       mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
               view.setSelected(true);
               Toast.makeText(getActivity(),"YOLO",Toast.LENGTH_SHORT).show();

           }
       });

    }


       /* Intent i = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(i, CONTACT_PICKER_RESULT);
*/


/*
   public  void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (reqCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String number = "";
                    String lastName = "";
                    try {

                        Uri result = data.getData();

                        // get the id from the uri
                        String id = result.getLastPathSegment();

                        // query
                        cursor = getActivity().getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone._ID
                                        + " = ? ", new String[]{id}, null);

                        // cursor = getContentResolver().query(Phone.CONTENT_URI,
                        // null, Phone.CONTACT_ID + "=?", new String[] { id },
                        // null);

                        int numberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                        if (cursor.moveToFirst()) {
                            number = cursor.getString(numberIdx);
                            // lastName =
                            // cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        } else {
                            // WE FAILED
                        }
                    } catch (Exception e) {
                        // failed
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        } else {
                        }
                    }

                    Log.d("ROHIT",number);

                    // EditText lastNameEditText =
                    // (EditText)findViewById(R.id.last_name);
                    // lastNameEditText.setText(lastName);

            }

        }
    }
    */

}