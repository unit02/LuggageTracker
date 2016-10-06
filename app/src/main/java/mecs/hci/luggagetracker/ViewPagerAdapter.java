package mecs.hci.luggagetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sensors.AccelerometerFragment;
import sensors.LightFragment;
import sensors.TemperatureFragment;


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
        }
        return new LightFragment();
    }

    @Override
    public int getCount() {
        return 3;           // As there are only 3 Tabs
    }



}
