package com.example.android.project_mkiv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class editp extends AppCompatActivity {

    String TAG = "Edit1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editp);
    }

    public void click(View view) {
        EditText text = (EditText) findViewById(R.id.edit_message);
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
