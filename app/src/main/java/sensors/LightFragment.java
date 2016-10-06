package sensors;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import mecs.hci.luggagetracker.CurrentBean;
import mecs.hci.luggagetracker.R;


public class LightFragment extends Fragment {

    public static String TAG = "lightFragment";

    private TextView lightTextView;

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
        int value = (int) (new Date().getTime()/1000);
        View rootView = inflater.inflate(R.layout.fragment_light, container, false);
        lightTextView = (TextView)rootView.findViewById(R.id.currentlightTextView);

        startMonitoringlight();

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

    private void startMonitoringlight(){

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              //update value ever 5 seconds
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //uget randomly changing numbers
                        lightTextView.setText("100 lux");
                    }
                });

            }
        }, 0, 250);    }


}
