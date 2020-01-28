package com.wellwisher;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wellwisher.Activity.AddAlertPage;
import com.wellwisher.Activity.AlertForEveryOneActivity;
import com.wellwisher.Activity.LoginActivity;
import com.wellwisher.Activity.SearchPageActivity;
import com.wellwisher.Controller.EventAdapter;
import com.wellwisher.Controller.TabAdapter;
import com.wellwisher.Controller.ViewPageAdapter;
import com.wellwisher.ModalClass.EventModule;
import com.wellwisher.ModalClass.TabModel;
import com.wellwisher.Utill.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    int hhhhhh = 0;
   private View.OnClickListener myClickListener;
    String pos;
    @BindView(R.id.recyclerview_tabs)
    RecyclerView recyclerViewTab;
    @BindView(R.id.recyclerview_upcoming_event)
    RecyclerView recyclerViewEvent;
    TabAdapter tabAdapter;
    ArrayList<TabModel> tabItemlist=new ArrayList<TabModel>();
    ArrayList<EventModule> EventList=new ArrayList<EventModule>();
    ArrayList<String> tabtext=new ArrayList<String >();
    ArrayList<String> tabcount=new ArrayList<String >();
    ArrayList<String> eventnameList=new ArrayList<String >();
    ArrayList<String> eventdateList=new ArrayList<String >();
    ArrayList<String> eventdayList=new ArrayList<String >();
    EventAdapter eventAdapter;
    // private ViewPager vp_slider;
    private LinearLayout ll_dots;

    //private TextView[] dots;
    int page_position = 0;
    private List<Integer> bannerListArray = new ArrayList<Integer>();
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private static final int REQUEST_RUNTIME_PERMISSION = 123;
    String[] permissons = {Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG};


    private Context context;
    private SubscriptionManager mSubscriptionManager;

    public static boolean isMultiSimEnabled = false;
    public static String defaultSimName;

    public static List<SubscriptionInfo> subInfoList;
    public static ArrayList<String> Numbers;


    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    private static String[] simStatusMethodNames = {"getSimStateGemini", "getSimState"};

    //   TextView textview;

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // textview = (TextView) findViewById(R.id.textview);
        context = this;
        oncreatemain();
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/
        //    showFragment(new HomePageFragment());
       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);

    }

    private void oncreatemain() {

        ButterKnife.bind(this);
        getTabitem();
        setTabitem();
        recyclerViewTab.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        tabAdapter = new TabAdapter(MainActivity.this, tabItemlist,new TabAdapter.OnEditTextChanged(){
            @Override
            public void onTextChanged(int position, View v) {
              //  enteredNumber[position] =charSeq;
                 pos= String.valueOf(position);
                 tabposition(pos);
             //   Toast.makeText(context, pos+"position", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewTab.setAdapter(tabAdapter);
        recyclerViewTab.setHasFixedSize(true);

        geteventlist();
        seteventlist();
        showeventlist();
        getimage();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this,bannerListArray);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.inactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == bannerListArray.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                viewPager.setCurrentItem(page_position, true);

            }
        };
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 3000);
    }

    private void tabposition(String pos) {
        if(pos.equals("0")){

        }else if(pos.equals("1")){

        }else if(pos.equals("2")){

        }else if(pos.equals("3")){

        }else if(pos.equals("4")){

        }else if(pos.equals("5")){

        }
    }


    public interface RecyclerViewClickListener {
        public void recyclerViewListClicked(View v, int position);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

                break;
            case R.id.fab2:


                if (Build.VERSION.SDK_INT > 22) {
                    //for dual sim mobile
                    SubscriptionManager localSubscriptionManager = SubscriptionManager.from(this);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (localSubscriptionManager.getActiveSubscriptionInfoCount() > 1) {
                        //if there are two sims in dual sim mobile
                        List localList = localSubscriptionManager.getActiveSubscriptionInfoList();
                        SubscriptionInfo simInfo = (SubscriptionInfo) localList.get(0);
                        SubscriptionInfo simInfo1 = (SubscriptionInfo) localList.get(1);
                        try {
                            final String sim1 = simInfo.getDisplayName().toString();
                            final String sim2 = simInfo1.getDisplayName().toString();
                            final String sim11 = simInfo.getNumber().toString();
                            final String sim21 = simInfo1.getNumber().toString();
                            final String sim211 = simInfo1.getNumber().toString();
                        } catch (Exception e) {
                        }

                    } else {
                        //if there is 1 sim in dual sim mobile
                        TelephonyManager tManager = (TelephonyManager) getBaseContext()
                                .getSystemService(Context.TELEPHONY_SERVICE);

                        String sim1 = tManager.getNetworkOperatorName();

                    }

                } else {
                    //below android version 22
                    TelephonyManager tManager = (TelephonyManager) getBaseContext()
                            .getSystemService(Context.TELEPHONY_SERVICE);

                    String sim1 = tManager.getNetworkOperatorName();
                }

                //   textview.setText(info);
            case R.id.fab3:

                if (CheckPermission(getApplicationContext(), permissons[0])) {
                    // you have permission go ahead
                    YourTaskNow();
                } else {
                    // you do not have permission go request runtime permissions
                    RequestPermission(MainActivity.this, permissons, REQUEST_RUNTIME_PERMISSION);
                }

                // textview.setText(alContacts);
                break;
            case R.id.fab4:
                TelephonyManager tMgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String mPhoneNumber = tMgr.getLine1Number();

                TextView textView = (TextView) findViewById(R.id.phonenumber);
                textView.setText(mPhoneNumber);

                break;
        }
    }

    private void YourTaskNow() {
        ContentResolver cr = getApplicationContext().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            ArrayList<String> alContacts = new ArrayList<String>();
            ArrayList<String>ddd=new ArrayList<>();
            do
            {
                String idd = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds
                            .Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds
                            .Phone.CONTACT_ID +" = ?",new String[]{ idd }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactNumber1 = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String name = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String namekk = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String email = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                        alContacts.add(contactNumber+ " "  + ":" + " " +name+"\n");
                        //  ddd.add(contactNumber1);
                        /* textview.setText(alContacts.toString());*/
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }

    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;


        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;


        }
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                super.onBackPressed();
            } else {
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 1) {
                    fm.popBackStack();
                } else {
                    backPressedAlert();
                }
            }
        }
    }
    private void backPressedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Do you want to close your app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finishAffinity(); // Close all activites
                System.exit(0);  // closing files, releasing resources
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Intent i = new Intent(getApplicationContext(), SearchPageActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            Intent i = new Intent(getApplicationContext(), AlertForEveryOneActivity.class);
//            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
//            Intent i = new Intent(getApplicationContext(), AddAlertPage.class);
//            startActivity(i);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
           startActivity(i);
           finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showeventlist()
    {
        recyclerViewEvent.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        eventAdapter = new EventAdapter(MainActivity.this,EventList);
        recyclerViewEvent.setAdapter(eventAdapter);
        recyclerViewEvent.setHasFixedSize(true);
        ViewCompat.setNestedScrollingEnabled(recyclerViewEvent, false);
    }
    public void getTabitem()
    {
        tabtext.add("BirthDay");
        tabtext.add("Anniversary");
        tabtext.add("Friendship Day");
        tabcount.add("1");
        tabcount.add("2");
        tabcount.add("3");

    }
    public void setTabitem()
    {
        for (int i=0;i<tabcount.size();i++)
        {
            TabModel tabModel=new TabModel();
            tabModel.setTabCount(tabcount.get(i).toString());
            tabModel.setTabText(tabtext.get(i).toString());
            tabItemlist.add(tabModel);
        }


    }
    public void geteventlist()
    {
        eventnameList.add("Jayamurugan");
        eventnameList.add("muthusamy");
        eventnameList.add("Raja");
        eventnameList.add("Harshu");
        eventnameList.add("Vijay");

        eventdateList.add("21-12-2019");
        eventdateList.add("25-12-2019");
        eventdateList.add("26-12-2019");
        eventdateList.add("29-12-2019");
        eventdateList.add("30-12-2019");

        eventdayList.add("2 days");
        eventdayList.add("5 days");
        eventdayList.add("6 days");
        eventdayList.add("8 days");
        eventdayList.add("9 days");
    }
    public void seteventlist()
    {
        for (int i=0;i<eventnameList.size();i++)
        {
            EventModule eventModule=new EventModule();
            eventModule.setName(eventnameList.get(i).toString());
            eventModule.setDate(eventdateList.get(i).toString());
            eventModule.setDay(eventdayList.get(i).toString());
            EventList.add(eventModule);
        }
    }
    public void getimage()
    {
        bannerListArray.add(R.drawable.b1_image);
        bannerListArray.add(R.drawable.b2_image);
        bannerListArray.add(R.drawable.b3_image);
        bannerListArray.add(R.drawable.b4_image);
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {

            case REQUEST_RUNTIME_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                    YourTaskNow();
                } else {
                    // you do not have permission show toast.
                }
                return;
            }
        }
    }

    public void RequestPermission(Activity context, String[] Permission, int Code) {
        if (ContextCompat.checkSelfPermission(context,
                Permission[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Permission[0])) {
            } else {
                ActivityCompat.requestPermissions(context, Permission,
                        Code);
            }
        }
    }

    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }



    private void bannerslistApi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RELATION_CATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bannerListArray.clear();
                        JSONObject object = null;
                        try {
                            object = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bannerlistlists_ApiResponse(object);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                     //   dialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("user_country",/*phonecode*/"961");
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void bannerlistlists_ApiResponse(JSONObject object) {
        try {
            String status = object.getString("status");
            JSONArray category_arrayk = object.getJSONArray("categories");
            int lenght = category_arrayk.length();
            for (int i = 0; i < lenght; i++) {
                JSONObject object1 = category_arrayk.getJSONObject(i);
              //  Bannerpojo bannerPojo = new Bannerpojo();
                TabModel tabModel=new TabModel();
                tabModel.setTabCount(object1.getString("category_id"));
                tabModel.setTabText(object1.getString("category_name"));
                tabItemlist.add(tabModel);
                tabAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
        }
        if (bannerListArray.size() == 0) {
        }else {
        }
    }
}


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
*/
