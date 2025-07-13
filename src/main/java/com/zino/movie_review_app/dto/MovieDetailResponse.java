package com.zino.movie_review_app.dto;

import com.zino.movie_review_app.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MovieDetailResponse {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private Double averageRating;
    private Integer reviewCount;
    private List<Review> reviews;
}