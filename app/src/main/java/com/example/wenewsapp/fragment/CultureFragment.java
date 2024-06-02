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

public class CultureFragment extends BaseArticlesFragment {

    private static final String LOG_TAG = CultureFragment.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String cultureUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.culture));
        Log.e(LOG_TAG, cultureUrl);

        return new NewsLoader(getActivity(), cultureUrl);
    }
}
