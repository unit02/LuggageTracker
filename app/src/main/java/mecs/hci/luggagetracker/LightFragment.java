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


public class LightFragment extends Fragment {

    public static String TAG = "TemperatureFragment";

    Bean bean;

    private TextView temperatureTextView;

    public LightFragment() {
        // Required empty public constructor
    }


    public static LightFragment newInstance(String param1, String param2) {
        LightFragment fragment = new LightFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);
        temperatureTextView = (TextView)rootView.findViewById(R.id.currentTempTextView);
        bean = CurrentBean.getBean();
        startMonitoringTemperature();

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

    private void startMonitoringTemperature(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bean.readTemperature(new Callback<Integer>() {
                    @Override
                    public void onResult(final Integer result) {
                        Log.d(TAG, "Current Temperature is: " + Integer.toString(result));
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                              temperatureTextView.setText(Integer.toString(result));
                            }
                        });
                    }
                });
            }
        }, 0, 250);    }


}
