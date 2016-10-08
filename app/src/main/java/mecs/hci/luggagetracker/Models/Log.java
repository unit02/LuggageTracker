package mecs.hci.luggagetracker.Models;


import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Log {
    private String time;
    private Type type;


    public Log() {

    }

    public Log(String time, Type type) {
        this.time = time;
        this.type = type;
    }

    public String getTime() {
        return this.time;
    }

    public Type getType() {
        return this.type;
    }
}
