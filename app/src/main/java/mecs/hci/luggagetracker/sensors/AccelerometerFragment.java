package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mecs.hci.luggagetracker.CurrentBean;
import mecs.hci.luggagetracker.Models.Type;
import mecs.hci.luggagetracker.R;


public class AccelerometerFragment extends Fragment {

    public static String TAG = "AccelerometerFragment";

    Bean bean;



//    private TextView  XTextView;
//    private TextView  YTextView;
//    private TextView  ZTextView;
//    private TextView mXLabel;
//    private TextView mYLabel;
//    private TextView mZLabel;
    private TextView mWarningLevel;
    private TextView mWarningLabel;
    private ImageView imageView;
    private TextView mTitle;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private Timer timer;
    private List<TriggerListener> listeners = new ArrayList<TriggerListener>();

    public AccelerometerFragment() {
        // Required empty public constructor
    }


    public static AccelerometerFragment newInstance(String param1, String param2) {
        return new AccelerometerFragment();
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
//        XTextView = (TextView) rootView.findViewById(R.id.xAxisAccelerationTextView);
//        YTextView = (TextView) rootView.findViewById(R.id.yAxisAccelerationTextView);
//        ZTextView = (TextView) rootView.findViewById(R.id.zAxisAccelerationTextView);
//        mXLabel = (TextView)rootView.findViewById(R.id.xLabel);
//        mYLabel = (TextView)rootView.findViewById(R.id.yLabel);
//        mZLabel = (TextView)rootView.findViewById(R.id.zLabel);
        mTitle = (TextView) rootView.findViewById(R.id.title);
        mWarningLevel = (TextView) rootView.findViewById(R.id.warningLevel);
        mWarningLabel = (TextView) rootView.findViewById(R.id.warningLabel);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageView = (ImageView)rootView.findViewById(R.id.luggageImage);

        bean = CurrentBean.getBean();

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");
//
//        ZTextView.setTypeface(custom_font);
//        YTextView.setTypeface(custom_font);
//        XTextView.setTypeface(custom_font);
//        mXLabel.setTypeface(custom_font);
//        mYLabel.setTypeface(custom_font);
//        mZLabel.setTypeface(custom_font);
//        mTitle.setTypeface(custom_font);
        mWarningLevel.setTypeface(custom_font);
        mWarningLabel.setTypeface(custom_font);
        ImageView img = (ImageView) rootView.findViewById(R.id.helpBtn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });

        FirebaseResponder responder = new FirebaseResponder();
        addListener(responder);

        if (bean != null) {
            startMonitoringAccelerometer();
        } else {
            Toast.makeText(getActivity(), "Bean not connected, sensor data will not work",
                    Toast.LENGTH_SHORT).show();
            startFakeAccelerometer();
        }

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

    private void startMonitoringAccelerometer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                bean.readAcceleration(new Callback<Acceleration>() {
                    @Override
                    public void onResult(final Acceleration result) {

                        final Acceleration acceleration = new AccelerationFake(result.x(),result.y(),result.z());
                        // detect movement
                        if (previousAccel != null) {
                            if (isKnocked(acceleration, previousAccel)) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        warningLevel.setText("BANG!");
                                        imageView.setImageResource(R.drawable.luggage_impact);
//                                for (TriggerListener listener : listeners) {
//                                    listener.significantEventOccurred(mAuth.getCurrentUser(), Type.MOTION);
//                                }
                                    }
                                });
                            } else if (accelerometerMoving(acceleration, previousAccel)) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        warningLevel.setText("MOVING");
                                        imageView.setImageResource(R.drawable.walking_with_luggage);
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        warningLevel.setText("STILL");
                                        imageView.setImageResource(R.drawable.luggage_still);
                                    }
                                });
                            }
                        }
                        previousAccel = acceleration;
                    }
                });
            }
        }, 0, 250);
    }

    private Random r = new Random();
    private Acceleration previousAccel;
    private void startFakeAccelerometer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // fake results
                //r.nextInt(80 - 75) + 75;
                double x = (r.nextDouble()*2) - 1;
                double y = (r.nextDouble()*2) - 1;
                double z = (r.nextDouble()*2) - 1;
                final Acceleration result = new AccelerationFake(x,y,z);

                // detect movement
                if (previousAccel != null) {
                    if (isKnocked(result, previousAccel)) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                mWarningLevel.setText("BANG!");
                                imageView.setImageResource(R.drawable.luggage_impact);
//                                for (TriggerListener listener : listeners) {
//                                    listener.significantEventOccurred(mAuth.getCurrentUser(), Type.MOTION);
//                                }
                            }
                        });
                    } else if (accelerometerMoving(result, previousAccel)) {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                mWarningLevel.setText("MOVING");
                                imageView.setImageResource(R.drawable.walking_with_luggage);
                            }
                        });
                    } else {
                         getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                mWarningLevel.setText("STILL");
                                imageView.setImageResource(R.drawable.luggage_still);
                            }
                        });
                    }
                }
                previousAccel = result;
            }
        }, 0, 750);
    }

    private double difference(double one, double two) {
        if (one > two) {
            return one - two;
        }
        return two - one;
    }

    // compares if the axis values are different which indicates its moving
    // currently if there is a big enough difference on any axis it gives true
    private boolean accelerometerMoving(Acceleration one, Acceleration two) {
        double threshold = 0.3;
        if (difference(one.x(), two.x()) > threshold) {
            return true;
        }
        if (difference(one.y(), two.y()) > threshold*2) {
            return true;
        }
        if (difference(one.z(), two.z()) > threshold*2) {
            return true;
        }
        return false;
    }

    private boolean isKnocked(Acceleration one, Acceleration two) {
        double threshold = 0.6;
        return (difference(one.x(), two.x()) > threshold &&
                difference(one.y(), two.y()) > threshold &&
                difference(one.z(), two.z()) > threshold);
    }

    @Override
    public void onPause() {
        if (timer !=  null) {
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
            startMonitoringAccelerometer();
        } else {
            Toast.makeText(getActivity(), "Bean not connected, sensor data will not work",
                    Toast.LENGTH_SHORT).show();
            startFakeAccelerometer();
        }
        super.onResume();
    }

    private void showHelp() {
        new AlertDialog.Builder(getContext())
                .setTitle("Motion")
                .setMessage("The suitcases motion is detected through the use of an accelerometer. It can detect three states: still, moving and impacts. If your suitcase is in an impact it will be recorded in the log")
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
