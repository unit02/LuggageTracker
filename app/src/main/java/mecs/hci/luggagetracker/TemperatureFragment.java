package mecs.hci.luggagetracker;

import android.content.Context;
import android.net.Uri;
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
import java.util.concurrent.RunnableFuture;


public class TemperatureFragment extends Fragment {

    public static String TAG = "TemperatureFragment";

    Bean bean;

    private TextView temperatureTextView;

    private OnFragmentInteractionListener mListener;

    public TemperatureFragment() {
        // Required empty public constructor
    }


    public static TemperatureFragment newInstance(String param1, String param2) {
        TemperatureFragment fragment = new TemperatureFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = CurrentBean.getBean();
        temperatureTextView = (TextView) getActivity().findViewById(R.id.currentTempTextView);
        startMonitoringTemperature();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
