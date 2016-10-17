package mecs.hci.luggagetracker.sensors;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mecs.hci.luggagetracker.Models.Event;
import mecs.hci.luggagetracker.Models.Type;
import mecs.hci.luggagetracker.R;


public class LogFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Event, LogHolder> mRecyclerViewAdapter;

    private RecyclerView.Adapter mAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mLogRef;

    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private RelativeLayout mLayout;
    public LogFragment() {
        // Required empty public constructor
    }


    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
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
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        Context ctx = getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAuth = FirebaseAuth.getInstance();

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Montserrat-Regular.otf");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Log.d("CJ", sdf.format(new Date()));

        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.keepSynced(true);

        ImageView img = (ImageView) view.findViewById(R.id.helpBtn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showHelp();
            }
        });

        mLogRef = mRef.child("logs").child(mAuth.getCurrentUser().getUid());
        attachRecyclerViewAdapter();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;
        }

    }

    private void attachRecyclerViewAdapter() {
        Query lastFifty = mLogRef.limitToLast(50);
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<Event, LogHolder>(
                Event.class, R.layout.cardlayout_log, LogHolder.class, lastFifty) {

            @Override
            public void populateViewHolder(LogHolder logView, final Event log, int position) {
                logView.setTime(log.getTime());
                logView.setType(log.getType());

                logView.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DisplayMetrics dm = new DisplayMetrics();
                        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

                        final int width = dm.widthPixels;
                        final int height = dm.heightPixels;
                        mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View popupView = mLayoutInflater.inflate(R.layout.log_popup, null);
                        TextView popupText = (TextView) popupView.findViewById(R.id.log_info);
                        Resources res = getResources();

                        if (log.getType() == Type.LIGHT) {
                            String text = String.format(res.getString(R.string.light_popup), log.getTime());
                            popupText.setText(text);

                        }
                        else if (log.getType() == Type.TEMPERATURE) {
                            String text = String.format(res.getString(R.string.temp_popup), log.getTime());
                            popupText.setText(text);

                        }

                        else {
                            String text = String.format(res.getString(R.string.motion_popup), log.getTime());
                            popupText.setText(text);

                        }

                        PopupWindow popupWindow = new PopupWindow(popupView,
                                (int) (width * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT, true);
                        // If the PopupWindow should be focusable
                        popupWindow.setFocusable(true);

                        // If you need the PopupWindow to dismiss when when touched outside
                        popupWindow.setBackgroundDrawable(new ColorDrawable());

                        // Get the View's(the one that was clicked in the Fragment) location

                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                        popupWindow.setOutsideTouchable(true);
                    }
                });
            }
        };

        mRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                mLayoutManager.smoothScrollToPosition(mRecyclerView, null, mRecyclerViewAdapter.getItemCount());
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    // Class for the cards containing each test
    public static class LogHolder extends RecyclerView.ViewHolder {
        View mView;


        public LogHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        // Sets the name of the test into the cards
        public void setTime(String time) {
            TextView field = (TextView) mView.findViewById(R.id.log_time);
            field.setText(time);

        }

        // Sets the number of questions in the test into the cards
        public void setType(Type type) {
            TextView field = (TextView) mView.findViewById(R.id.log_type);
            field.setText(type.toString());
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void showHelp() {
        new AlertDialog.Builder(getContext())
                .setTitle("Log")
                .setMessage("The log shows all significant events which have occurred. In the log you can see which sensor recognized the event and at what time.")
                .setIcon(R.drawable.question_mark_dark)
                .show();
    }
}
