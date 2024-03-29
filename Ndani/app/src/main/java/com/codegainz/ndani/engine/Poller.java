package com.codegainz.ndani.engine;

import android.os.Handler;

import com.codegainz.ndani.engine.model.Poll;
import com.codegainz.ndani.engine.model.Question;
import com.codegainz.ndani.engine.model.Token;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Stuart on 25/10/15.
 */
public class Poller {

    public interface VideoCall {
        void someOneEnteredVideo(Question question);
    }

    private final VideoCall listener;

    private static final int POLL_TIME = 1500;

    private List<Question> sentQuestion = new ArrayList<>();

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Token token = new RushSearch().findSingle(Token.class);
            Call<Poll> call = serverApi.poll(token.getToken());
            call.enqueue(new Callback<Poll>() {
                @Override
                public void onResponse(Response<Poll> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        if (listener != null) {
                            if (response.body() != null && response.body().getPollQuestions() != null) {
                                for (Question question : response.body().getPollQuestions()) {
                                    if (!sentQuestion.contains(question)) {
                                        listener.someOneEnteredVideo(question);
                                        sentQuestion.add(question);
                                    }
                                }
                            }
                        }
                        handler.postDelayed(runnable, POLL_TIME);
                    } else {
                        onFailure(null);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    handler.postDelayed(runnable, POLL_TIME);
                }
            });
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
