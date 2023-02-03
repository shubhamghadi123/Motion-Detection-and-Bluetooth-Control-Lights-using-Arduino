package com.example.arduinolightscontroller;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SwitchCompat bt_switch;
    Button show_devices;
    TextView btText;
    ImageView bulb_image;
    Spinner bulb_time;
    BluetoothAdapter btAdapter;
    final int[] bulb = {0};
    static int PERMISSION_CODE = 100;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static SendReceive sendReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_switch = findViewById(R.id.bt_switch);
        show_devices = findViewById(R.id.show_btdevices);
        btText = findViewById(R.id.btCon);
        bulb_image = findViewById(R.id.bulbimage);
        bulb_time = findViewById(R.id.bulb_time);
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        //checking if permission is given or not
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, PERMISSION_CODE);
        }

        //if bluetooth is on the switch will be check and if off then switch will be unchecked
        if (btAdapter.isEnabled()) {
            bt_switch.setChecked(true);
            show_devices.setVisibility(View.VISIBLE);
        } else {
            bt_switch.setChecked(false);
            show_devices.setVisibility(View.INVISIBLE);
        }

        //transfer to bt_devicelist activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("EXTRA_ADDRESS");
        btText.setText(name);

        //setting resources for bulb_time_spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time_bulb, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bulb_time.setAdapter(adapter);
        bulb_time.setOnItemSelectedListener(this);
    }
        //switch
    public void btSwitch(View view) {
        if (bt_switch.isChecked()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            btAdapter.enable();
            Log.i(TAG, "Bluetooth Enabled");
            Toast.makeText(MainActivity.this,"Bluetooth Enabled",Toast.LENGTH_SHORT).show();
            show_devices.setVisibility(View.VISIBLE);
        }
        else{
            btAdapter.disable();
            Log.i(TAG, "Bluetooth Disabled");
            Toast.makeText(MainActivity.this, "Bluetooth Disabled", Toast.LENGTH_SHORT).show();
            show_devices.setVisibility(View.INVISIBLE);
            btText.setVisibility(View.INVISIBLE);
            bulb_image.setVisibility(View.INVISIBLE);
            bulb_time.setVisibility(View.INVISIBLE);
        }
    }
        //show devices onClick
    public void showDevices(View view) {
        Intent intent = new Intent(MainActivity.this, bt_devicelist.class);
        startActivity(intent);
    }
        //bulb image onClick
    public void bulbImage(View view) {
        if (bulb[0] == 0) {
            bulb_image.setImageResource(R.drawable.bulb_on);
            Toast.makeText(MainActivity.this, "Bulb Turned ON", Toast.LENGTH_SHORT).show();
            String string = String.valueOf('1');
            bulb[0] = 1;
            Log.i(TAG, "string.getBytes()" + string);
            sendReceive.write(string.getBytes());
        } else if (bulb[0] == 1) {
            bulb_image.setImageResource(R.drawable.bulb_off);
            Toast.makeText(MainActivity.this, "Bulb Turned OFF", Toast.LENGTH_SHORT).show();
            String string = String.valueOf('0');
            bulb[0] = 0;
            Log.i(TAG, "string.getBytes()" + string);
            sendReceive.write(string.getBytes());
        }
    }
        //bulb_time_spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (position) {
            case 0:
                bulb_image.setImageResource(R.drawable.bulb_off);
                break;
            case 1:
                tenSeconds_bulb();
                break;
            case 2:
                twentySeconds_bulb();
                break;
            case 3:
                thirtySeconds_bulb();
                break;
        }
    }
        //bulb_time_spinner_methods
    private void tenSeconds_bulb() {
        Toast.makeText(MainActivity.this, "Bulb will be on for 10 seconds", Toast.LENGTH_SHORT).show();
        String string = String.valueOf('2');
        Log.i(TAG, "string.getBytes()" + string);
        sendReceive.write(string.getBytes());
        //change imageview
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_on), 10200);
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_off),100);
        animation.setOneShot(true);
        bulb_image.setImageDrawable(animation);
        animation.start();
    }

    private void twentySeconds_bulb() {
        Toast.makeText(MainActivity.this, "Bulb will be on for 20 seconds", Toast.LENGTH_SHORT).show();
        String string = String.valueOf('3');
        Log.i(TAG, "string.getBytes()" + string);
        sendReceive.write(string.getBytes());
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_on), 20200);
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_off),100);
        animation.setOneShot(true);
        bulb_image.setImageDrawable(animation);
        animation.start();
    }

    private void thirtySeconds_bulb() {
        Toast.makeText(MainActivity.this, "Bulb will be on for 30 seconds", Toast.LENGTH_SHORT).show();
        String string = String.valueOf('4');
        Log.i(TAG, "string.getBytes()" + string);
        sendReceive.write(string.getBytes());
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_on), 30200);
        animation.addFrame(getResources().getDrawable(R.drawable.bulb_off),100);
        animation.setOneShot(true);
        bulb_image.setImageDrawable(animation);
        animation.start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //bluetooth connection
    public static class ClientClass extends Thread {

        private BluetoothDevice device;
        private BluetoothSocket socket;

        @SuppressLint("MissingPermission")
        public ClientClass(BluetoothDevice bt_device) {
            device = bt_device;
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Socket's create() method failed", e);
            }
        }
        //msg send
        @SuppressLint("MissingPermission")
        public void run() {
            try {
                socket.connect();
                Log.i(TAG, "Socket connected");
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Log.e(TAG, "Could not close the client socket", ex);
                }
                return;
            }
        }
    }

    private static class SendReceive extends Thread {

        private final BluetoothSocket bluetoothSocket;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket) {
            bluetoothSocket = socket;
            OutputStream tempOut = null;
            try {
                tempOut = bluetoothSocket.getOutputStream();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = tempOut;
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error occurred when sending data", e);
            }
        }
    }
}