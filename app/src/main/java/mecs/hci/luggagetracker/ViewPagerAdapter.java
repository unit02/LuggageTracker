package mecs.hci.luggagetracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import mecs.hci.luggagetracker.sensors.AccelerometerFragment;
import mecs.hci.luggagetracker.sensors.LightFragment;
import mecs.hci.luggagetracker.sensors.LocationFragment;
import mecs.hci.luggagetracker.sensors.LogFragment;
import mecs.hci.luggagetracker.sensors.SecurityFragment;
import mecs.hci.luggagetracker.sensors.TemperatureFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}
