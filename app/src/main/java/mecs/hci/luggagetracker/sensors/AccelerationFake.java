package mecs.hci.luggagetracker.sensors;

import android.os.Parcel;
import android.os.Parcelable;

import com.punchthrough.bean.sdk.message.Acceleration;


public class AccelerationFake extends Acceleration {

    private double x;
    private double y;
    private double z;

    //private CREATOR

    public AccelerationFake(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Object[] newArray(int i) {
            return new Object[0];
        }
    };
}
