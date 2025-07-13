package com.zino.movie_review_app.controller;

import com.zino.movie_review_app.model.Review;
import com.zino.movie_review_app.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @PostMapping("/edit")
    public String edit(@ModelAttribute Review review) {
        System.out.println(review.getReviewer()+ review.getMovieId()+ review.getId());
        reviewRepository.update(review);
        return "redirect:/movies/" + review.getMovieId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        int movieId = reviewRepository.findMovieIdByReviewId(id);
        reviewRepository.deleteById(id);

        return "redirect:/movies/" + movieId;
    }
}