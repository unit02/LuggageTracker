package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import mecs.hci.luggagetracker.R;


public class SecurityFragment extends Fragment {

    private boolean isLocked = false;
    private Button btn;
    private TextView mTitle;
    private ImageView imageView;

    public SecurityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_security, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.imageView);
        btn = (Button) rootView.findViewById(R.id.securityBtn);
        btn.setOnClickListener(listener);

        mTitle.setTypeface(custom_font);

        //rootView.findViewById(R.id.progressBar).getBackground().setLevel(5000);
        unlock();

        return rootView;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isLocked) {
                unlock();
            } else {
                lock();
            }
        }
    };

    private void unlock() {
        btn.setText("Alarm OFF");
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageView.setImageResource(R.drawable.unlocked_icon);
        isLocked = false;
    }

    private void lock() {
        btn.setText("Alarm ON");
        btn.setTextColor(Color.DKGRAY);
        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        imageView.setImageResource(R.drawable.locked_icon);
        isLocked = true;
    }
}
