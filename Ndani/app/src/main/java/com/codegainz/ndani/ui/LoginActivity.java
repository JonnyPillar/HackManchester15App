package com.codegainz.ndani.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.codegainz.ndani.engine.model.Login;
import com.codegainz.ndani.engine.model.Token;

import co.uk.rushorm.core.RushSearch;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private View parent;
    private EditText username;
    private EditText password;
    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Token token = new RushSearch().findSingle(Token.class);
        if(token != null) {
            complete();
        } else {
            setContentView(R.layout.activity_launch);
            parent = findViewById(R.id.parent);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);
            button = findViewById(R.id.button);
            button.setOnClickListener(loginPressed);
        }
    }

    private void complete(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private View.OnClickListener loginPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            button.setEnabled(false);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(password.getWindowToken(), 0);

            Call<Token> token = ((NdaniApplication)getApplication())
                    .getServerApi().login(new Login(username.getText().toString(), password.getText().toString()));

            token.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Response<Token> response, Retrofit retrofit) {
                    response.body().save();
                    complete();
                }

                @Override
                public void onFailure(Throwable t) {
                    button.setEnabled(true);
                    Snackbar.make(parent, "Incorrect username or password.", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    };

}
