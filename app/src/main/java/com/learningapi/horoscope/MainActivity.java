package com.learningapi.horoscope;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {

    // creating class instance variables for our UI elements
    EditText Et1, Et2;
    Button Btn;
    TextView Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding UI elements to their Ids
        Et1 = (EditText)findViewById(R.id.et1);
        Et2 = (EditText)findViewById(R.id.et2);
        Btn = (Button)findViewById(R.id.btn);
        Tv = (TextView)findViewById(R.id.text_view);

        // Initializing python in onCreate function section of the app:
        // "context" must be an Activity, Service or Application object from your app.
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));

            // creating instance of python object and pointing to our script for backend logic
            Python python = Python.getInstance();
            PyObject pyobj = python.getModule("script");

            // give reference to our UI elements
            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                                                // Script function name,    //1st argument,     //2nd argument
                        PyObject object = pyobj.callAttr("main", Et1.getText().toString(), Et2.getText().toString());
                        // object will contain our result, now in line below, we set the result to textview
                        Tv.setText(object.toString());
                    } catch (PyException e) {

                        Toast.makeText(MainActivity.this, "Opps! Seems you forgot to pick either your Zodiac sign or day", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

    }

}
