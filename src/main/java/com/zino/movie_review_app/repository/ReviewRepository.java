package com.zino.movie_review_app.repository;

import com.zino.movie_review_app.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Review> mapper = (resultSet, rowNum) ->
            Review.builder()
                    .id(resultSet.getInt("id"))
                    .reviewer(resultSet.getString("reviewer"))
                    .comment(resultSet.getString("comment"))
                    .rating(resultSet.getInt("rating"))
                    .movieId(resultSet.getInt("movie_id"))
                    .build();

    public Review findById(int id) {
        String sql = "SELECT r.id, r.reviewer, r.comment, r.rating, r.movie_id " +
                "FROM reviews r " +
                "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    public int save(Review review) {
        String sql = "INSERT INTO reviews (reviewer, comment, rating, movie_id) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(sql, review.getReviewer(), review.getComment(), review.getRating(), review.getMovieId());
    }

    public int update(Review review) {
        String sql = "UPDATE reviews SET comment = ?, reviewer = ?, rating = ? WHERE id = ?";
        return jdbcTemplate.update(sql, review.getComment(), review.getReviewer(), review.getRating(), review.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM reviews WHERE id = ?", id);
    }

    public List<Review> findAllByMovieId(int movieId) {
        String sql = "SELECT r.id, r.reviewer, r.comment, r.rating, m.id as movie_id " +
                "FROM reviews r LEFT JOIN movies m ON r.movie_id = m.id " +
                "WHERE r.movie_id = ?";

        return jdbcTemplate.query(sql, mapper, movieId);
    }

    public int findMovieIdByReviewId(int reviewId) {
        String sql = "SELECT r.movie_id " +
                "FROM reviews r " +
                "WHERE r.id = ?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> resultSet.getInt("movie_id"), reviewId);
    }

}