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
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.Callback;

import java.util.Timer;
import java.util.TimerTask;


public class AccelerometerFragment extends Fragment {

    public static String TAG = "AccelerometerFragment";

    Bean bean;

    private TextView  XTextView;
    private TextView  YTextView;
    private TextView  ZTextView;

    public AccelerometerFragment() {
        // Required empty public constructor
    }


    public static AccelerometerFragment newInstance(String param1, String param2) {
        AccelerometerFragment fragment = new AccelerometerFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        XTextView = (TextView)rootView.findViewById(R.id.xAxisAccelerationTextView);
        YTextView = (TextView)rootView.findViewById(R.id.yAxisAccelerationTextView);
        ZTextView = (TextView)rootView.findViewById(R.id.zAxisAccelerationTextView);

        bean = CurrentBean.getBean();

        startMonitoringAccelerometer();

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

    private void startMonitoringAccelerometer(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bean.readAcceleration(new Callback<Acceleration>() {
                    @Override
                    public void onResult(final Acceleration result) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                XTextView.setText(Double.toString(result.x()));
                                YTextView.setText(Double.toString(result.y()));
                                ZTextView.setText(Double.toString(result.z()));
                            }
                        });
                    }
                });
            }
        }, 0, 250);    }


}
