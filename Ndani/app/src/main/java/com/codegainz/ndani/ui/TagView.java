package com.codegainz.ndani.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.codegainz.ndani.R;

/**
 * Created by Stuart on 24/10/15.
 */
public class TagView extends FrameLayout {

    private TextView textView;
    private View body;

    public TagView(Context context) {
        super(context);
        setUp(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp(context);
    }

    private void setUp(Context context) {
        setClickable(true);
        body = View.inflate(context, R.layout.tag_view, this);
        setOnClickListener(selectClickListener);
        textView = (TextView) body.findViewById(R.id.textView);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    private OnClickListener selectClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setSelected(true);
            setOnClickListener(unselectListener);
        }
    };

    private OnClickListener unselectListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setSelected(false);
            setOnClickListener(selectClickListener);
        }
    };
}
