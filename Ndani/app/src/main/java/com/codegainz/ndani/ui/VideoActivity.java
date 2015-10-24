package com.codegainz.ndani.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codegainz.ndani.NdaniApplication;
import com.codegainz.ndani.R;
import com.oovoo.core.media.ooVooCamera;
import com.oovoo.core.sdk_error;
import com.oovoo.sdk.api.ooVooClient;
import com.oovoo.sdk.api.ui.VideoPanel;
import com.oovoo.sdk.interfaces.AVChatListener;
import com.oovoo.sdk.interfaces.AudioControllerListener;
import com.oovoo.sdk.interfaces.AudioRoute;
import com.oovoo.sdk.interfaces.AudioRouteController;
import com.oovoo.sdk.interfaces.Participant;
import com.oovoo.sdk.interfaces.VideoControllerListener;
import com.oovoo.sdk.interfaces.VideoRender;

public class VideoActivity extends AppCompatActivity implements AVChatListener, VideoControllerListener, AudioControllerListener,AudioRouteController.AudioRouteControllerListener {

    private ooVooClient sdk = null;

    public static final String CONFERENCE_ID = "CONFERENCE_ID";

    private VideoPanel ownPreview;
    private VideoPanel videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoPanel) findViewById(R.id.video);
        videoView.setVisibility(View.GONE);
        ownPreview = (VideoPanel) findViewById(R.id.video_preview);

        try {
            sdk = ooVooClient.sharedInstance();
            sdk.getAVChat().setListener(this);
            sdk.getAVChat().getVideoController().setListener(this);
            sdk.getAVChat().getAudioController().setListener(this);
            sdk.getAVChat().getAudioController().getAudioRouteController().setListener(this);

            sdk.getAVChat().getVideoController().openCamera();
            sdk.getAVChat().getVideoController().openPreview();
            sdk.getAVChat().getVideoController().bindRender(((NdaniApplication) getApplication()).getVideoUsername(), ownPreview);
            sdk.getAVChat().join("1", "");

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        sdk.getAVChat().getVideoController().closePreview();
        sdk.getAVChat().getVideoController().closeCamera();
        sdk.getAVChat().leave();
        super.onBackPressed();
    }

    @Override
    public void onParticipantJoined(Participant participant, String s) {
        try {
            sdk.getAVChat().getVideoController().bindRender(participant.getID(), videoView);
            sdk.getAVChat().getVideoController().registerRemote(participant.getID());
            videoView.setVisibility(View.VISIBLE);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onParticipantLeft(Participant participant) {
        try {
            videoView.setVisibility(View.GONE);
            sdk.getAVChat().getVideoController().unbindRender(participant.getID(), videoView);
            sdk.getAVChat().getVideoController().unregisterRemote(participant.getID());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConferenceStateChanged(ConferenceState conferenceState, sdk_error sdk_error) {
        if (conferenceState == ConferenceState.Joined && sdk_error == sdk_error.OK) {
            //You are joined in a conference
        }
        else if (conferenceState == ConferenceState.Joined && sdk_error != sdk_error.OK) {
            //You are not joined in a conference because an error occured
        }
    }

    @Override
    public void onReceiveData(String s, byte[] bytes) {

    }

    @Override
    public void onConferenceError(sdk_error sdk_error) {

    }

    @Override
    public void onNetworkReliability(int i) {

    }

    @Override
    public void onRemoteVideoStateChanged(String s, RemoteVideoState remoteVideoState, int i, int i1, sdk_error sdk_error) {

    }

    @Override
    public void onCameraStateChanged(ooVooCamera.ooVooCameraState ooVooCameraState, String s, int i, int i1, int i2, sdk_error sdk_error) {
    }

    @Override
    public void onTransmitStateChanged(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onVideoPreviewStateChanged(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onCameraChanged(String s, sdk_error sdk_error) {

    }

    @Override
    public void onAudioTransmitStateChanged(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onAudioReceiveStateChanged(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onMicrophoneStateChange(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onSpeakerStateChange(boolean b, sdk_error sdk_error) {

    }

    @Override
    public void onAudioRouteChanged(AudioRoute audioRoute, AudioRoute audioRoute1) {

    }
}
