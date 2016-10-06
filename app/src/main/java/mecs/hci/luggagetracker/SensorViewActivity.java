package mecs.hci.luggagetracker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class SensorViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Setup viewpageradapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        // Creating the tabs
        final TabLayout.Tab accelerometerTab = tabLayout.newTab();
        final TabLayout.Tab lightTab = tabLayout.newTab();
        final TabLayout.Tab temperatureTab = tabLayout.newTab();

        // Setting the title of the tabs
        accelerometerTab.setText("Accelerometer");
        lightTab.setText("Light");
        temperatureTab.setText("Temperature");

        // Add the tabs to the layout
        tabLayout.addTab(accelerometerTab, 0);
        tabLayout.addTab(lightTab, 1);
        tabLayout.addTab(temperatureTab, 2);

        // Setup tab colours
        //tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        //tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        // setting up the tabs to chage when the viewpager changes
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

}
