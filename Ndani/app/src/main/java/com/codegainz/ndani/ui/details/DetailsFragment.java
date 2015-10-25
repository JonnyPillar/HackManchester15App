package com.codegainz.ndani.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.codegainz.ndani.ui.WebFragment;
import com.codegainz.ndani.ui.profile.ProfileActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stuart on 24/10/15.
 */
public class DetailsFragment extends WebFragment {

    public static final String ID = "ID";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWebView().addJavascriptInterface(itemClicked, "nativeListener");
    }

    @Override
    public String getPath() {
        return "question/details";
    }

    @Override
    public Map<String, String> getQueries() {
        Map<String, String> queries = new HashMap<>();
        queries.put("id", getArguments().getString(ID));
        return queries;
    }

    private Object itemClicked = new Object() {
        @JavascriptInterface           // For API 17+
        public void performClick(String id) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            intent.putExtra(ProfileActivity.ID, id);
            getActivity().startActivity(intent);
        }

    };
}
