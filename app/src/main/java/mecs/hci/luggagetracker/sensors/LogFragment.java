package mecs.hci.luggagetracker.sensors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import mecs.hci.luggagetracker.Models.Log;
import mecs.hci.luggagetracker.Models.Type;
import mecs.hci.luggagetracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Log, LogHolder> mRecyclerViewAdapter;

    private RecyclerView.Adapter mAdapter;

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mLogRef;

    public LogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogFragment newInstance(String param1, String param2) {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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


        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.keepSynced(true);

        mLogRef = mRef.child("logs").child(mAuth.getCurrentUser().getUid());
        attachRecyclerViewAdapter();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void attachRecyclerViewAdapter() {
        Query lastFifty = mLogRef.limitToLast(50);
        mRecyclerViewAdapter = new FirebaseRecyclerAdapter<Log, LogHolder>(
                Log.class, R.layout.cardlayout_log, LogHolder.class, lastFifty) {

            @Override
            public void populateViewHolder(LogHolder logView, final Log log, int position) {
                logView.setTime(log.getTime());
                logView.setType(log.getType());

                logView.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(view.getContext(), LogDescriptionActivity.class);
//                        intent.putExtra("log", log);
//                        startActivity(intent);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
