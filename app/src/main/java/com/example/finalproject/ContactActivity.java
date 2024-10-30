package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactActivity extends AppCompatActivity {

    String[] items = new String[]{"Call", "Write", "Visit", "Find"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        ListView contactListView = (ListView) findViewById(R.id.contactListView);
        contactListView.setAdapter(new ContactAdapter(this, items));
        contactListView.setTextFilterEnabled(true);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);

                if(selectedValue.equals("Call")) {
                     callPhoneNumber("tel:7097587091");
                }
                else if(selectedValue.equals("Write")) {
                   sendEmail();
                }
                else if(selectedValue.equals("Visit")) {
                    Website("https://www.cna.nl.ca/");
                }
                else if(selectedValue.equals("Find")) {
                    openGoogleMaps();
                }
            }
        });
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"arielle_jessica.iba_bogaubi@ed.cna.nl.ca"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ETC Programs Info");

        try {
            startActivity(Intent.createChooser(emailIntent, "Complete Action Using"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void Website(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }

    private void openGoogleMaps() {

        double latitude = 47.588100;
        double longitude = -52.734371;


        String geoUri = "geo:" + latitude + "," + longitude + "?z=14";


        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

       if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Start the activity with the Intent
            startActivity(mapIntent);
        } else {
            Website("https://www.google.com/maps?q=" + latitude + "," + longitude);
        }
    }

    private void callPhoneNumber(String phoneNumber) {
        // Create an intent to dial the phone number
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(phoneNumber));


        PackageManager packageManager = getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(dialIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfo != null) {
            startActivity(dialIntent);
        } else {


            startActivity(dialIntent);
        }
    }

    private void showNoDialerAppError() {
        // Display a toast message or show a dialog to inform the user
        Toast.makeText(getApplicationContext(), "No dialer app found. Please install a dialer app.", Toast.LENGTH_SHORT).show();
    }
}






