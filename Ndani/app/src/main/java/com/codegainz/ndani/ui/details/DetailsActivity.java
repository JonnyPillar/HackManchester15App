package com.codegainz.ndani.ui.details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Comment;
import com.codegainz.ndani.engine.model.Token;

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
