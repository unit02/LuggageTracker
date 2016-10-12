package mecs.hci.luggagetracker.sensors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import mecs.hci.luggagetracker.R;


public class LocationFragment extends Fragment implements OnMapReadyCallback  {

    public static String TAG = "LocationFragment";

    private TextView LocationTextView;
    private GoogleMap mMap;


    private Timer timer;
    private TextView mTitle;


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
        mTitle = (TextView) rootView.findViewById(R.id.title);

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");
        mTitle.setTypeface(custom_font);

        startMonitoringLocation();

        ImageView img = (ImageView) rootView.findViewById(R.id.helpBtn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.lite_map);
        mapFragment.getMapAsync(this);

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void startMonitoringLocation(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
          //upadte location on google maps every 5 seconds or so
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
        startMonitoringLocation();
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Example LatLng at Mcdonalds Queen St
        LatLng luggage = new LatLng(-36.850171, 174.765191);
        mMap.addMarker(new MarkerOptions().position(luggage)
                .title("Luggage"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                luggage,
                15));

    }

    private void showHelp() {
        new AlertDialog.Builder(getContext())
                .setTitle("Location")
                .setMessage("The GPS within the luggage tracks its current location which is displayed on the map. It can be used to check your luggage is where you expect")
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
