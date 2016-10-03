package mecs.hci.luggagetracker;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.Acceleration;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.DeviceInfo;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "LuggageTracker.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        final TextView xField;
        final TextView yField;
        final TextView zField;

        final List<Bean> beans = new ArrayList<>();

        final Context self = this;

        xField = (TextView) findViewById(R.id.x_field);
        yField = (TextView) findViewById(R.id.y_field);
        zField = (TextView) findViewById(R.id.z_field);

        BeanDiscoveryListener listener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi) {
                beans.add(bean);
            }

            @Override
            public void onDiscoveryComplete() {
                for (final Bean bean : beans) {
                    Log.w(TAG, bean.getDevice().getName());       // "Bean"              (example)
                    Log.w(TAG, bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example)

                    BeanListener beanListener = new BeanListener() {
                        @Override
                        public void onConnected() {
                            Log.i(TAG, "connected to Bean!");
                            bean.readDeviceInfo(new Callback<DeviceInfo>() {
                                @Override
                                public void onResult(DeviceInfo deviceInfo) {
                                    Log.w(TAG, deviceInfo.hardwareVersion());
                                    Log.w(TAG, deviceInfo.firmwareVersion());
                                    Log.w(TAG, deviceInfo.softwareVersion());
                                }
                            });

                            new Timer().scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    bean.readAcceleration(new Callback<Acceleration>() {
                                        @Override
                                        public void onResult(Acceleration result) {
                                            xField.setText("x:" + String.format("%.6f", result.x()));
                                            yField.setText("y:" + String.format("%.6f", result.y()));
                                            zField.setText("z:" + String.format("%.6f", result.z()));
                                        }
                                    });
                                }
                            }, 0, 250);

                        }

                        @Override
                        public void onConnectionFailed() {
                            Log.w(TAG, "Bean connection failed!");
                        }

                        @Override
                        public void onDisconnected() {
                            Log.w(TAG, "Bean disconnected!");
                        }

                        @Override
                        public void onSerialMessageReceived(byte[] data) {

                        }

                        @Override
                        public void onScratchValueChanged(ScratchBank bank, byte[] value) {

                        }

                        @Override
                        public void onError(BeanError error) {
                            Log.w(TAG, "Bean error!");
                        }

                        @Override
                        public void onReadRemoteRssi(int rssi) {

                        }
                    };

                    bean.connect(self, beanListener);
                }
            }
        };
        int name = 0;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                name);
        BeanManager bm = BeanManager.getInstance();
        bm.startDiscovery(listener);
        Log.w(TAG, "The end of the story!");
    }

}
