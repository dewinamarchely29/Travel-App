package com.example.wenewsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public final class NewsPreferences {
    public static Uri.Builder getPreferredUri(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        String numOfItems = getPreferenceValue(context, sharedPrefs,
                R.string.settings_number_of_items_key, R.string.settings_number_of_items_default);
        String orderBy = getPreferenceValue(context, sharedPrefs,
                R.string.settings_order_by_key, R.string.settings_order_by_default);
        String orderDate = getPreferenceValue(context, sharedPrefs,
                R.string.settings_order_date_key, R.string.settings_order_date_default);
        String fromDate = getPreferenceValue(context, sharedPrefs,
                R.string.settings_from_date_key, R.string.settings_from_date_default);

        Uri baseUri = Uri.parse(context.getString(R.string.news_request_url)); // Assuming this URL is defined in strings.xml
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(context.getString(R.string.query_param), "");
        uriBuilder.appendQueryParameter(context.getString(R.string.order_by_param), orderBy);
        uriBuilder.appendQueryParameter(context.getString(R.string.page_size_param), numOfItems);
        uriBuilder.appendQueryParameter(context.getString(R.string.order_date_param), orderDate);
        uriBuilder.appendQueryParameter(context.getString(R.string.from_date_param), fromDate);
        uriBuilder.appendQueryParameter(context.getString(R.string.show_fields_param), context.getString(R.string.show_fields));
        uriBuilder.appendQueryParameter(context.getString(R.string.format_param), context.getString(R.string.format));
        uriBuilder.appendQueryParameter(context.getString(R.string.show_tags_param), context.getString(R.string.show_tags));
        uriBuilder.appendQueryParameter(context.getString(R.string.api_key_param), context.getString(R.string.api_key));

        return uriBuilder;
    }

    public static String getPreferredUrl(Context context, String section) {
        Uri.Builder uriBuilder = getPreferredUri(context);
        return uriBuilder.appendQueryParameter(context.getString(R.string.section_param), section).toString();
    }

    private static String getPreferenceValue(Context context, SharedPreferences sharedPrefs, int keyResId, int defaultResId) {
        String key = context.getString(keyResId);
        String defaultValue = context.getString(defaultResId);
        return sharedPrefs.getString(key, defaultValue);
    }
}
