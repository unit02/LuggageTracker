package mecs.hci.luggagetracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import mecs.hci.luggagetracker.sensors.AccelerometerFragment;
import mecs.hci.luggagetracker.sensors.LightFragment;
import mecs.hci.luggagetracker.sensors.LocationFragment;
import mecs.hci.luggagetracker.sensors.LogFragment;
import mecs.hci.luggagetracker.sensors.SecurityFragment;
import mecs.hci.luggagetracker.sensors.TemperatureFragment;

public class SensorViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SecurityFragment(), "Security");
        adapter.addFragment(new AccelerometerFragment(), "Motion");
        adapter.addFragment(new LightFragment(), "Light");
        adapter.addFragment(new TemperatureFragment(), "Temperature");
        adapter.addFragment(new LocationFragment(), "Location");
        adapter.addFragment(new LogFragment(), "Log");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }



}
