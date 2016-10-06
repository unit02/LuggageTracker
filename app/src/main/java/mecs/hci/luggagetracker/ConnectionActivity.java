package mecs.hci.luggagetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.punchthrough.bean.sdk.Bean;
import com.punchthrough.bean.sdk.BeanDiscoveryListener;
import com.punchthrough.bean.sdk.BeanListener;
import com.punchthrough.bean.sdk.BeanManager;
import com.punchthrough.bean.sdk.message.BeanError;
import com.punchthrough.bean.sdk.message.Callback;
import com.punchthrough.bean.sdk.message.DeviceInfo;
import com.punchthrough.bean.sdk.message.ScratchBank;

import java.util.ArrayList;
import java.util.List;

public class ConnectionActivity extends AppCompatActivity {

    public static String TAG = "ConnectionActivity";

    private TextView loadingProgressTextView;
    //private BluetoothAdapter bluetoothAdapter;
    //private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        loadingProgressTextView = (TextView) findViewById(R.id.loadingTextView) ;
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        loadingSpinner = (ProgressBar) findViewById(R.id.loadingSpinner);

        //TODO after 45 seconds restart activity
        //TODO make it so that the listner is set up once bluetooth is on
//        if (!bluetoothAdapter.isEnabled())
//        {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            int REQUEST_ENABLE_BT = 1;
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }

        setUpListener();
    }

    public void moveToSensorView(View v) {
        Intent intent = new Intent(this, SensorViewActivity.class);
        startActivity(intent);
    }

    private void setUpListener(){
        final Context self = this;
        final List<Bean> beans = new ArrayList<>();

        loadingProgressTextView.setText(R.string.bean_searching);

        BeanDiscoveryListener listener = new BeanDiscoveryListener() {
            @Override
            public void onBeanDiscovered(Bean bean, int rssi) {
                beans.add(bean);
            }

            @Override
            public void onDiscoveryComplete() {
                if (beans.size() == 0) {
                    loadingProgressTextView.setText(R.string.bean_failed);
                }

                for (final Bean bean : beans) {
                    Log.w(TAG, bean.getDevice().getName());       // "Bean"              (example)
                    Log.w(TAG, bean.getDevice().getAddress());    // "B4:99:4C:1E:BC:75" (example)

                    BeanListener beanListener = new BeanListener() {
                        @Override
                        public void onConnected() {
                            loadingProgressTextView.setText(R.string.bean_success);
                            Log.i(TAG, "Connected to Bean!");
                            bean.readDeviceInfo(new Callback<DeviceInfo>() {
                                @Override
                                public void onResult(DeviceInfo deviceInfo) {
                                    Log.w(TAG, deviceInfo.hardwareVersion());
                                    Log.w(TAG, deviceInfo.firmwareVersion());
                                    Log.w(TAG, deviceInfo.softwareVersion());
                                }
                            });
                            Intent intent = new Intent(ConnectionActivity.this, SensorViewActivity.class);
                            startActivity(intent);
                            CurrentBean.setBean(bean);
                        }

                        @Override
                        public void onConnectionFailed() {
                            Log.w(TAG, "Bean connection failed! Try again");
                            setUpListener();
                        }

                        @Override
                        public void onDisconnected() {
                            Log.w(TAG, "Bean disconnected!");
                            setUpListener();
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
                            setUpListener();
                        }

                        @Override
                        public void onReadRemoteRssi(int rssi) {

                        }
                    };

                    bean.connect(self, beanListener);
                }
            }
        };
        BeanManager bm = BeanManager.getInstance();
        //bm.setScanTimeout(45);
        bm.startDiscovery(listener);
    }

}
