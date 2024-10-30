package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.R;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);

        Bundle extras = getIntent().getExtras();
        String message = extras.getString("message");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView nextText = (TextView) findViewById(R.id.emailTextView);
        nextText.setText(message);

        


    }
}