package com.codegainz.ndani.engine;

import com.codegainz.ndani.engine.model.Comment;
import com.codegainz.ndani.engine.model.Login;
import com.codegainz.ndani.engine.model.Tags;
import com.codegainz.ndani.engine.model.Token;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

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

}
