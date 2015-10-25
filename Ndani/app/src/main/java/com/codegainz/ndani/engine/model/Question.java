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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (description != null ? !description.equals(question.description) : question.description != null)
            return false;
        if (questionTags != null ? !questionTags.equals(question.questionTags) : question.questionTags != null)
            return false;
        return !(id != null ? !id.equals(question.id) : question.id != null);
    }

}
