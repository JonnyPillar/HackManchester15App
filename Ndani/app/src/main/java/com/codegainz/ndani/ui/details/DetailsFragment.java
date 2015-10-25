package com.codegainz.ndani.ui.details;

import com.codegainz.ndani.ui.WebFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stuart on 24/10/15.
 */
public class DetailsFragment extends WebFragment {

    public static final String ID = "ID";

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
}
