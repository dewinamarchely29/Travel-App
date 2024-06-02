package com.example.wenewsapp.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.wenewsapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Load the preferences fragment
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new NewsPreferenceFragment())
                .commit();
    }

    public static class NewsPreferenceFragment extends PreferenceFragmentCompat
            implements Preference.OnPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_main, rootKey);

            Preference numOfItems = findPreference(getString(R.string.settings_number_of_items_key));
            bindPreferenceSummaryToValue(numOfItems);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference orderDate = findPreference(getString(R.string.settings_order_date_key));
            bindPreferenceSummaryToValue(orderDate);

            Preference colorTheme = findPreference(getString(R.string.settings_color_key));
            bindPreferenceSummaryToValue(colorTheme);

            Preference textSize = findPreference(getString(R.string.settings_text_size_key));
            bindPreferenceSummaryToValue(textSize);

            Preference fromDate = findPreference(getString(R.string.settings_from_date_key));
            setOnPreferenceClick(fromDate);
            bindPreferenceSummaryToValue(fromDate);

            Preference logoutPreference = findPreference(getString(R.string.settings_logout_key));
            setOnPreferenceClick(logoutPreference); // Set click listener for logout
        }

        private void setOnPreferenceClick(Preference preference) {
            preference.setOnPreferenceClickListener(preference1 -> {
                String key = preference1.getKey();
                if (key.equalsIgnoreCase(getString(R.string.settings_from_date_key))) {
                    showDatePicker();
                } else if (key.equalsIgnoreCase(getString(R.string.settings_logout_key))) {
                    showLogoutConfirmation();
                }
                return true;
            });
        }

        private void showDatePicker() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getActivity(), this, year, month, dayOfMonth).show();
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            month = month + 1;
            String selectedDate = year + "-" + month + "-" + dayOfMonth;
            String formattedDate = formatDate(selectedDate);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.settings_from_date_key), formattedDate).apply();

            Preference fromDatePreference = findPreference(getString(R.string.settings_from_date_key));
            bindPreferenceSummaryToValue(fromDatePreference);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            // Update the summary of a ListPreference using the label
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

        private String formatDate(String dateString) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
            try {
                Date dateObject = simpleDateFormat.parse(dateString);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.format(dateObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        private void showLogoutConfirmation() {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, which) -> logout(getActivity()))
                    .setNegativeButton("No", null)
                    .show();
        }

        private void logout(Activity activity) {
            // Retrieve the current login data
            SharedPreferences sharedPreferences = activity.getSharedPreferences("USER", AppCompatActivity.MODE_PRIVATE);

            // Clear only the "isLoggedIn" flag
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Navigate to the login screen
            Intent intent = new Intent(activity, IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                getActivity().finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}