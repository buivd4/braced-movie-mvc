package com.mkyong.common.models.entities;

import lombok.Setter;
import lombok.Getter;
import lombok.experimental.Accessors;


@Accessors(fluent = true, chain = true)
@Getter
@Setter
public class Movie {
    private int id;
    private String title;
    private String genre;
    private int yearOfRelease;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }
}
