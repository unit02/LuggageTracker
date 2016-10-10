package mecs.hci.luggagetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mecs.hci.luggagetracker.sensors.AccelerometerFragment;
import mecs.hci.luggagetracker.sensors.LightFragment;
import mecs.hci.luggagetracker.sensors.LocationFragment;
import mecs.hci.luggagetracker.sensors.LogFragment;
import mecs.hci.luggagetracker.sensors.SecurityFragment;
import mecs.hci.luggagetracker.sensors.TemperatureFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SecurityFragment();
            case 1:
                return new AccelerometerFragment();
            case 2:
                return new LightFragment();
            case 3:
                return new TemperatureFragment();
            case 4:
                return new LocationFragment();
            case 5:
                return new LogFragment();
        }
        return new LightFragment();
    }

    @Override
    public int getCount() {
        return 6;           // As there are only 3 Tabs
    }



}
