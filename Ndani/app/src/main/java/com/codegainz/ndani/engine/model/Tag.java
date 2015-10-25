package com.codegainz.ndani.engine.model;

/**
 * Created by Stuart on 24/10/15.
 */
public class Tag {

    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        return !(imageUrl != null ? !imageUrl.equals(tag.imageUrl) : tag.imageUrl != null);

    }

}
