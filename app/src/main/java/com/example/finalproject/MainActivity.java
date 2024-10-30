package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager connManager;
    NetworkInfo mWifi;
    NetworkInfo mMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Move the initialization here
        connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        ImageButton programsButton = findViewById(R.id.programsButton);
        programsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), ProgramsActivity.class);
                nextScreen.putExtra("message", "Hello from Schedule Activity");
                startActivity(nextScreen);
            }
        });

        ImageButton scheduleButton = findViewById(R.id.scheduleButton);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), ScheduleActivity.class);
                nextScreen.putExtra("message", "Hello from Schedule Activity");
                startActivity(nextScreen);
            }
        });

        ImageButton calendarButton = findViewById(R.id.calendarButton);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), NextActivity.class);
                nextScreen.putExtra("message", "Hello from MainActivity");
                startActivity(nextScreen);
            }
        });

        ImageButton contactButton = findViewById(R.id.contactButton);

        contactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), ContactActivity.class);
                nextScreen.putExtra("message", "Hello from MainActivity");
                startActivity(nextScreen);
            }
        });
        ImageButton newsButton = findViewById(R.id.NewsButton);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWifi.isConnected() == false && mMobile.isConnected() == false) {
                    showErrorView();
                } else {
                    System.out.println("Connected");
                    setContentView(R.layout.news_layout);


                    FileDownloader news = new FileDownloader("https://computingsystems.me/etcapp/news/news.xml", MainActivity.this);
                    news.setOnResultsListener(new AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Intent newsScreen = new Intent(getApplicationContext(), NewsActivity1.class);
                            newsScreen.putExtra("xmlData", output);
                           // findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            startActivity(newsScreen);
                        }
                    });
                    news.execute();
                }
            }


            private void showErrorView() {
                setContentView(R.layout.error_layout);
                TextView errorView = findViewById(R.id.errorMessage);
                errorView.setText("App cannot connect to network. Check network settings and try again.");
            }
        });

        ImageButton busButton = findViewById(R.id.busButton);
        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Website("http://www.metrobus.com");
            }
        });

        ImageButton emailButton = findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        Button infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show developer information using an AlertDialog
                showDeveloperInfo();
            }
        });
    }

    private void showDeveloperInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Developer Info");
        builder.setMessage("Name: Arielle Jessica Iba Bogaubi\nEmail: ibajessica243@gmail.com\nCyberSecurity Babe ;)\nGotta be proud of all minor achievements");

        // Add a button to close the dialog
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the AlertDialog
        builder.show();
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"arielle_jessica.iba_bogaubi@ed.cna.nl.ca"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ETC Programs Info");

        try {
            startActivity(Intent.createChooser(emailIntent, "Complete Action Using"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Website(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }
}
