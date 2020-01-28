package com.wellwisher.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wellwisher.MainActivity;
import com.wellwisher.R;

import java.util.List;

public class AlertForEveryOneActivity extends AppCompatActivity implements View.OnClickListener{
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
LinearLayout floatin_linear_1,floatin_linear_2,floatin_linear_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_for_every_one);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        floatin_linear_1=(LinearLayout)findViewById(R.id.floatin_linear_1);
        floatin_linear_2=(LinearLayout)findViewById(R.id.floatin_linear_2);
        floatin_linear_3=(LinearLayout)findViewById(R.id.floatin_linear_3);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
       /* fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);*/

        floatin_linear_1.setOnClickListener(this);
        floatin_linear_2.setOnClickListener(this);
        floatin_linear_3.setOnClickListener(this);


    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.floatin_linear_1:

//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);

                break;
            case R.id.floatin_linear_2:


                break;
                //   textview.setText(info);
            case R.id.floatin_linear_3:

                // textview.setText(alContacts);
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

           /* fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;*/
            fab.startAnimation(rotate_backward);
            floatin_linear_1.startAnimation(fab_close);
            floatin_linear_2.startAnimation(fab_close);
            floatin_linear_3.startAnimation(fab_close);
            floatin_linear_1.setClickable(false);
            floatin_linear_2.setClickable(false);
            floatin_linear_3.setClickable(false);
            isFabOpen = false;



        } else {

           /* fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;*/
            fab.startAnimation(rotate_forward);
            floatin_linear_1.startAnimation(fab_open);
            floatin_linear_2.startAnimation(fab_open);
            floatin_linear_3.startAnimation(fab_open);
            floatin_linear_1.setClickable(true);
            floatin_linear_2.setClickable(true);
            floatin_linear_3.setClickable(true);
            isFabOpen = true;


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
