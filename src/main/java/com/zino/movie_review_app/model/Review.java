package com.zino.movie_review_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Integer id;
    private String reviewer;
    private Integer rating;
    private String comment;
    private Integer movieId;
}