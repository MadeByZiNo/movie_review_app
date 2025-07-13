package com.zino.movie_review_app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MovieSummaryDto {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private Double averageRating;
    private Integer reviewCount;
}