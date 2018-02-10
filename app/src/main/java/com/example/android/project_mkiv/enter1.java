package com.example.android.project_mkiv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.example.android.project_mkiv.list.Activity_tag;

public class enter1 extends AppCompatActivity {

    String TAG = "enter1";

    public static final String tag = "BluLeUART";
    public static String actTag = null;

    EditText etSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter1);

        if(getIntent().getExtras() != null)
        {
            actTag = getIntent().getStringExtra(Activity_tag);
        }


    }

    public void click(View view)
    {
        EditText text = (EditText)findViewById(R.id.edit_message);
        String str = text.getText().toString();

        String tmp = "1" + str;
        Log.d(TAG, "Input is " + tmp);

        Intent i = new Intent(this,connecting.class);
        Bundle extras = new Bundle();
        extras.putString("Number", tmp);

        extras.putString("Activity Tag", "1");

        i.putExtras(extras);

        startActivity(i);
    }
}
