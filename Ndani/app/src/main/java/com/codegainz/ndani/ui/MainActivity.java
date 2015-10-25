package com.codegainz.ndani.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codegainz.ndani.R;
import com.codegainz.ndani.ui.add.AddActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(actionButtonClickListener);
    }

    private View.OnClickListener actionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
            /*
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(VideoActivity.CONFERENCE_ID, "Conference");
            startActivity(intent); */
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(viewPager.getCurrentItem() == 0) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.home, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
                TagsFilterDialog dialog = new TagsFilterDialog();
                dialog.show(getSupportFragmentManager(), "DIALOG");

                return true;
        }
        return false;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            invalidateOptionsMenu();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
