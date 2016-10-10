package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mecs.hci.luggagetracker.Models.Type;
import mecs.hci.luggagetracker.R;


public class LightFragment extends Fragment {

    public static String TAG = "lightFragment";

    private TextView lightTextView;
    private TextView mTitle;
    private TextView mLuxText;
    private ProgressBar progressBar;

    private Timer timer;
    private Random r;
    private FirebaseAuth mAuth;
    private List<TriggerListener> listeners = new ArrayList<TriggerListener>();


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
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");
        View rootView = inflater.inflate(R.layout.fragment_light, container, false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        lightTextView = (TextView)rootView.findViewById(R.id.currentlightTextView);
        mTitle = (TextView) rootView.findViewById(R.id.title);
        mLuxText = (TextView) rootView.findViewById(R.id.currentlightTitleTextView);
        lightTextView.setTypeface(custom_font);
        mTitle.setTypeface(custom_font);
        mLuxText.setTypeface(custom_font);

        mAuth = FirebaseAuth.getInstance();
        FirebaseResponder responder = new FirebaseResponder();
        addListener(responder);
        r = new Random();
        startMonitoringlight();


        return rootView;
    }

    public void addListener(TriggerListener toAdd) {
        listeners.add(toAdd);
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

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              //update value ever 5 seconds
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // randomly changing numbers
                        int lightIntensity = r.nextInt(120 - 75) + 75;
                        lightTextView.setText(lightIntensity + "");
                        // set progress between 250 and 500
                        progressBar.setProgress((int)((lightIntensity*(250/200.0))+250));
                        if (lightIntensity > 100) {
                            for (TriggerListener listener : listeners) {
                                    listener.significantEventOccurred(mAuth.getCurrentUser(), Type.LIGHT);
                                }
                        }
                    }
                });
            }
        }, 0, 5000);
    }

    @Override
    public void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (timer !=  null) {
            timer.cancel();
        }
        startMonitoringlight();
        super.onResume();
    }


}
