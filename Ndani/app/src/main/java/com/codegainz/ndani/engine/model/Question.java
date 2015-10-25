package com.codegainz.ndani.engine.model;

import java.util.List;

/**
 * Created by Stuart on 25/10/15.
 */
public class Question {

    private String title;
    private String description;
    private List<Tag> questionTags;

    public Question(String title, String description, List<Tag> questionTags) {
        this.title = title;
        this.description = description;
        this.questionTags = questionTags;
    }
}
