package com.example.android.project_mkiv;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class connecting extends AppCompatActivity implements BluetoothLeUart.Callback{

    private static final String TAG = "connecting";
    // UI elements
    private TextView messages;

    public static final String endFlag = null;

    public static String primNum = null;
    public static String ActTag = null;

    public static boolean firstFlag = true;
    public static boolean firstFlag2 = true;


    // Bluetooth LE UART instance.  This is defined in BluetoothLeUart.java.
    private static BluetoothLeUart uart;

    // Write some text to the messages text view.
    // Care is taken to do this on the main UI thread so writeLine can be called from any thread
    // (like the BTLE callback).
    private void writeLine(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.append(text);
                messages.append("\n");
            }
        });
    }

    // Handler for mouse click on the send button.
    public void sendClick(String number) {

        Log.d(TAG, "sendClick is starting");

        StringBuilder stringBuilder = new StringBuilder();
        String message = number;

        // We can only send 20 bytes per packet, so break longer messages
        // up into 20 byte payloads
        int len = message.length();
        int pos = 0;
        while(len != 0) {
            stringBuilder.setLength(0);
            if (len>=20) {
                stringBuilder.append(message.toCharArray(), pos, 20 );
                len-=20;
                pos+=20;
            }
            else {
                stringBuilder.append(message.toCharArray(), pos, len);
                len = 0;
            }
            Log.d(TAG, "what is being sent is: " + stringBuilder.toString());
            uart.send(stringBuilder.toString());
        }

        messages.setText(number + " has been sent.");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting);

        Log.d(TAG, "onCreate is called");

        // Grab references to UI elements.
        messages = (TextView) findViewById(R.id.messages);
        // Initialize UART.

        if(firstFlag == true)
        {
            Log.d(TAG, "creating a new uart instance");
            uart = new BluetoothLeUart(getApplicationContext());
        }

        firstFlag = false;

        // Enable auto-scroll in the TextView
        messages.setMovementMethod(new ScrollingMovementMethod());
    }

    // OnCreate, called once to initialize the activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // OnResume, called right before UI is displayed.  Connect to the bluetooth device.
    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getExtras() != null) {

            if(endFlag == null) {
                messages.setText("");
                messageConfirm();
            }
            else
            {
                Stop();
            }
        }


        if(firstFlag2 == true) {
            Log.d(TAG, "making a new connection");
            //writeLine("Scanning for devices ...");
            uart.registerCallback(this);
            uart.connectFirstAvailable();
        }
    }

    protected void Stop() {
        uart.unregisterCallback(this);
        uart.disconnect();
    }


    // UART Callback event handlers.
    @Override
    public void onConnected(BluetoothLeUart uart) {
        // Called when UART device is connected and ready to send/receive data.
        writeLine("Connected!");
    }

    private void toEnter1() {

        Intent i = new Intent(this, enter1.class);

        startActivity(i);
    }

    @Override
    public void onConnectFailed(BluetoothLeUart uart) {
        // Called when some error occured which prevented UART connection from completing.
        writeLine("Error connecting to device!");
    }

    @Override
    public void onDisconnected(BluetoothLeUart uart) {
        // Called when the UART device disconnected.
        writeLine("Disconnected!");
        // Disable the send button.
    }

    @Override
    public void onReceive(BluetoothLeUart uart, BluetoothGattCharacteristic rx) {
        // Called when data is received by the UART.
        writeLine("Received: " + rx.getStringValue(0));
    }

    @Override
    public void onDeviceFound(BluetoothDevice device) {
        // Called when a UART device is discovered (after calling startScan).
        writeLine("Found device : " + device.getAddress());
        writeLine("Waiting for a connection ...");
    }

    @Override
    public void onDeviceInfoAvailable() {
        if(firstFlag2==true){
            writeLine(uart.getDeviceInfo());
            firstFlag2 = false;
            toEnter1();
        }
    }

    public void messageConfirm()
    {
        if (getIntent().getExtras() != null) {                  // we have numbers from list class to send. else it just goes back to list
            Bundle extras = getIntent().getExtras();
            primNum = extras.getString("Number");
            primNum += "\n";

            ActTag = extras.getString("Activity Tag");

            Log.d(TAG, "primNum in " + TAG + " is " + primNum);
            Log.d(TAG, "ActTag in " + TAG + " is " + ActTag);



            Log.d(TAG, "calling sendClick for primNum");
            sendClick(primNum);
        }

        Log.d(TAG, "Numbers sent :D");

        Intent i=new Intent(this,list.class);
        Bundle extras = new Bundle();
        extras.putString("Number", primNum);
        extras.putString("Activity Tag", ActTag);

        i.putExtras(extras);
        startActivity(i);
    }

}