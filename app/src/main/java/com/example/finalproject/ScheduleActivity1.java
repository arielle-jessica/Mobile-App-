package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.DialogInterface;

import com.example.finalproject.CustomPagerAdapter;
import com.example.finalproject.R;

public class ScheduleActivity1 extends AppCompatActivity {
    final String[] page_titles = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};

    final String[] hours = new String[]{"9", "10", "11", "noon", "1", "2", "3", "4", "5", "6"};

    private String[][] days;
    private int pos;

    public static ArrayList<String> schedule = new ArrayList<String>();

    List<Map<String, String>>[] list = new List[5]; // Mon - Fri
    Map<String, String> map;
    String newURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newURL = getIntent().getStringExtra("loc");
        if (!isNetworkAvailable())
            new AlertDialog.Builder(ScheduleActivity1.this).
                    setTitle("Error").setMessage("No Network Connection").
                    setNeutralButton("Close", null).show();
        else {
            new GetXML().execute("");
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    private class GetXML extends AsyncTask<String, Void, String> {
        String src = null;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(newURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                src = readStream(con.getInputStream());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return src;
        }

        @Override
        protected void onPostExecute(String result) {
            if (src == null || src.isEmpty()) {
                new AlertDialog.Builder(ScheduleActivity1.this)
                        .setTitle("Error")
                        .setMessage("No Schedule Found")
                        .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
// Finish the current activity
                                finish();
                            }
                        })
                        .show();
            } else {
                parseXML(src);

                setContentView(R.layout.schedule_activity1);

                days = new String[5][10];
                int totalItems = schedule.size(); // Get the total number of items in the schedule

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 10; j++) {
                        int k = i * 10 + j;
                        if (k < totalItems) { // Check if the index is within the bounds
                            days[i][j] = schedule.get(k);
                        } else {
                            days[i][j] = ""; // Set an empty string if the index is out of bounds
                        }
                    }
                }

                for (int i = 0; i < 5; i++)
                    list[i] = new ArrayList<Map<String, String>>();

                int count = hours.length;

                for (int j = 0; j < 5; j++) {
                    for (int i = 0; i < count; i++) {
                        map = new HashMap<String, String>();
                        map.put("time", hours[i]);
                        map.put("description", days[j][i]);
                        list[j].add(map);
                    }
                }

                Calendar cal = Calendar.getInstance();
                int today = cal.get(Calendar.DAY_OF_WEEK) - 2;

                pos = 0;
                if (today >= 0 && today <= 4)
                    pos = today;

                ViewPager viewPager = findViewById(R.id.ViewPager);
                CustomPagerAdapter adapter = new CustomPagerAdapter(ScheduleActivity1.this, list);
                PagerTabStrip pagerTabStrip = findViewById(R.id.pager_tab);

                int color = Color.parseColor("#344966");
                pagerTabStrip.setTabIndicatorColor(color);

                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(pos);
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
    @Override
    public void onDestroy()
    {
        schedule.clear();
        super.onDestroy();
    }

    public void parseXML(String src) {
        try {
            StringReader sr = new StringReader(src);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(sr);

            int token = xpp.getEventType();
            while (token != XmlPullParser.END_DOCUMENT) {
                if (token == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("cid")) {
                        token = xpp.nextToken();

                        if (xpp.getText() == null)
                            schedule.add("");

                        if (token == XmlPullParser.TEXT) {
                            schedule.add(xpp.getText());
                        }
                    }
                }

                token = xpp.nextToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        String line = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}