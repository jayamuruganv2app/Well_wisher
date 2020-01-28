package com.wellwisher.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.msg91.sendotpandroid.library.SendOTPConfig;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.Verification;
import com.msg91.sendotpandroid.library.VerificationListener;
import com.msg91.sendotpandroid.library.internal.Iso2Phone;
import com.wellwisher.MainActivity;
import com.wellwisher.OTP.IPConverter;
import com.wellwisher.OTP.NetworkConnectivity;
import com.wellwisher.R;
import com.wellwisher.Utill.Constants;
import com.wellwisher.Utill.InternetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OTPPage extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {

    private static final String TAG = Verification.class.getSimpleName();

    private Verification mVerification;
    @BindView(R.id.textview_submit)
    TextView textview_submit;
    @BindView(R.id.textview_change_number)
    TextView textview_change_number;
    @BindView(R.id.textview_resend)
    TextView textview_resend;
    EditText edittext_new_number;
    String new_phone_number="";
     String phoneNumber;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);
        ButterKnife.bind(this/*, rootview*/);

        textview_change_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Change__phone_number_();
            }
        });

        /*OTP Integration */
        textview_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateVerification();
            }
        });
        initiateVerification();


        ArrayList<String> dshgfj=new ArrayList<>();
        dshgfj.add("raja");
        dshgfj.add("jai");
        dshgfj.add("vijay");
        dshgfj.add("murugan");
        dshgfj.add("bala");
        dshgfj.add("ram");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dshgfj);
        editor.putString("bannerListArray", json);
        editor.commit();
    }

    public void Change__phone_number_() {
        LayoutInflater layoutInflater = LayoutInflater.from(OTPPage.this);
        final View promptView = layoutInflater.inflate(R.layout.change_number_otp, null);
        final AlertDialog alertD1 = new android.support.v7.app.AlertDialog.Builder(OTPPage.this).create();
        alertD1.setCancelable(true);
        edittext_new_number = (EditText) promptView.findViewById(R.id.edittext_new_number);
        Button button_submitk = (Button) promptView.findViewById(R.id.button_submitk);
        button_submitk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // changeformvalidation();

                if ( edittext_new_number.getText().toString().equals("") ){
                    if (edittext_new_number.getText().toString().equals("")) {
                        edittext_new_number.setError("Please Enter Mobile Number");
                    }
                } else {
                    if (!InternetUtils.isOnline(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
                    }else {
                        //  forgotPasswordApi();
                        new_phone_number = edittext_new_number.getText().toString();
                        initiateVerification();
                        Toast.makeText(OTPPage.this, "Your number has been updated successfully", Toast.LENGTH_SHORT).show();
                        alertD1.dismiss();
                    }
                    alertD1.dismiss();
                }

            }});
        alertD1.setView(promptView);
        alertD1.show();
    }


    void createVerification(String phoneNumber, boolean skipPermissionCheck, String countryCode) {
        if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
            //hideProgressBar();
        } else {
            boolean withoutOtp = false;
            if (NetworkConnectivity.isConnectedMobileNetwork(getApplicationContext())) {
                withoutOtp = true;
            } else {

            }


            SendOTPConfig otpConfig = SendOtpVerification
                    .config(/*countryCode +*/ phoneNumber)
                    .context(this)
                    .httpsConnection(false)//use false currently https is under maintenance
                    /*         direct verification while connect with mobile network    */
                    .autoVerification(false)
                    .setIp(getIp(withoutOtp))
                    .verifyWithoutOtp(withoutOtp)
                    //////////////////////////////////////////////////////////////////////////////////////////////////
                    .unicode(false) // set true if you want to use unicode (or other language) in sms
                    .expiry("5")//value in minutes
                    .senderId("ABCDEF") //where ABCDEF is any string
                    .otplength("6") //length of your otp max length up to 9 digits
                    //--------case 1-------------------
                    //                  .message("##OTP## is Your verification digits.")//##OTP## use for default generated OTP
                    //--------case 2-------------------
                    /*  .otp("1234")// Custom Otp code, if want to add yours
                      .message("1234 is Your verification digits.")//Here 1234 same as above Custom otp.*/
                    //-------------------------------------
                    //use single case at a time either 1 or 2
                    .build();
            mVerification = SendOtpVerification.createSmsVerification(otpConfig, this);
            mVerification.initiate();
        }
    }


    /*
     * if moiblenetwork is true than device is in mobile network
     */
    private String getIp(boolean moibleNetwork) {
        if (moibleNetwork) {
            try {

                return IPConverter.getIPAddress(true);
            } catch (Exception ex) {
            }

        } else {
            WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return Formatter.formatIpAddress(ip);
        }
        return "";
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
                        + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
                        .show();
            }
          //  enableInputField(true);
        }
        initiateVerificationAndSuppressPermissionCheck();
    }

    void initiateVerification() {
        initiateVerification(false);
    }

    void initiateVerificationAndSuppressPermissionCheck() {
        initiateVerification(true);
    }

    void initiateVerification(boolean skipPermissionCheck) {
        Intent intent = getIntent();
        if (intent != null) {
            phoneNumber = intent.getStringExtra(SignUpPage.INTENT_PHONENUMBER);
            String countryCode = intent.getStringExtra(SignUpPage.INTENT_COUNTRY_CODE);
         //   TextView phoneText = (TextView) findViewById(R.id.numberText);
          //  phoneText.setText("+" + countryCode + phoneNumber);

            if(new_phone_number.equals("")) {
                createVerification(phoneNumber, skipPermissionCheck, countryCode);
            }else{
                createVerification(new_phone_number, skipPermissionCheck, countryCode);
            }
        }
    }

    public void ResendCode() {
      //  startTimer();
        mVerification.resend("voice");
    }

    public void onSubmitClicked(View view) {
        String code = ((EditText) findViewById(R.id.edittextview_otp)).getText().toString();
        if (!code.isEmpty()) {
            hideKeypad();
            if (mVerification != null) {
                mVerification.verify(code);
              //  showProgress();
              //  TextView messageText = (TextView) findViewById(R.id.textView);
              //  messageText.setText("Verification in progress");
              //  enableInputField(false);
            }
        }else {
            Toast.makeText(this, "Enter your OTP code", Toast.LENGTH_SHORT).show();
        }
    }



    void hideProgressBarAndShowMessage(int message) {
      //  hideProgressBar();
      //  TextView messageText = (TextView) findViewById(R.id.textView);
     //   messageText.setText(message);
     //   Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

   /* void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.INVISIBLE);
        TextView progressText = (TextView) findViewById(R.id.progressText);
        progressText.setVisibility(View.INVISIBLE);
    }
*/
   /* void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.VISIBLE);
    }
*/
  /*  void showCompleted() {
        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
        checkMark.setVisibility(View.VISIBLE);
    }*/

    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "Verification initialization failed: " + exception.getMessage());
        hideProgressBarAndShowMessage(R.string.failed);
    }

    @Override
    public void onVerified(String response) {
        //enableInputField(false);
        Log.d(TAG, "Verified!\n" + response);
        hideKeypad();
        hideProgressBarAndShowMessage(R.string.verified);
      //  showCompleted();
        Intent intent = getIntent();
        if (intent != null) {
         String   name = intent.getStringExtra("NameResits");
            String Password = intent.getStringExtra("Password");
            String country_code = intent.getStringExtra("country_code");
            signupformSubmitApi(name,Password,country_code);
        }
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "Verification failed: " + exception.getMessage());
        hideKeypad();
        hideProgressBarAndShowMessage(R.string.failed);
        //enableInputField(true);
    }

  /*  private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(VerificationActivity.this, R.color.sendotp_grey));
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend via call ( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
                resend_timer.setClickable(true);
                resend_timer.setText("Resend via call");
                resend_timer.setTextColor(ContextCompat.getColor(VerificationActivity.this, R.color.send_otp_blue));
            }
        }.start();
    }*/

    private void hideKeypad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        /*        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0)*/
    }

    private void signupformSubmitApi(final String namename, final String password, final String country_code) {
        dialog = ProgressDialog.show(OTPPage.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
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
                        Signup_Response(object);
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
                if(new_phone_number.equals("")) {
                   //String gfd= phoneNumber.length();
                    params.put("phone", phoneNumber);
                }else{
                    params.put("phone", new_phone_number);
                }
                params.put("name",namename);
                params.put("password",password);
                params.put("country_code",country_code);
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
        RequestQueue requestQueue = Volley.newRequestQueue(OTPPage.this);
        requestQueue.add(stringRequest);
    }
    public void Signup_Response(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("value");
                String user_id = object.getString("user_id");
                PreferenceManager.getDefaultSharedPreferences(OTPPage.this).edit().putString("userid",user_id).commit();

                // otpflag=false;
                Toast.makeText(OTPPage.this, message, Toast.LENGTH_LONG).show();
                Intent verification = new Intent(this, SearchPageActivity.class);
                startActivity(verification);
                finish();

            } else {
                String message = object.getString("value");
                Toast.makeText(OTPPage.this, message, Toast.LENGTH_LONG).show();
//                Intent verification = new Intent(this, LoginActivity.class);
//                startActivity(verification);
              //  finish();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }




    private void Otp_Api() {
        dialog = ProgressDialog.show(OTPPage.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.FORGETPASSWORD_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object= null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        forgotPasswordApiResponse(object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Login.this, R.string.poor_connection_volley, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if(new_phone_number.equals("")) {
                    params.put("user_email", phoneNumber);
                }else{
                    params.put("user_email", new_phone_number);
                }
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void forgotPasswordApiResponse(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("message");
                // String s_email = object.getString("user_email");
                Toast.makeText(OTPPage.this, message, Toast.LENGTH_LONG).show();

               // PreferenceManager.getDefaultSharedPreferences(OTPPage.this).edit().putString("fstatus","10" ).commit();
                //  Changepassword(s_email);
               /* SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(bannerListArray);
                editor.putString("bannerListArray", json);
                editor.commit();
*/





                Intent i = new Intent(getApplicationContext(), SearchPageActivity.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            } else {
                String message = object.getString("value");
                Toast.makeText(OTPPage.this, message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

}
