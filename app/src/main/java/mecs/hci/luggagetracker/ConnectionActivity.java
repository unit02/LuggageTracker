package mecs.hci.luggagetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
    }

    public void moveToSensorView(View v) {
        Intent intent = new Intent(this, SensorViewActivity.class);
        startActivity(intent);
    }
}
