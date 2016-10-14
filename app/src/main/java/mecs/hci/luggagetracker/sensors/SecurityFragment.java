package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import mecs.hci.luggagetracker.R;


public class SecurityFragment extends Fragment {

    private boolean isLocked = false;
    private Switch btn;
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
        btn = (Switch) rootView.findViewById(R.id.securityBtn);
        btn.setOnClickListener(listener);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");

        //rootView.findViewById(R.id.progressBar).getBackground().setLevel(5000);
        unlock();

        ImageView img = (ImageView) rootView.findViewById(R.id.helpBtn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });

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
        btn.setChecked(false);
//        btn.setTextColor(Color.GREEN);
        imageView.setImageResource(R.drawable.unlocked_icon);
        isLocked = false;
        Context context = getContext();
        CharSequence text = "Security is off";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void lock() {
        btn.setChecked(true);
//        btn.setTextColor(Color.RED);
        imageView.setImageResource(R.drawable.locked_icon);
        isLocked = true;
        Context context = getContext();
        CharSequence text = "Security is on";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void showHelp() {
        new AlertDialog.Builder(getContext())
                .setTitle("Security")
                .setMessage("This screen can be used to activate and deactivate the luggages alarm. When armed the alarm will go off if the lock is tampered with or the light sensor indicates the bag is opened.")
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
