package com.zino.movie_review_app.repository;

import com.zino.movie_review_app.dto.MovieSummaryDto;
import com.zino.movie_review_app.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Movie> movieMapper = (resultSet, rowNum) -> Movie.builder()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .releaseYear(resultSet.getInt("release_year"))
            .build();

    private final RowMapper<MovieSummaryDto> movieSummaryDtoRowMapper = (rs, rowNum) -> MovieSummaryDto.builder()
            .id(rs.getInt("id"))
            .title(rs.getString("title"))
            .releaseYear(rs.getInt("release_year"))
            .averageRating(rs.getDouble("avg_rating"))
            .reviewCount(rs.getInt("review_count"))
            .build();

    public List<Movie> findAll() {
        String sql = "SELECT m.id, m.title, m.release_year " +
                "FROM movies m " +
                "ORDER BY m.id";

        return jdbcTemplate.query(sql, movieMapper);
    }

    public Movie findById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, movieMapper, id);
    }

    public int save(Movie movie) {
        String sql = "INSERT INTO movies (title, release_year) VALUES (?, ?)";
        return jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear());
    }

    public int update(Movie movie) {
        String sql = "UPDATE movies SET title = ?, release_year = ? WHERE id = ?";
        return jdbcTemplate.update(sql, movie.getTitle(), movie.getReleaseYear(), movie.getId());
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM movies WHERE id = ?", id);
    }


    public List<MovieSummaryDto> findAllWithAverageRating() {
        String sql = "SELECT m.id, m.title, m.release_year, mar.avg_rating, mar.review_count " +
                "FROM movies m " +
                "LEFT JOIN movie_average_rating mar ON m.id = mar.movie_id";

        return jdbcTemplate.query(sql, movieSummaryDtoRowMapper);
    }

    public MovieSummaryDto findWithAverageRatingById(int id) {
        String sql = "SELECT m.id, m.title, m.release_year, mar.avg_rating, mar.review_count " +
                "FROM movies m " +
                "LEFT JOIN movie_average_rating mar ON m.id = mar.movie_id " +
                "WHERE m.id = ?";
        return jdbcTemplate.queryForObject(sql, movieSummaryDtoRowMapper, id);
    }

}