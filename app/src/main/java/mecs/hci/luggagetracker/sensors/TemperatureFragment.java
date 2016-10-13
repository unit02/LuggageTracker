package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.Callback;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mecs.hci.luggagetracker.CurrentBean;
import mecs.hci.luggagetracker.R;


public class TemperatureFragment extends Fragment {

    public static String TAG = "TemperatureFragment";

    Bean bean;

    private TextView temperatureTextView;
    private TextView mTitle;
    private TextView mTemperatureCurrent;
    private ProgressBar progressBar;

    private Timer timer;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        temperatureTextView = (TextView)rootView.findViewById(R.id.currentTempTextView);
        mTemperatureCurrent = (TextView)rootView.findViewById(R.id.currentTempertureTitleTextView);
        mTitle = (TextView) rootView.findViewById(R.id.title);

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");
        temperatureTextView.setTypeface(custom_font);
        mTitle.setTypeface(custom_font);
        mTemperatureCurrent.setTypeface(custom_font);
        bean = CurrentBean.getBean();

        ImageView img = (ImageView) rootView.findViewById(R.id.helpBtn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });

        if (bean != null) {
            startMonitoringTemperature();
        } else {
            Toast.makeText(getActivity(), "Bean not connected",
                    Toast.LENGTH_SHORT).show();
            startFakingTemperature();
        }

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
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bean.readTemperature(new Callback<Integer>() {
                    @Override
                    public void onResult(final Integer result) {
                        Log.d(TAG, "Current Temperature is: " + Integer.toString(result));
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                              temperatureTextView.setText(Integer.toString(result));
                                // set progress between 250 and 500
                                progressBar.setProgress((int)((result*(250/30.0))+250));
                            }
                        });
                    }
                });
            }
        }, 0, 250);
    }

    private void startFakingTemperature(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final int temp;
                int ran = new Random().nextInt(10);
                if (ran == 3) {
                    //temp = 25;
                    if (new Random().nextInt(10) == 10) {
                        temp = 26;
                        // TODO trigger notification thingy
                    } else {
                        temp = 25;
                    }
                } else if(ran == 4) {
                    temp = 23;
                } else {
                    temp = 24;
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        temperatureTextView.setText(Integer.toString(temp));
                        // set progress between 250 and 500
                        progressBar.setProgress((int)((temp*(250/30.0))+250));
                    }
                });
            }
        }, 0, 250);
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
        if (bean != null) {
            startMonitoringTemperature();
        } else {
            Toast.makeText(getActivity(), "Bean not connected",
                    Toast.LENGTH_SHORT).show();
            startFakingTemperature();
        }
        super.onResume();
    }

    private void showHelp() {
        new AlertDialog.Builder(getContext())
                .setTitle("Temperature")
                .setMessage("Use the temperature sensor to ensure your goods remain in the optimal conditions. If the suitcases temperature exceeds 25 degrees it will be recorded in the log")
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // continue with delete
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
                .setIcon(R.drawable.question_mark_dark)
                .show();
    }
}
