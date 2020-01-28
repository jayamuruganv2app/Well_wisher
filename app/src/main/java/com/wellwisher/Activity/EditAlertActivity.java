package com.wellwisher.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wellwisher.MainActivity;
import com.wellwisher.R;
import com.wellwisher.Utill.Constants;
import com.wellwisher.Utill.InternetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAlertActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();

    @BindView(R.id.linear_only_alert)
    LinearLayout linear_only_alert;
    @BindView(R.id.linear_only_alert_message)
    LinearLayout linear_only_alert_message;
    @BindView(R.id.line_one_time_alert)
    LinearLayout line_one_time_alert;
    @BindView(R.id.line_repetive)
    LinearLayout line_repetive;


    @BindView(R.id.textview_date)
    TextView textview_date;
    @BindView(R.id.textview_submit)
    TextView textview_submit;
    @BindView(R.id.text_occation)
    TextView textview_occation;

    @BindView(R.id.Liner_data_picker)
    RelativeLayout Liner_data_picker;
    @BindView(R.id.tick1)
    RelativeLayout tick1;
    @BindView(R.id.tick2)
    RelativeLayout tick2;
    @BindView(R.id.tick3)
    RelativeLayout tick3;
    @BindView(R.id.tick4)
    RelativeLayout tick4;

    Spinner spinRealtionshp_type,spinner2,spinner3;
    String relation_type_uid,relation_types_id;
    ArrayList<String > relationship_typarrylist = new ArrayList<String>();
    ArrayList<String > relationship_typearrylistid= new ArrayList<String>();
    ArrayList<String >relationship_tag_arrylist = new ArrayList<String>();
    ArrayList<String > relationship_tag_arrylistid= new ArrayList<String>();
    ArrayList<String >Occasion_type_arrylist = new ArrayList<String>();
    ArrayList<String > Occasion_type_arrylistid= new ArrayList<String>();

    String string_relation_type,relation_type_id,string_relation_tag,relation_tag_id,string_Occasion_type,Occasion_type_id;
    ProgressDialog dialog;
    String userid;
    String alart_1="",alart_2="",alart_3="",alart_4="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_alert_page );

        ButterKnife.bind( this/*, rootview*/ );
        Toolbar mToolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( mToolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

        userid = PreferenceManager.getDefaultSharedPreferences( EditAlertActivity.this ).getString( "userid", "" );

        spinRealtionshp_type = (Spinner) findViewById( R.id.spinner );
        spinner2 = (Spinner) findViewById( R.id.spinner2 );
        spinner3 = (Spinner) findViewById( R.id.spinner3 );
        Relationship_type_API();
        Occasion_type_API();
        Relationship_tag_API( "" );
        textview_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitformSubmitApi();
                //formvalidation();
            }
        } );

        Liner_data_picker.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog( EditAlertActivity.this, date, myCalendar
                        .get( Calendar.YEAR ), myCalendar.get( Calendar.MONTH ),
                        myCalendar.get( Calendar.DAY_OF_MONTH ) ).show();
            }
        } );

        linear_only_alert.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tick1.setVisibility( View.VISIBLE );
                tick2.setVisibility( View.GONE );
                alart_1 = "0";
                alart_2 = "";
            }
        } );

        linear_only_alert_message.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tick1.setVisibility( View.GONE );
                tick2.setVisibility( View.VISIBLE );
                alart_1 = "";
                alart_2 = "1";
            }
        } );
        line_one_time_alert.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tick3.setVisibility( View.VISIBLE );
                tick4.setVisibility( View.GONE );
                alart_3 = "0";
                alart_4 = "";
            }
        } );

        line_repetive.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tick3.setVisibility( View.GONE );
                tick4.setVisibility( View.VISIBLE );
                alart_3 = "";
                alart_4 = "1";
            }
        } );

        spinRealtionshp_type.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    string_relation_type = adapterView.getItemAtPosition( i ).toString();
                    relation_type_id = relationship_typearrylistid.get( i ).toString();
                    if (relation_type_id.equals( "0" )) {

                    } else {
                        Relationship_tag_API( relation_type_id );
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //    seller_doc_id="";
            }
        } );

        spinner2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    string_relation_tag = adapterView.getItemAtPosition( i ).toString();
                    relation_tag_id = relationship_tag_arrylistid.get( i ).toString();
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        spinner3.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    string_Occasion_type = adapterView.getItemAtPosition( i ).toString();
                    Occasion_type_id = relationship_tag_arrylistid.get( i ).toString();
                    textview_occation.setText( string_Occasion_type );

                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //    seller_doc_id="";
            }
        } );


    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set( Calendar.YEAR, year );
            myCalendar.set( Calendar.MONTH, monthOfYear );
            myCalendar.set( Calendar.DAY_OF_MONTH, dayOfMonth );
            String myFormat = "yyyy-MM-dd"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat( myFormat, Locale.US );
            textview_date.setText( sdf.format( myCalendar.getTime() ) );


        }

    };


    private void Relationship_type_API() {

        dialog = ProgressDialog.show( EditAlertActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true );
        dialog.setCancelable( false );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Constants.RELATION_CATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        relationship_typarrylist.clear();
                        relationship_typearrylistid.clear();
                        JSONObject object = null;
                        try {
                            object = new JSONObject( response );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Relationship_type_ApiResponse( object );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        stringRequest.setRetryPolicy( new RetryPolicy() {
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
        } );
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        requestQueue.add( stringRequest );
    }

    public void Relationship_type_ApiResponse(JSONObject object) {
        try {
            String status = object.getString( "status" );
            JSONArray category_arrayk = object.getJSONArray( "categories" );
            int lenght = category_arrayk.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject object1 = category_arrayk.getJSONObject( i );
                relation_types_id = object1.getString( "category_id" );
                relation_type_uid = object1.getString( "category_name" );
                relationship_typearrylistid.add( relation_types_id );
                relationship_typarrylist.add( relation_type_uid );
            }

            relationship_typarrylist.add( 0, "Select Relationship Type" );
            relationship_typearrylistid.add( 0, "0" );
            ArrayAdapter dataAdapter = new ArrayAdapter( getApplicationContext(), android.R.layout.simple_spinner_item, relationship_typarrylist );
            dataAdapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice );
            spinRealtionshp_type.setAdapter( dataAdapter );

            if(relationship_typearrylistid!=null)
                for(int j=0; j<relationship_typearrylistid.size(); j++){
                    if(relationship_typearrylistid.get(j).toString().equals("")){
                        spinRealtionshp_type.setSelection(j);
                        break;
                    } }
         //   Relationship_tag_API( relationship_typearrylistid.get( 1 ).toString() );
            dialog.dismiss();
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

    private void Relationship_tag_API(final String relation_type_id) {
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Constants.RELATIONSHIP_SUB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        relationship_tag_arrylist.clear();
                        relationship_tag_arrylistid.clear();
                        JSONObject object = null;
                        try {
                            object = new JSONObject( response );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Relationship_tag_ApiResponse( object );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "category_id", relation_type_id );

                return params;
            }
        };
        stringRequest.setRetryPolicy( new RetryPolicy() {
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
        } );
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        requestQueue.add( stringRequest );
    }

    public void Relationship_tag_ApiResponse(JSONObject object) {
        try {
            String status = object.getString( "status" );
            JSONArray category_arrayk = object.getJSONArray( "subcategories" );
            int lenght = category_arrayk.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject object1 = category_arrayk.getJSONObject( i );
                String relation_types_id = object1.getString( "subcategory_id" );
                String relation_type_name = object1.getString( "subcat_name" );
                relationship_tag_arrylistid.add( relation_types_id );
                relationship_tag_arrylist.add( relation_type_name );
            }

            relationship_tag_arrylist.add( 0, "Select Relationship Tag" );
            relationship_tag_arrylistid.add( 0, "0" );
            ArrayAdapter dataAdapter = new ArrayAdapter( getApplicationContext(), android.R.layout.simple_spinner_item, relationship_tag_arrylist );
            dataAdapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice );
            spinner2.setAdapter( dataAdapter );

            if(relationship_tag_arrylistid!=null)
                for(int j=0; j<relationship_tag_arrylistid.size(); j++){
                    if(relationship_tag_arrylistid.get(j).toString().equals("")){
                        spinner2.setSelection(j);
                        break;
                    } }
            dialog.dismiss();
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

    private void Occasion_type_API() {
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Constants.OCCASIONS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Occasion_type_arrylist.clear();
                        Occasion_type_arrylistid.clear();
                        JSONObject object = null;
                        try {
                            object = new JSONObject( response );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Occasion_type_ApiResponse( object );
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        stringRequest.setRetryPolicy( new RetryPolicy() {
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
        } );
        RequestQueue requestQueue = Volley.newRequestQueue( getApplicationContext() );
        requestQueue.add( stringRequest );
    }

    public void Occasion_type_ApiResponse(JSONObject object) {
        try {
            String status = object.getString( "status" );
            JSONArray category_arrayk = object.getJSONArray( "occasions" );
            int lenght = category_arrayk.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject object1 = category_arrayk.getJSONObject( i );
                String relation_types_id = object1.getString( "occasion_id" );
                String relation_type_name = object1.getString( "occasion_name" );
                Occasion_type_arrylistid.add( relation_types_id );
                Occasion_type_arrylist.add( relation_type_name );
            }

            Occasion_type_arrylist.add( 0, "Select Occasion Type" );
            Occasion_type_arrylistid.add( 0, "0" );
            ArrayAdapter dataAdapter = new ArrayAdapter( getApplicationContext(), android.R.layout.simple_spinner_item, Occasion_type_arrylist );
            dataAdapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice );
            spinner3.setAdapter( dataAdapter );

            if(Occasion_type_arrylistid!=null)
                for(int j=0; j<Occasion_type_arrylistid.size(); j++){
                    if(Occasion_type_arrylistid.get(j).toString().equals("")){
                        spinner3.setSelection(j);
                        break;
                    } }
            dialog.dismiss();
        } catch (Exception e) {
        }

        dialog.dismiss();
    }

    public void formvalidation() {
        if (spinRealtionshp_type.getSelectedItem().toString().equals( "Select Relationship Type" ) || spinner2.getSelectedItem().toString().equals( "Select Relationship Tag" )
                || spinner3.getSelectedItem().toString().equals( "Select Occasion Type" ) || textview_date.getText().toString().trim().isEmpty()) {


            if (spinRealtionshp_type.getSelectedItem().toString().equals( "Select Relationship Type" )) {
                ((TextView) spinRealtionshp_type.getSelectedView()).setError( "Please select relationship type" );

            }
            if (spinner2.getSelectedItem().toString().equals( "Select Relationship Tag" )) {
                ((TextView) spinner2.getSelectedView()).setError( "Please select relationship tag" );
            }
            if (spinner3.getSelectedItem().toString().equals( "Select Occasion Type" )) {
                ((TextView) spinner3.getSelectedView()).setError( "Please select occasion type" );

            }
            if (textview_date.getText().toString().trim().isEmpty()) {

                textview_date.setError( "Please select the occasion date" );
            }


        } else {
            if (!InternetUtils.isOnline( getApplicationContext() )) {
                Toast.makeText( getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG ).show();
            } else {
                submitformSubmitApi();
            }
        }
    }


    private void submitformSubmitApi() {
        dialog = ProgressDialog.show( EditAlertActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true );
        dialog.setCancelable( false );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Constants.CREATE_ALART_API,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject( response );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Signup_Response( object );
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Register.this,  R.string.poor_connection_volley, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "firend_id", "3" );
                params.put( "occasion_type", string_Occasion_type );
                if (alart_1.equals( "" )) {
                    params.put( "alert_type", "1" );
                } else {
                    params.put( "alert_type", "0" );
                }
                if (alart_3.equals( "" )) {
                    params.put( "alert_cycle", "1" );
                } else {
                    params.put( "alert_cycle", "0" );
                }

                params.put( "alert_date", textview_date.getText().toString() );
                params.put( "user_id", userid );
                params.put( "relation_cat", string_relation_type );
                params.put( "relation_subcat", string_relation_tag );
                return params;
            }
        };
        stringRequest.setRetryPolicy( new RetryPolicy() {
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
        } );
        RequestQueue requestQueue = Volley.newRequestQueue( EditAlertActivity.this );
        requestQueue.add( stringRequest );
    }

    public void Signup_Response(JSONObject object) {
        try {
            String status = object.getString( "status" );
            if (status.equals( "true" )) {
                String message = object.getString( "value" );
                // otpflag=false;
                Toast.makeText( EditAlertActivity.this, message, Toast.LENGTH_LONG ).show();
                Intent verification = new Intent( this, MainActivity.class );
                startActivity( verification );
                finish();
            } else {
                String message = object.getString( "value" );
                Toast.makeText( EditAlertActivity.this, message, Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected( menuItem );
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}