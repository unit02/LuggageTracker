package mecs.hci.luggagetracker.sensors;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import mecs.hci.luggagetracker.CurrentBean;
import mecs.hci.luggagetracker.Models.Type;


public class FirebaseResponder implements TriggerListener {
    private DatabaseReference mDatabase;
    private DatabaseReference mRef;


    @Override
    public void significantEventOccurred(final FirebaseUser user, final Type type) {
        final String userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("logs").child(userID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        updateLogs(userID, (int) dataSnapshot.getChildrenCount(), type);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void updateLogs(String id, int logNumber, Type type) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        logNumber++;
        mRef = mDatabase.child("logs").child(id).child(Integer.toString(logNumber));

        mRef.child("time").setValue(sdf.format(new Date()));
        mRef.child("type").setValue(type.toString());
        //TO-DO
        // PUSH UP ALL THE RELEVANT INFORMATION TO FIREBASE.

    }

}
