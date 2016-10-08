package mecs.hci.luggagetracker.sensors;


import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import mecs.hci.luggagetracker.CurrentBean;
import mecs.hci.luggagetracker.Models.Type;

public class TriggerSensor {

    private List<TriggerListener> listeners = new ArrayList<TriggerListener>();

    public void addListener(TriggerListener toAdd) {
        listeners.add(toAdd);
    }



}

interface TriggerListener {
    void significantEventOccurred(FirebaseUser user, CurrentBean bean, Type type);
}