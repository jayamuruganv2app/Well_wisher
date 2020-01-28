package com.wellwisher.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.wellwisher.MainActivity;
import com.wellwisher.R;

public class SplashActivity extends AppCompatActivity {
    String username;
    String password;
   // CountryCodePicker ccp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs = getSharedPreferences(LoginActivity.MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences prefs1 = getSharedPreferences(LoginActivity.MY_PREFS_NAME, MODE_PRIVATE);
        username = prefs.getString("userNameLogin", null);
        password = prefs1.getString("passwordLogin", null);
        changeStatusBarColor();
        startTimer();
        //Test git for wellwisher

        String name="123";
        String raja="124";

        String jai="123456";
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (username == null || password == null) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } }


        }, 3 * 1000);

    }
    private void startTimer() {

    }
    private void changeStatusBarColor() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
}
