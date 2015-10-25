package com.codegainz.ndani.ui.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codegainz.ndani.R;

public class ProfileActivity extends AppCompatActivity {

    public static String ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ProfileFragment.ID, getIntent().getStringExtra(ID));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment)
                .commit();
    }

}
