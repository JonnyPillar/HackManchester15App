package com.codegainz.ndani.engine.model;

/**
 * Created by Stuart on 25/10/15.
 */
public class Comment {

    private String text;
    private boolean videoCallAvailable;
    private int questionForId;

    public Comment(String text, boolean videoCallAvailable, String questionForId) {
        this.text = text;
        this.videoCallAvailable = videoCallAvailable;
        this.questionForId = Integer.parseInt(questionForId);
    }
}
