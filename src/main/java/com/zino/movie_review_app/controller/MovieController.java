package com.zino.movie_review_app.controller;

import com.zino.movie_review_app.dto.MovieDetailResponse;
import com.zino.movie_review_app.dto.MovieListResponse;
import com.zino.movie_review_app.dto.MovieSummaryDto;
import com.zino.movie_review_app.model.Movie;
import com.zino.movie_review_app.model.Review;
import com.zino.movie_review_app.repository.MovieRepository;
import com.zino.movie_review_app.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping
    public String list(Model model) {
        List<MovieSummaryDto> movies = movieRepository.findAllWithAverageRating();

        List<MovieListResponse> responses = movies.stream()
                .map(dto -> MovieListResponse.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .releaseYear(dto.getReleaseYear())
                        .averageRating(dto.getAverageRating())
                        .build())
                .collect(Collectors.toList());
        model.addAttribute("movieList", responses);

        return "movie-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        MovieSummaryDto movie = movieRepository.findWithAverageRatingById(id);
        List<Review> reviews = reviewRepository.findAllByMovieId(movie.getId());

        MovieDetailResponse response = MovieDetailResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .releaseYear(movie.getReleaseYear())
                .averageRating(movie.getAverageRating())
                .reviews(reviews)
                .build();

        model.addAttribute("movie", response);

        if (!model.containsAttribute("review")) {
            model.addAttribute("review", new Review());
        }

        return "movie-detail";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("movie", new Movie());

        return "movie-form";
    }

    @PostMapping
    public String add(@ModelAttribute Movie movie) {
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        Movie movie = movieRepository.findById(id);
        model.addAttribute("movie", movie);
        return "movie-form";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Movie movie) {
        movieRepository.update(movie);
        return "redirect:/movies";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }

    @PostMapping("/{id}/reviews/add")
    public String addReview(@PathVariable int id,
                            @ModelAttribute Review review) {
        reviewRepository.save(review);
        return "redirect:/movies/" + id;
    }

    @PostMapping("/{id}/reviews/edit")
    public String editReviewRedirect(@PathVariable int id,
                                     @ModelAttribute Review review,
                                     RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("review", review);
        return "redirect:/movies/" + id;
    }
}