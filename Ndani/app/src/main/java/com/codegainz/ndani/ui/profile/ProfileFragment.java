package com.codegainz.ndani.ui.profile;

import com.codegainz.ndani.ui.WebFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stuart on 24/10/15.
 */
public class ProfileFragment extends WebFragment {

    public static final String ID = "ID";

    @Override
    public String getPath() {
        return "profile/detail";
    }

    @Override
    public Map<String, String> getQueries() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getArguments().getString(ID));
        return map;
    }
}
