package com.codegainz.ndani.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Comment;
import com.codegainz.ndani.engine.model.Token;
import com.codegainz.ndani.ui.MainActivity;
import com.codegainz.ndani.ui.TagsFilterDialog;
import com.codegainz.ndani.ui.VideoActivity;

import co.uk.rushorm.core.RushSearch;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailsActivity extends AppCompatActivity {

    public static final String ID = "ID";

    private FloatingActionButton fab;
    private EditText commentField;
    private DetailsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        commentField = (EditText) findViewById(R.id.comment);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addComment);

        fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DetailsFragment.ID, getIntent().getStringExtra(ID));
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Share");
                i.putExtra(Intent.EXTRA_TEXT, ((NdaniApplication)getApplication()).getBaseUrl() + "question/details/" + getIntent().getStringExtra(ID));
                startActivity(Intent.createChooser(i, "Share"));
                return true;
        }
        return false;
    }

    private View.OnClickListener addComment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            commentField.setEnabled(false);
            String commentText = commentField.getText().toString();
            Comment comment = new Comment(commentText, true, getIntent().getStringExtra(ID));
            Token token = new RushSearch().findSingle(Token.class);
            Call<Void> call = ((NdaniApplication)getApplication()).getServerApi().comment(token.getToken(), comment);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Response<Void> response, Retrofit retrofit) {
                    if(response.isSuccess()) {
                        commentField.setText(null);
                        commentField.setEnabled(true);
                        fragment.refresh();

                        Intent intent = new Intent(DetailsActivity.this, VideoActivity.class);
                        intent.putExtra(VideoActivity.CONFERENCE_ID, getIntent().getStringExtra(ID));
                        startActivity(intent);

                    } else {
                        onFailure(null);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    commentField.setEnabled(true);
                    Snackbar.make(findViewById(R.id.details_content), "Sorry failed to post", Snackbar.LENGTH_LONG);
                }
            });
        }
    };

}
