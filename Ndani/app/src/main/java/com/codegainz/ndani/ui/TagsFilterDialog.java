package com.codegainz.ndani.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

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

                List<Tag> selected = ((NdaniApplication)getActivity().getApplication()).getSelectedTags();

                if (response.isSuccess()) {
                    for (Tag tag : response.body().getQuestionTags()) {
                        TagView tagView = new TagView(getContext());
                        tagView.setSelected(selected.contains(tag));
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

        view.findViewById(R.id.cancel).setOnClickListener(cancelListener);
        view.findViewById(R.id.filter).setOnClickListener(filterListener);

        return view;
    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener filterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<Tag> selected = new ArrayList<>();
            for(TagView tagView : tagViews) {
                if(tagView.isSelected()) {
                    selected.add(tagView.getTag());
                }
            }
            ((NdaniApplication)(getActivity().getApplication())).setSelectedTags(selected);
            dismiss();
        }
    };
}
