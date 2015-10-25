package com.codegainz.ndani.engine;

import android.os.Handler;

import com.codegainz.ndani.engine.model.Question;

/**
 * Created by Stuart on 25/10/15.
 */
public class Poller {

    public interface VideoCall {
        void someOneEnteredVideo(Question question);
    }

    private final VideoCall listener;

    private static final int POLL_TIME = 1500;

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //serverApi.


            handler.postDelayed(this, POLL_TIME);
        }
    };

    private final ServerApi serverApi;

    public Poller(VideoCall listener, ServerApi serverApi) {
        this.listener = listener;
        this.serverApi = serverApi;
    }

    public void startPoling(){
        handler.post(runnable);
    }

    public void stopPolling(){
        handler.removeCallbacks(runnable);
    }
}
