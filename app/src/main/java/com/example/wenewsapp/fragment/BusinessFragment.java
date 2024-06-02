package com.example.wenewsapp.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.Loader;

import com.example.wenewsapp.News;
import com.example.wenewsapp.NewsLoader;
import com.example.wenewsapp.NewsPreferences;
import com.example.wenewsapp.R;

import java.util.List;

public class BusinessFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = BusinessFragment.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String businessUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.business));
        Log.e(LOG_TAG, businessUrl);

        return new NewsLoader(getActivity(),businessUrl);
    }
}
