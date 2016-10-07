package mecs.hci.luggagetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mecs.hci.luggagetracker.sensors.AccelerometerFragment;
import mecs.hci.luggagetracker.sensors.LightFragment;
import mecs.hci.luggagetracker.sensors.LocationFragment;
import mecs.hci.luggagetracker.sensors.TemperatureFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AccelerometerFragment();
            case 1:
                return new LightFragment();
            case 2:
                return new TemperatureFragment();
            case 3:
                return new LocationFragment();
        }
        return new LightFragment();
    }

    @Override
    public int getCount() {
        return 4;           // As there are only 3 Tabs
    }



}
