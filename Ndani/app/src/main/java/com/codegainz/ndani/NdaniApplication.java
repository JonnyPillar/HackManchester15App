package com.codegainz.ndani;

import android.app.Application;
import android.provider.Settings;

import com.codegainz.ndani.engine.ServerApi;
import com.codegainz.ndani.engine.model.Token;
import com.oovoo.core.LoggerListener;
import com.oovoo.core.sdk_error;
import com.oovoo.sdk.api.ooVooClient;
import com.oovoo.sdk.interfaces.ooVooSdkResult;
import com.oovoo.sdk.interfaces.ooVooSdkResultListener;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Stuart on 24/10/15.
 */
public class NdaniApplication extends Application implements LoggerListener {

    private ooVooClient client;
    private boolean loginComplete = false;
    private ServerApi serverApi;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        List<Class<? extends Rush>> classList = new ArrayList<>();
        classList.add(Token.class);
        config.setClasses(classList);
        RushCore.initialize(config);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ndani.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverApi = retrofit.create(ServerApi.class);


        ooVooClient.setContext(this);
        ooVooClient.setLogger(this, LogLevel.Trace);
        try {
            client = ooVooClient.sharedInstance();
            authenticate();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnLog(LogLevel logLevel, String s, String s1) {

    }

    private void authenticate(){
        client.authorizeClient(getString(R.string.video_token), new ooVooSdkResultListener() {
            @Override
            public void onResult(ooVooSdkResult result) {
                if (result.getResult() == sdk_error.OK) {
                    login();
                }
            }
        });
    }

    private void login(){
        client.getAccount().login(getVideoUsername(), new ooVooSdkResultListener() {
            @Override
            public void onResult(ooVooSdkResult autorize_result) {
                if (autorize_result.getResult() == sdk_error.OK) {
                    loginComplete = true;
                }
            }
        });
    }

    public boolean isLoginComplete() {
        return loginComplete;
    }

    public String getVideoUsername(){
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public ServerApi getServerApi() {
        return serverApi;
    }
}
