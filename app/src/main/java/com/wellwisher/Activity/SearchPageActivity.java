package com.wellwisher.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wellwisher.Controller.ListViewAdapter;
import com.wellwisher.MainActivity;
import com.wellwisher.ModalClass.searchmodalclass;
import com.wellwisher.R;
import com.wellwisher.Utill.Constants;
import com.wellwisher.ModalClass.Searchmodal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPageActivity extends AppCompatActivity {

    private EditText etsearch;
    private ListView list;
    private ListViewAdapter adapter;
   // private String[] moviewList;
    ArrayList<String>moviewList;
    ArrayList<String>moviewList_id;
    public static ArrayList<Searchmodal> movieNamesArrayList;
    public static ArrayList<String> app_status=new  ArrayList<>();
    public static ArrayList<Searchmodal> array_sort;
    int textlength = 0;
    ProgressDialog dialog;
    ArrayList<String> StoreContacts ;
    Cursor cursor ;

    String name, phonenumber,userid ;
    public  static final int RequestPermissionCode  = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        userid = PreferenceManager.getDefaultSharedPreferences(SearchPageActivity.this).getString("userid", "");

        etsearch = (EditText) findViewById(R.id.searchView);
        list = (ListView) findViewById(R.id.lv1);

        StoreContacts = new ArrayList<String>();
        EnableRuntimePermission();
        GetContactsIntoArrayList();

        moviewList= new ArrayList<>();
        moviewList_id= new ArrayList<>();

        movieNamesArrayList = new ArrayList<>();
        array_sort = new ArrayList<>();

        ArrayList<String>dkjgl=new ArrayList<>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("bannerListArray", "");
        Type type = new TypeToken<List<String>>() {}.getType();
        dkjgl= gson.fromJson(json, type);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(SearchPageActivity.this, array_sort.get(position).getMovieName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddAlertPage.class);
               // intent.putExtra("friend_idds", phoneNumber);
                startActivity(intent);
              //  finish();

            }
        });

        etsearch.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = etsearch.getText().length();
                array_sort.clear();
                for (int i = 0; i < movieNamesArrayList.size(); i++) {
                    if (textlength <= movieNamesArrayList.get(i).getMovieName().length()) {
                        Log.d("ertyyy",movieNamesArrayList.get(i).getMovieName().toLowerCase().trim());
                        if (movieNamesArrayList.get(i).getMovieName().toLowerCase().trim().contains(
                                etsearch.getText().toString().toLowerCase().trim())) {
                            array_sort.add(movieNamesArrayList.get(i));
                        }
                    }
                }
                adapter = new ListViewAdapter(SearchPageActivity.this, array_sort, app_status);
                list.setAdapter(adapter);

            }
        });

    }
    public void GetContactsIntoArrayList(){

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                .CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }
          contactsynApi();
        cursor.close();

    } public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                SearchPageActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(getApplicationContext(),"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(SearchPageActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);


        }
    }


    private void contactsynApi() {
        dialog = ProgressDialog.show(SearchPageActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CONTACT_API,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        contactsynApi_Response(object);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Register.this,  R.string.poor_connection_volley, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",userid);
                params.put("contact_list",StoreContacts.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 500000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 500000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SearchPageActivity.this);
        requestQueue.add(stringRequest);
    }

    public void contactsynApi_Response(JSONObject object) {
        try {
            String status = object.getString("status");
            JSONArray category_arrayk = object.getJSONArray("user_list");
            int lenght = category_arrayk.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject object1 = category_arrayk.getJSONObject(i);
                searchmodalclass search=new searchmodalclass();
                search.setFriend_id (object1.getString("friend_id"));
                search.setUser_name(object1.getString("user_name"));
                search.setUser_mobile( object1.getString("user_mobile"));
                search.setApp_status(object1.getString("app_status"));
              String app_status1=  object1.getString("app_status");
                app_status.add(app_status1);
               // Occasion_type_arrylistid.add(relation_types_id);
              String  relation_type_name= object1.getString("user_name");
                moviewList.add(relation_type_name);

            }
            for (int i = 0; i < moviewList.size(); i++) {
                Searchmodal movieNames = new Searchmodal(moviewList.get(i));
                movieNamesArrayList.add(movieNames);
                array_sort.add(movieNames);
            }

            adapter = new ListViewAdapter(this,movieNamesArrayList,app_status);
            list.setAdapter(adapter);
            dialog.dismiss();
            //  Products_code_API(mechinearrylistid.get(1).toString());
        } catch (Exception e) {
        }
        dialog.dismiss();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        Intent intent=new Intent(SearchPageActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }
}