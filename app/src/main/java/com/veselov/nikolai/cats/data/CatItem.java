package com.veselov.nikolai.cats.data;

import java.io.Serializable;
import java.util.Objects;

import androidx.annotation.NonNull;

public class CatItem implements Serializable {

    @NonNull
    private final String title;
    @NonNull
    private final String imageUrl;

    public CatItem(@NonNull String title, @NonNull String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatItem newsItem = (CatItem) o;
        return Objects.equals(title, newsItem.title) &&
            Objects.equals(imageUrl, newsItem.imageUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, imageUrl);
    }

    @Override
    public String toString() {
        return "CatItem{" +
            "title='" + title + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
    }

}

