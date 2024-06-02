package com.example.wenewsapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.wenewsapp.R;
import com.example.wenewsapp.fragment.BusinessFragment;
import com.example.wenewsapp.fragment.CultureFragment;
import com.example.wenewsapp.fragment.EnvironmentFragment;
import com.example.wenewsapp.fragment.FashionFragment;
import com.example.wenewsapp.fragment.HomeFragment;
import com.example.wenewsapp.fragment.ScienceFragment;
import com.example.wenewsapp.fragment.SocietyFragment;
import com.example.wenewsapp.fragment.SportFragment;
import com.example.wenewsapp.fragment.WorldFragment;


public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public CategoryFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new WorldFragment();
            case 2:
                return new ScienceFragment();
            case 3:
                return new SportFragment();
            case 4:
                return new EnvironmentFragment();
            case 5:
                return new SocietyFragment();
            case 6:
                return new FashionFragment();
            case 7:
                return new BusinessFragment();
            case 8:
                return new CultureFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 9; // Number of categories
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.ic_title_home);
            case 1:
                return mContext.getString(R.string.ic_title_world);
            case 2:
                return mContext.getString(R.string.ic_title_science);
            case 3:
                return mContext.getString(R.string.ic_title_sport);
            case 4:
                return mContext.getString(R.string.ic_title_environment);
            case 5:
                return mContext.getString(R.string.ic_title_society);
            case 6:
                return mContext.getString(R.string.ic_title_fashion);
            case 7:
                return mContext.getString(R.string.ic_title_business);
            case 8:
                return mContext.getString(R.string.ic_title_culture);
            default:
                return null;
        }
    }
}
