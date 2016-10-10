package layout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mecs.hci.luggagetracker.R;


public class SecurityFragment extends Fragment {

    private boolean isLocked = false;
    private Button btn;

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

        btn = (Button) rootView.findViewById(R.id.securityBtn);
        btn.setOnClickListener(listener);
        btn.setBackgroundColor(Color.GREEN);

        rootView.findViewById(R.id.progress_bar).getBackground().setLevel(2500);

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
        btn.setText("Lock suitcase");
        btn.setBackgroundColor(Color.RED);
    }

    private void lock() {
        btn.setText("Unlock suitcase");
        btn.setBackgroundColor(Color.GREEN);
    }
}
