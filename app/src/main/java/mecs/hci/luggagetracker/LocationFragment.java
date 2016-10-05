package mecs.hci.luggagetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.Callback;

import java.util.Timer;
import java.util.TimerTask;


public class LocationFragment extends Fragment {

    public static String TAG = "LocationFragment";

    Bean bean;

    private TextView LocationTextView;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_location, container, false);
        LocationTextView = (TextView)rootView.findViewById(R.id.currentTempTextView);
        bean = CurrentBean.getBean();
        startMonitoringLocation();

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void startMonitoringLocation(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
          //upadte location on google maps every 5 seconds or so
            }
        }, 0, 250);    }


}
