package com.codegainz.ndani.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Tag;
import com.codegainz.ndani.engine.model.Tags;
import com.codegainz.ndani.engine.model.Token;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Stuart on 25/10/15.
 */
public class TagsFilterDialog extends DialogFragment {

    private List<TagView> tagViews = new ArrayList<>();
    private FlowLayout flowLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);
        flowLayout = (FlowLayout) view.findViewById(R.id.flowLayout);

        getDialog().setTitle("Select filters");

        Token token = new RushSearch().findSingle(Token.class);
        Call<Tags> tagsCall = ((NdaniApplication)getActivity().getApplication()).getServerApi().tags(token.getToken());
        tagsCall.enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Response<Tags> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    for (Tag tag : response.body().getQuestionTags()) {
                        TagView tagView = new TagView(getContext());
                        tagViews.add(tagView);
                        tagView.setText(tag.getName());
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

        return view;
    }
}
