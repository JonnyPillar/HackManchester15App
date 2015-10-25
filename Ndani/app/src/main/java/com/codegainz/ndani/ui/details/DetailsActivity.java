package com.codegainz.ndani.ui.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codegainz.ndani.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DetailsFragment.ID, getIntent().getStringExtra(ID));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment)
                .commit();
    }

}
