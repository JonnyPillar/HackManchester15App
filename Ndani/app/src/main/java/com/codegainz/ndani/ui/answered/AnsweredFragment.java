package com.codegainz.ndani.ui.answered;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.codegainz.ndani.ui.WebFragment;
import com.codegainz.ndani.ui.details.DetailsActivity;

/**
 * Created by Stuart on 24/10/15.
 */
public class AnsweredFragment extends WebFragment {
    @Override
    public String getPath() {
        return "home/Answered";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWebView().addJavascriptInterface(itemClicked, "nativeListener");

    }

    private Object itemClicked = new Object() {
        @JavascriptInterface           // For API 17+
        public void performClick(String id) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.ID, id);
            getActivity().startActivity(intent);
        }
    };
}
