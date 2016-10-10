package mecs.hci.luggagetracker.Models;


import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Event {
    private String time;
    private Type type;
    private Double xAccel;
    private Double yAccel;
    private Double zAccel;
    private int lux;
    private int temperature;
    private int longitude;
    private int latitude;


    public Event() {

    }

    public Event(String time, Type type, Double xAccel, Double yAccel, Double zAccel, int lux, int temperature, int longitude, int latitude) {
        this.time = time;
        this.type = type;
        this.xAccel = xAccel;
        this.yAccel = yAccel;
        this.zAccel = zAccel;
        this.lux = lux;
        this.temperature = temperature;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTime() {
        return this.time;
    }

    public Type getType() {
        return this.type;
    }
}
