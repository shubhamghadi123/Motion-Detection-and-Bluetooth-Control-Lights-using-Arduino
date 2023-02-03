package com.example.arduinolightscontroller;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Set;

public class bt_devicelist extends AppCompatActivity {

    ListView listView;
    BluetoothAdapter btAdapter;
    BluetoothDevice[] btArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_devicelist);
        listView = findViewById(R.id.listview);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDeviceList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.ClientClass clientClass = new MainActivity.ClientClass(btArray[i]);
                Log.i(TAG, "onItemClick: "+btArray[i]);
                Toast.makeText(bt_devicelist.this,"Connected to " +btArray[i].getName(),Toast.LENGTH_SHORT).show();
                clientClass.start();
                String info = ((TextView) view).getText().toString();
                Intent intent = new Intent(bt_devicelist.this, MainActivity.class);
                intent.putExtra("EXTRA_ADDRESS", info);
                startActivity(intent);
            }
        });
    }

    private void pairedDeviceList() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> bt = btAdapter.getBondedDevices();
        String[] strings = new String[bt.size()];
        btArray = new BluetoothDevice[bt.size()];
        int index = 0;
        if (btAdapter.isEnabled()) {
            if (bt.size() > 0) {
                for (BluetoothDevice device : bt) {
                    btArray[index] = device;
                    strings[index] = device.getName();
                    index++;
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listview_color, strings);
                listView.setAdapter(arrayAdapter);
            }
        }
    }
}