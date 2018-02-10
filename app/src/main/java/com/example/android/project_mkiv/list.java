package com.example.android.project_mkiv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class list extends AppCompatActivity{

    static final String TAG = "list";

    public static final String Activity_tag = "WhichAct";
    public static final String primary = null;
    public static final String secondary = null;

    public static String actTag = null;
    public static String primNum;
    public static String secNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        actTag = extras.getString("Activity Tag");

        Log.d(TAG, "actTag is " + actTag);

        if (actTag.equals("1")) {
            primNum = extras.getString("Number");
            primNum = primNum.substring(1);
            primNum += "\n";
        } else if (actTag.equals("2")) {
            secNum = extras.getString("Number");
            secNum = secNum.substring(1);
            secNum += "\n";
        }

        Log.d(TAG, "primNum is " + primNum);


        TextView textPrimary = (TextView) findViewById(R.id.prim);
        textPrimary.setText(primNum);

        if (secNum != null) {
            TextView textSecondary = (TextView) findViewById(R.id.sec);
            textSecondary.setText(secNum);
        }
        //setTag();
    }

    public void editP(View view) {
        Intent i = new Intent(this, editp.class);
        startActivity(i);
    }

    public void editS(View view) {
        Intent i = new Intent(this, enter2.class);
        startActivity(i);
    }
}
