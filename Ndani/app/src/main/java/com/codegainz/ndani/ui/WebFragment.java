package com.codegainz.ndani.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Token;

import java.util.Map;

import co.uk.rushorm.core.RushSearch;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }

    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_view);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        swipeRefreshLayout.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(true);
            }
        });

        load();

        return view;
    }

    public abstract String getPath();

    private void load() {
        webView.stopLoading();
        webView.loadUrl("about:blank");
        Token token = new RushSearch().findSingle(Token.class);
        String baseUrl = ((NdaniApplication)getActivity().getApplication()).getBaseUrl() + getPath() + token.getToken();
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        builder.path(getPath());
        builder.appendQueryParameter("id", token.getToken());
        Map<String, String> queries = getQueries();
        if(queries != null) {
            for (String key : queries.keySet()) {
                builder.appendQueryParameter(key, queries.get(key));
            }
        }
        webView.loadUrl(builder.build().toString());
    }

    public WebView getWebView(){
        return webView;
    }

    public Map<String, String> getQueries(){
        return null;
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipeRefreshLayout.setEnabled(false);
            load();
        }
    };

}
