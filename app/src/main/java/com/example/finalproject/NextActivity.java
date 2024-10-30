package com.example.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NextActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "EventPrefs";
    private static final String EVENT_KEY_PREFIX = "event_";

    private CalendarView calendarView;
    private EditText eventEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);

        calendarView = findViewById(R.id.calendarView);
        eventEditText = findViewById(R.id.eventEditText);
        saveButton = findViewById(R.id.saveButton);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                showEventsForSelectedDate(year, month, day);
            }
        });

        saveButton.setOnClickListener(view -> {
            saveEvent();
        });
    }

    private void saveEvent() {
        String eventText = eventEditText.getText().toString().trim();

        if (!eventText.isEmpty()) {
            long selectedDateMillis = calendarView.getDate();
            String key = EVENT_KEY_PREFIX + selectedDateMillis;

            // Save event to SharedPreferences
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(key, eventText);
            editor.apply();

            // Notify the user that the event has been saved
            Toast.makeText(getApplicationContext(), "Event saved!", Toast.LENGTH_SHORT).show();

            // Clear the input field
            eventEditText.getText().clear();
        } else {
            // Notify the user that the input field is empty
            Toast.makeText(getApplicationContext(), "Please enter an event", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEventsForSelectedDate(int year, int month, int day) {
        long selectedDateMillis = calendarView.getDate();
        String key = EVENT_KEY_PREFIX + selectedDateMillis;

        // Retrieve event from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String eventText = preferences.getString(key, "");

        if (!eventText.isEmpty()) {
            // Display event for the selected date
            String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
            String eventMessage = "Event for " + formattedDate + ":\n" + eventText;
            Toast.makeText(getApplicationContext(), eventMessage, Toast.LENGTH_LONG).show();
        } else {
            // No event for the selected date
            Toast.makeText(getApplicationContext(), "No event for this date", Toast.LENGTH_SHORT).show();
        }
    }
}
