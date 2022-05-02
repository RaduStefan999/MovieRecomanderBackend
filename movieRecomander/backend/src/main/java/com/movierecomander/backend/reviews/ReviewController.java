package com.movierecomander.backend.reviews;

import com.movierecomander.backend.comments.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/")
    public List<Review> get() {
        return reviewRepository.findAll();
    }

    @PostMapping("/")
    public void post(@RequestBody Review review) {
        reviewRepository.save(review);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> read(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundReview.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Long id, @RequestBody Review review) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foundReview.get().update(review);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reviewRepository.delete(foundReview.get());
        return ResponseEntity.ok(true);
    }
}
