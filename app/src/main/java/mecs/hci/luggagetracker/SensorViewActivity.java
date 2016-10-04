package mecs.hci.luggagetracker;

import android.Manifest;
import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.DeviceInfo;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SensorViewActivity extends AppCompatActivity {

    public static String TAG = "LuggageTracker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

/*
        Assigning view variables to thier respective view in xml
        by findViewByID method
         */

        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        /*
        Creating Adapter and setting that adapter to the viewPager
        setSupportActionBar method takes the toolbar and sets it as
        the default action bar thus making the toolbar work like a normal
        action bar.
         */
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        //setSupportActionBar(toolbar);

        /*
        TabLayout.newTab() method creates a tab view, Now a Tab view is not the view
        which is below the tabs, its the tab itself.
         */

        final TabLayout.Tab home = tabLayout.newTab();
        final TabLayout.Tab inbox = tabLayout.newTab();
        final TabLayout.Tab star = tabLayout.newTab();

        /*
        Setting Title text for our tabs respectively
         */

        home.setText("Home");
        inbox.setText("Inbox");
        star.setText("Star");

        /*
        Adding the tab view to our tablayout at appropriate positions
        As I want home at first position I am passing home and 0 as argument to
        the tablayout and like wise for other tabs as well
         */
        tabLayout.addTab(home, 0);
        tabLayout.addTab(inbox, 1);
        tabLayout.addTab(star, 2);

        /*
        TabTextColor sets the color for the title of the tabs, passing a ColorStateList here makes
        tab change colors in different situations such as selected, active, inactive etc

        TabIndicatorColor sets the color for the indiactor below the tabs
         */

        //tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        /*
        Adding a onPageChangeListener to the viewPager
        1st we add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        changes when a viewpager page changes.
         */

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






//        final TextView xField;
//        final TextView yField;
//        final TextView zField;
//
//        final List<Bean> beans = new ArrayList<>();
//
//        final Context self = this;
//
//        xField = (TextView) findViewById(R.id.x_field);
//        yField = (TextView) findViewById(R.id.y_field);
//        zField = (TextView) findViewById(R.id.z_field);
//
//        BeanDiscoveryListener listener = new BeanDiscoveryListener() {
//            @Override
//            public void onBeanDiscovered(Bean bean, int rssi) {
//                beans.add(bean);
//            }
//
//            @Override
//            public void onDiscoveryComplete() {
//                for (final Bean bean : beans) {
//                    Log.w(TAG, bean.getDevice().getName());       // "Bean"              (example)
//                    Log.w(TAG, bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example)
//
//                    BeanListener beanListener = new BeanListener() {
//                        @Override
//                        public void onConnected() {
//                            Log.i(TAG, "connected to Bean!");
//                            bean.readDeviceInfo(new Callback<DeviceInfo>() {
//                                @Override
//                                public void onResult(DeviceInfo deviceInfo) {
//                                    Log.w(TAG, deviceInfo.hardwareVersion());
//                                    Log.w(TAG, deviceInfo.firmwareVersion());
//                                    Log.w(TAG, deviceInfo.softwareVersion());
//                                }
//                            });
//
//                            new Timer().scheduleAtFixedRate(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    bean.readTemperature(new Callback<Integer>() {
//                                        @Override
//                                        public void onResult(Integer result) {
//                                            Log.d(TAG, Integer.toString(result));
//                                        }
//                                    });
//                                    bean.readAcceleration(new Callback<Acceleration>() {
//                                        @Override
//                                        public void onResult(Acceleration result) {
//                                            xField.setText("x:" + String.format("%.6f", result.x()));
//                                            yField.setText("y:" + String.format("%.6f", result.y()));
//                                            zField.setText("z:" + String.format("%.6f", result.z()));
//                                        }
//                                    });
//                                }
//                            }, 0, 250);
//
//                        }
//
//                        @Override
//                        public void onConnectionFailed() {
//                            Log.w(TAG, "Bean connection failed!");
//                        }
//
//                        @Override
//                        public void onDisconnected() {
//                            Log.w(TAG, "Bean disconnected!");
//                        }
//
//                        @Override
//                        public void onSerialMessageReceived(byte[] data) {
//
//                        }
//
//                        @Override
//                        public void onScratchValueChanged(ScratchBank bank, byte[] value) {
//
//                        }
//
//                        @Override
//                        public void onError(BeanError error) {
//                            Log.w(TAG, "Bean error!");
//                        }
//
//                        @Override
//                        public void onReadRemoteRssi(int rssi) {
//
//                        }
//                    };
//
//                    bean.connect(self, beanListener);
//                }
//            }
//        };
//        int name = 0;
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                name);
//        BeanManager bm = BeanManager.getInstance();
//        bm.startDiscovery(listener);
//        Log.w(TAG, "The end of the story!");
    }

}
