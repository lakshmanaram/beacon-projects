package com.lakshmanaram.eddystone_url;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Beacon beacon = new Beacon.Builder()
                .setId1("https://google.com")
                .setId2("")
                .setId3("")
                .setManufacturer(0x015D) // Estimote Beacon - manufacturer I first bumped into.
                .setTxPower(-59)
                .setDataFields(Arrays.asList(new Long[]{0l}))
                .build();
// beacon layout for Eddystone-URL
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v");
        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {

            @Override
            public void onStartFailure(int errorCode) {
                Log.e(TAG, "Advertisement start failed with code: " + errorCode);
            }

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.i(TAG, "Advertisement start succeeded.");
            }
        });
    }
}
