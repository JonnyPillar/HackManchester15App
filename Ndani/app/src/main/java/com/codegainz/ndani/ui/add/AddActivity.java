package com.codegainz.ndani.ui.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Question;
import com.codegainz.ndani.engine.model.QuestionId;
import com.codegainz.ndani.engine.model.Tag;
import com.codegainz.ndani.engine.model.Tags;
import com.codegainz.ndani.engine.model.Token;
import com.codegainz.ndani.ui.FlowLayout;
import com.codegainz.ndani.ui.MainActivity;
import com.codegainz.ndani.ui.TagView;
import com.codegainz.ndani.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class AddActivity extends AppCompatActivity {

    private FlowLayout flowLayout;
    private FloatingActionButton fab;
    private List<TagView> tagViews = new ArrayList<>();

    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addQuestion);

        Token token = new RushSearch().findSingle(Token.class);
        Call<Tags> tagsCall = ((NdaniApplication)getApplication()).getServerApi().tags(token.getToken());
        tagsCall.enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Response<Tags> response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    for (Tag tag : response.body().getQuestionTags()) {
                        TagView tagView = new TagView(AddActivity.this);
                        tagViews.add(tagView);
                        tagView.setTag(tag);
                        FlowLayout.LayoutParams flowLP = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
                        flowLayout.addView(tagView, flowLP);
                    }
                    flowLayout.requestLayout();
                }
            }
            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private View.OnClickListener addQuestion = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fab.setEnabled(false);
            String titleText = title.getText().toString();
            String descriptionText = description.getText().toString();
            List<Tag> tags = new ArrayList<>();
            for(TagView tagView : tagViews){
                if(tagView.isSelected()) {
                    tags.add(tagView.getTag());
                }
            }
            Question question = new Question(titleText, descriptionText, tags);
            Token token = new RushSearch().findSingle(Token.class);
            Call<QuestionId> call = ((NdaniApplication)getApplication()).getServerApi().question(token.getToken(), question);
            call.enqueue(new Callback<QuestionId>() {
                @Override
                public void onResponse(Response<QuestionId> response, Retrofit retrofit) {

                    if(response.isSuccess()) {
                        finish();
                        /*Intent intent = new Intent(AddActivity.this, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.ID, response.body().getQuestionId());
                        startActivity(intent);*/

                    }else{
                        onFailure(null);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    fab.setEnabled(true);
                }
            });

        }
    };

}
