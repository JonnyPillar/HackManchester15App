package com.codegainz.ndani.engine;

import com.codegainz.ndani.engine.model.Comment;
import com.codegainz.ndani.engine.model.Login;
import com.codegainz.ndani.engine.model.Poll;
import com.codegainz.ndani.engine.model.Question;
import com.codegainz.ndani.engine.model.QuestionId;
import com.codegainz.ndani.engine.model.Tags;
import com.codegainz.ndani.engine.model.Token;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Stuart on 24/10/15.
 */
public interface ServerApi {

    @POST("/api/Accounts/Login")
    Call<Token> login(@Body Login login);

    @GET("/api/QuestionTag")
    Call<Tags> tags(@Header("authorization") String token);

    @POST("/api/Offers/Submit")
    Call<Void> comment(@Header("authorization") String token, @Body Comment comment);

    @POST("/api/Questions/Submit")
    Call<QuestionId> question(@Header("authorization") String token, @Body Question question);

    @POST("/api/Questions/enter/{id}")
    Call<Void> enter(@Header("authorization") String token, @Path("id") String id);

    @POST("/api/Questions/leave/{id}")
    Call<Void> leave(@Header("authorization") String token, @Path("id") String id);

    @GET("/api/Questions/poll")
    Call<Poll> poll(@Header("authorization") String token);
}
