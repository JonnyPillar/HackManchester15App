package com.codegainz.ndani.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.engine.model.Tag;
import com.codegainz.ndani.ui.MainActivity;
import com.codegainz.ndani.ui.WebFragment;
import com.codegainz.ndani.ui.details.DetailsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stuart on 24/10/15.
 */
public class HomeFragment extends WebFragment implements NdaniApplication.TagSelectedChange {
    @Override
    public String getPath() {
        return "";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWebView().addJavascriptInterface(itemClicked, "nativeListener");
        ((NdaniApplication)getActivity().getApplication()).setTagSelectedChange(this);
    }

    private Object itemClicked = new Object() {
        @JavascriptInterface           // For API 17+
        public void performClick(String id) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.ID, id);
            getActivity().startActivity(intent);
        }
    };

    @Override
    public Map<String, String> getQueries() {
        Map<String, String> map = new HashMap<>();
        List<Tag> tags = ((NdaniApplication)getActivity().getApplication()).getSelectedTags();
        StringBuilder tagsString = new StringBuilder();
        for(Tag tag : tags){
            tagsString.append(tag.getName());
            tagsString.append(",");
        }
        if(tags.size() > 0) {
            tagsString.reverse();
            map.put("tags", tagsString.toString());
        }
        return map;
    }

    @Override
    public void change() {
        refresh();
    }
}
