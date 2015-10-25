package com.codegainz.ndani.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.Poller;
import com.codegainz.ndani.engine.model.Question;
import com.codegainz.ndani.ui.add.AddActivity;
import com.codegainz.ndani.ui.details.DetailsFragment;

public class MainActivity extends AppCompatActivity {

    public static int VIDEO_CHAT = 1;

    private ViewPager viewPager;
    private Poller poller;

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

        poller = new Poller(new Poller.VideoCall() {
            @Override
            public void someOneEnteredVideo(final Question question) {

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Join video call?")
                        .setMessage(question.getTitle());

                AlertDialog dialog = builder.create();
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        intent.putExtra(VideoActivity.CONFERENCE_ID, question.getId());
                        startActivityForResult(intent, VIDEO_CHAT);
                    }
                });
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }, ((NdaniApplication) getApplication()).getServerApi());

        poller.startPoling();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == VIDEO_CHAT) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EndorseFragment fragment = new EndorseFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(EndorseFragment.ID, data.getStringExtra(DetailsFragment.ID));
                    fragment.setArguments(bundle);
                    fragment.show(getSupportFragmentManager(), "EndorseFragment");
                }
            }, 2000);

        }

    }
}
