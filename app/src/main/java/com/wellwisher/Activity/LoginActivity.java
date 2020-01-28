package com.wellwisher.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.textview_new_account)
    TextView textview_new_account;
    @BindView(R.id.textview_login)
    TextView textview_login;
    @BindView(R.id.textview_forget_pwsd)
    TextView textview_forget_pwsd;
    @BindView(R.id.edittextview_pnone_number)
    EditText edittextview_pnone_number;
    @BindView(R.id.edittext_pswd)
    EditText edittext_pswd;
    @BindView(R.id.login_relative)
    RelativeLayout login_relative;

    EditText   edittext_forgetpasswor,edittext_forgetOTPpasswor,edittext_Confirm_pswd,edittext_change_new_pswd;
    AlertDialog alertD1;

    String Phonenumber,Passwords,edittext_forgetpasswortring;
    Dialog dialog;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private final int CAMERA_RESULT = 101;
    String userid;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userid = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("userid", "");

        if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS )==
                PackageManager.PERMISSION_GRANTED)&&
                (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS )== PackageManager.PERMISSION_GRANTED))
        {
            requestStoragePermission();
            requestCameraPermission();
        }
        else{
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)){
                //  Toast.makeText(getContext(), "Please enable the camera Permission", Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.READ_SMS
                        , Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);

            }
            else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE))
            {
                requestPermissions(new String[]{Manifest.permission.READ_SMS
                        , Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.READ_CONTACTS
                        , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);
            }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS))
            {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS
                        , Manifest.permission.READ_PHONE_STATE
                        ,Manifest.permission.READ_SMS
                        , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);
            }

            else {
                Toast.makeText(getApplicationContext(), "Please enable the permission form your app settings", Toast.LENGTH_LONG).show();
                requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);

            }
        }

        textview_new_account.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (!InternetUtils.isOnline(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
                    return;
                }
                if((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS )==
                        PackageManager.PERMISSION_GRANTED)&&
                        (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
                        && (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_CONTACTS )== PackageManager.PERMISSION_GRANTED))
                {
                    requestStoragePermission();
                    requestCameraPermission();
                    Intent i = new Intent(getApplicationContext(), SignUpPage.class);
                    startActivity(i);
                    finish();
                }
                else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)){
                        //  Toast.makeText(getContext(), "Please enable the camera Permission", Toast.LENGTH_LONG).show();
                        requestPermissions(new String[]{Manifest.permission.READ_SMS
                               , Manifest.permission.READ_PHONE_STATE
                                ,Manifest.permission.READ_CONTACTS
                                , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);

                    }
                    else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE))
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_SMS
                                , Manifest.permission.READ_PHONE_STATE
                                ,Manifest.permission.READ_CONTACTS
                                , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);
                    }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS))
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS
                                , Manifest.permission.READ_PHONE_STATE
                                ,Manifest.permission.READ_SMS
                                , Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);
                    }
                   /* else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CALL_LOG))
                    {
                        // Toast.makeText(getContext(), "Please enable the storage permission form your app settings", Toast.LENGTH_LONG).show();
                        requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_CALL_LOG}, CAMERA_RESULT);
                    }*/
                    else {
                        Toast.makeText(getApplicationContext(), "Please enable the permission form your app settings", Toast.LENGTH_LONG).show();
                        requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ,Manifest.permission.READ_CONTACTS,Manifest.permission.READ_PHONE_STATE,
                                 Manifest.permission.WRITE_CONTACTS}, CAMERA_RESULT);

                    }
                }

            }
        });
        login_relative.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!InternetUtils.isOnline(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
                    return;
                }

                FormValidation();
            }
        });

        textview_forget_pwsd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                    forgetpassword();

            }
        });
    }

    public void forgetpassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.forgetpassword, null);
         alertD1 = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this).create();
        alertD1.setCancelable(true);
        edittext_forgetpasswor = (EditText) promptView.findViewById(R.id.edittext_forgetpasswor);
        Button button_submitk = (Button) promptView.findViewById(R.id.button_submitk);
        button_submitk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeformvalidation();
            }});
        alertD1.setView(promptView);
        alertD1.show();
    }

    public void forgetOTPpassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.forgetotppassword, null);
        alertD1 = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this).create();
        alertD1.setCancelable(false);
        edittext_forgetOTPpasswor = (EditText) promptView.findViewById(R.id.edittext_forgetOTPpasswor);
        Button button_submitk = (Button) promptView.findViewById(R.id.button_OTP_submitk);
        button_submitk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeOTPformvalidation();
            }});
        alertD1.setView(promptView);
        alertD1.show();
    }
    public void Changepassword() {
        LayoutInflater layoutInflater = LayoutInflater.from(LoginActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.changepassword, null);
        alertD1 = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this).create();
        alertD1.setCancelable(false);
        edittext_change_new_pswd = (EditText) promptView.findViewById(R.id.edittext_change_new_pswd);
        edittext_Confirm_pswd = (EditText) promptView.findViewById(R.id.edittext_Confirm_pswd);
        Button button_submitk = (Button) promptView.findViewById(R.id.button_change_submitk);
        button_submitk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePasswordformvalidation();
            }});
        alertD1.setView(promptView);
        alertD1.show();
    }
    public void changeformvalidation() {
        if ( edittext_forgetpasswor.getText().toString().equals("") ){
            if (edittext_forgetpasswor.getText().toString().equals("")) {
                edittext_forgetpasswor.setError("Please Enter Mobile Number");
            }
        } else {
            if (!InternetUtils.isOnline(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
            }else {
                forgotPasswordApi();
            }
            alertD1.dismiss();
        }
    }
    public void changeOTPformvalidation() {
        if ( edittext_forgetOTPpasswor.getText().toString().equals("") ){
            if (edittext_forgetOTPpasswor.getText().toString().equals("")) {
                edittext_forgetOTPpasswor.setError("Please Enter OTP Code");
            }
        } else {
            if (!InternetUtils.isOnline(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
            }else {
                forgotOTPPasswordApi();
            }
            alertD1.dismiss();
        }
    }
    public void changePasswordformvalidation() {
        if ( edittext_change_new_pswd.getText().toString().equals("")||
                edittext_Confirm_pswd.getText().toString().equals("") ){
            if (edittext_change_new_pswd.getText().toString().equals("")) {
                edittext_change_new_pswd.setError("Please Enter New Password");
            }
            if (edittext_Confirm_pswd.getText().toString().equals("")) {
                edittext_Confirm_pswd.setError("Please Enter Confirm Password");
            }
        } else {
            if (!InternetUtils.isOnline(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
            }else {
                if(edittext_change_new_pswd.getText().toString().equals(edittext_Confirm_pswd.getText().toString())) {
                    Change_New_PasswordApi();
                }else {
                    Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                }
            }
            alertD1.dismiss();
        }
    }

    public void FormValidation() {
        if (edittextview_pnone_number.getText().toString().equals("") || edittext_pswd.getText().toString().equals("")) {
            if (edittextview_pnone_number.getText().toString().trim().isEmpty()) {
                edittextview_pnone_number.setError("Please Enter Mobile Number");
            } else if (edittext_pswd.getText().toString().trim().isEmpty()) {
                edittext_pswd.setError("Please Enter Password");
            }
        } else {
            if (!InternetUtils.isOnline(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
            }else {
              FormSubmitApi();

            }
        }
    }

    public void FormSubmitApi() {

        Phonenumber = edittextview_pnone_number.getText().toString();
        Passwords = edittext_pswd.getText().toString();
        dialog = ProgressDialog.show(LoginActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loginApiResponse(object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Toast.makeText(Login.this,  R.string.poor_connection_volley, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",Phonenumber);
                params.put("password",Passwords);
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
    public void loginApiResponse(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                dialog.dismiss();
                String message = object.getString("value");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                String user_id=object.getString("user_id");
                String username=object.getString("username");
                String name=object.getString("name");
                // String fstatus=object.getString("reset_password");
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("user_name", name).commit();
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("userid",user_id).commit();
                PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("email", username).commit();
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("userNameLogin", Phonenumber);
                editor.putString("passwordLogin",Passwords);
                editor.putString("loginid","true");
                editor.commit();
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();

            } else {
                String message = object.getString("value");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        } catch (Exception e) {
        }

    }



    /*       forgetpswd            */

    private void forgotPasswordApi() {
        edittext_forgetpasswortring = edittext_forgetpasswor.getText().toString();
        dialog = ProgressDialog.show(LoginActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
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
                params.put("username", edittext_forgetpasswortring);
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
                String message = object.getString("value");
                 String user_id = object.getString("user_id");
                  PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("userid",user_id).commit();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                forgetOTPpassword();
                dialog.dismiss();
            } else {
                String message = object.getString("value");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }

    /*    forgetOTPpswd          */

    private void forgotOTPPasswordApi() {
        userid = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).getString("userid", "");
        edittext_forgetpasswortring = edittext_forgetOTPpasswor.getText().toString();
        dialog = ProgressDialog.show(LoginActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.FORGETPASSWORD_OTP_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object= null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        forgotPasswordOTPApiResponse(object);
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
                params.put("userid", userid);
                params.put("otp", edittext_forgetpasswortring);
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
    private void forgotPasswordOTPApiResponse(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("value");
                // String s_email = object.getString("user_email");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                Changepassword();
                dialog.dismiss();
            } else {
                String message = object.getString("value");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }
      /*         Change NEw pswd             */

    private void Change_New_PasswordApi() {
        dialog = ProgressDialog.show(LoginActivity.this, Constants.PLEASE_WAIT, Constants.LOADING, true);
        dialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.CHANGEPASSWORD_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object= null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        change_pswd_new_ApiResponse(object);
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
                params.put("userid", userid);
                params.put("new_password", edittext_change_new_pswd.getText().toString());
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
    private void change_pswd_new_ApiResponse(JSONObject object) {
        try {
            String status = object.getString("status");
            if (status.equals("true")) {
                String message = object.getString("value");
                // String s_email = object.getString("user_email");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                String message = object.getString("value");
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
        }
        dialog.dismiss();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_RESULT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]
                    == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(getApplicationContext(), "Permission granted  you can start sell now", Toast.LENGTH_LONG).show();
                //   OptionChooseListener();
                Intent i = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(i);
            } else {
                //    Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},110);
    }
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_SMS)) {

        }

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_SMS},111);
    }
}
