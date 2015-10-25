package com.codegainz.ndani.engine.model;

import java.util.List;

/**
 * Created by Stuart on 25/10/15.
 */
public class Question {

    private String title;
    private String description;
    private List<Tag> questionTags;
    private String id;

    public Question(String title, String description, List<Tag> questionTags) {
        this.title = title;
        this.description = description;
        this.questionTags = questionTags;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
