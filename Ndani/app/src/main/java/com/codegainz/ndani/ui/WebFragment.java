package com.codegainz.ndani.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
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

    private static final String BLANK = "about:blank";

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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_view);
        swipeRefreshLayout.setOnRefreshListener(refreshListener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        webView = (WebView) view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                if(!url.equals(BLANK)) {
                    swipeRefreshLayout.setRefreshing(false);
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });

        load();

        return view;
    }

    public abstract String getPath();

    private void load() {
        webView.stopLoading();
        webView.loadUrl(BLANK);
        Token token = new RushSearch().findSingle(Token.class);
        String baseUrl = ((NdaniApplication)getActivity().getApplication()).getBaseUrl() + getPath() + token.getToken();
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        builder.path(getPath());
        builder.appendQueryParameter("token", token.getToken());
        Map<String, String> queries = getQueries();
        if(queries != null) {
            for (String key : queries.keySet()) {
                builder.appendQueryParameter(key, queries.get(key));
            }
        }
        String url = builder.build().toString();
        webView.loadUrl(url);
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
