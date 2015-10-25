package com.codegainz.ndani.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codegainz.ndani.ui.answered.AnsweredFragment;
import com.codegainz.ndani.ui.asked.AskedFragment;
import com.codegainz.ndani.ui.home.HomeFragment;
import com.codegainz.ndani.ui.profile.MyProfileFragment;
import com.codegainz.ndani.ui.profile.ProfileFragment;

/**
 * Created by Stuart on 24/10/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public enum Page {
        HOME,
        ASKED,
        ANSWERED,
        PROFILE,
        COUNT
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (Page.values()[position]) {
            case HOME: return new HomeFragment();
            case ASKED: return new AskedFragment();
            case ANSWERED: return new AnsweredFragment();
            case PROFILE: return new MyProfileFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return Page.COUNT.ordinal();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (Page.values()[position]) {
            case HOME: return "Home";
            case ASKED: return "Asked";
            case ANSWERED: return "Answered";
            case PROFILE: return "Profile";
            default: return "";
        }
    }
}
