package com.wellwisher.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.msg91.sendotpandroid.library.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotpandroid.library.PhoneNumberUtils;
import com.msg91.sendotpandroid.library.internal.Iso2Phone;
import com.rilixtech.CountryCodePicker;
import com.wellwisher.OTP.CountrySpinner;
import com.wellwisher.R;
import com.wellwisher.Utill.Constants;
import com.wellwisher.Utill.InternetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpPage extends AppCompatActivity {

    public static final String INTENT_PHONENUMBER = "phonenumber";
    public static final String INTENT_COUNTRY_CODE = "code";
    String mCountryIso;

    @BindView(R.id.textview_signup)
    TextView textview_signup;
    @BindView(R.id.textview_login_page)
    TextView textview_login_page;
    @BindView(R.id.edittext_pnone_number)
    EditText edittext_pnone_number;
    @BindView(R.id.edittext_name)
    EditText edittext_name;
    @BindView(R.id.edittext_pswsd)
    EditText edittext_pswsd;
    @BindView(R.id.signup_relative)
    RelativeLayout signup_relative;

    ProgressDialog dialog;
    ArrayList<String> StoreContacts ;
    Cursor cursor ;

    String name, phonenumber,phonecode ;
    public  static final int RequestPermissionCode  = 1 ;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        ButterKnife.bind(this/*, rootview*/);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phonecode =ccp.getFullNumberWithPlus();
        StoreContacts = new ArrayList<String>();

        textview_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup_relative.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openActivity(getE164Number());

               phoeNumberWithOutCountryCode(" +919655434975");


            }
        } );


        /*  Msg91 OTP  Integration */
        mCountryIso = PhoneNumberUtils.getDefaultCountryIso(this);
        final String defaultCountryName = new Locale("", mCountryIso).getDisplayName();

        tryAndPrefillPhoneNumber();
    }
   public void phoeNumberWithOutCountryCode(String phone)
   {
       try {
           // phone must begin with '+'
           PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
           Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phone, "");
           int countryCode = numberProto.getCountryCode();
           long nationalNumber = numberProto.getNationalNumber();
           Log.i("code", "code " + countryCode);
           Log.i("code", "national number " + nationalNumber);
           long ph=nationalNumber;
       } catch (NumberParseException e) {
           Log.i("code", "national number1 " + phone);
           System.err.println("NumberParseException was thrown: " + e.toString());
       }
   }
    private void tryAndPrefillPhoneNumber() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            edittext_pnone_number.setText(manager.getLine1Number());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
try {
    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        tryAndPrefillPhoneNumber();
    } else {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
            Toast.makeText(this, "This application needs permission to read your phone number to automatically "
                    + "pre-fill it", Toast.LENGTH_LONG).show();
        }
    }
    }catch (Exception e){

    }
    }

    private void openActivity(String phoneNumber) {

        formvalidation(phoneNumber);
    }

    private void setButtonsEnabled(boolean enabled) {
        textview_signup.setEnabled(enabled);
    }

    /* public void onButtonClicked(View view) {
        openActivity(getE164Number());
    }
   */

    private boolean isPossiblePhoneNumber() {
        return PhoneNumberUtils.isPossibleNumber(edittext_pnone_number.getText().toString(), mCountryIso);
    }
    private String getE164Number() {
        return edittext_pnone_number.getText().toString().replaceAll("\\D", "").trim();
        // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
    }


    public void formvalidation(String phoneNumber) {
        if (edittext_pnone_number.getText().toString().equals("") ||
                edittext_name.getText().toString().equals("")
                || edittext_pswsd.getText().toString().equals("")){
            if (edittext_pnone_number.getText().toString().trim().isEmpty()) {
                edittext_pnone_number.setError("Please Enter Mobile Number");
            }
            if (edittext_name.getText().toString().trim().isEmpty()) {
                edittext_name.setError("Please Enter Name");
            }
            if (edittext_pswsd.getText().toString().trim().isEmpty()) {
                edittext_pswsd.setError("Please Enter Password");
            }
        } else {
            if (!InternetUtils.isOnline(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
            }else {
              //  formSubmitApi(phoneNumber);
                String newnumber;
                if(phoneNumber.length()==10){
                     newnumber=phoneNumber;
                }else {
                     newnumber = phoneNumber.substring(2);
                }
                Intent verification = new Intent(this, OTPPage.class);
                verification.putExtra(INTENT_PHONENUMBER, newnumber);
                verification.putExtra("NameResits",edittext_name.getText().toString());
                verification.putExtra("Password", edittext_pswsd.getText().toString());
                verification.putExtra("country_code", phonecode);
                verification.putExtra(INTENT_COUNTRY_CODE, Iso2Phone.getPhone(mCountryIso));
                startActivity(verification);
                finish();
            }
        }}
    private void formSubmitApi(final String phoneNumber) {
        dialog = ProgressDialog.show(SignUpPage.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTERATION_API,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Signup_Response(object,phoneNumber);
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
                params.put("phone",edittext_pnone_number.getText().toString());
                params.put("name",edittext_name.getText().toString());
                params.put("password",edittext_pswsd.getText().toString());
                params.put("country_code","+91"/*StoreContacts.toString()*/);
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
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpPage.this);
        requestQueue.add(stringRequest);
    }
    public void Signup_Response(JSONObject object, String phoneNumber) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("value");
               // otpflag=false;
                Toast.makeText(SignUpPage.this, message, Toast.LENGTH_LONG).show();
                Intent verification = new Intent(this, OTPPage.class);
                verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
                verification.putExtra(INTENT_COUNTRY_CODE, Iso2Phone.getPhone(mCountryIso));
                startActivity(verification);
                finish();
            } else {
                String message = object.getString("value");
                Toast.makeText(SignUpPage.this, message, Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }


    private void contactsynApi() {
        dialog = ProgressDialog.show(SignUpPage.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
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
                params.put("user_id","17");
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
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpPage.this);
        requestQueue.add(stringRequest);
    }
    public void contactsynApi_Response(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("value");
                // otpflag=false;
                Toast.makeText(SignUpPage.this, message, Toast.LENGTH_LONG).show();
            } else {
                String message = object.getString("value");
                Toast.makeText(SignUpPage.this, message, Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

}
