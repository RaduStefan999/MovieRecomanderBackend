package com.movierecommender.backend.reviews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public List<Review> get() {
        return reviewRepository.findAll();
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody Review review) {
        reviewRepository.save(review);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public ResponseEntity<Review> read(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundReview.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public ResponseEntity<Boolean> update(@PathVariable("id") Long id, @RequestBody Review review) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foundReview.get().update(review);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reviewRepository.delete(foundReview.get());
        return ResponseEntity.ok(true);
    }
}
