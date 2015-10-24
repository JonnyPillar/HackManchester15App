package com.codegainz.ndani.ui;

import android.app.Activity;

import java.lang.ref.WeakReference;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

/**
 * Created by Stuart on 24/10/15.
 */
public class SpeechListener implements RecognitionListener {

    private WeakReference<Activity> activityWeakReference;

    public SpeechListener(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {

    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if(activityWeakReference.get() != null) {
            activityWeakReference.get().finish();
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {

    }
}
