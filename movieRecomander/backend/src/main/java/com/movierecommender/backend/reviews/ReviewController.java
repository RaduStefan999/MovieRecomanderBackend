package com.movierecommender.backend.reviews;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
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
    private final IdentityService identityService;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, IdentityService identityService) {
        this.reviewRepository = reviewRepository;
        this.identityService = identityService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Review>> get() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody Review review) {
        reviewRepository.save(review);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Review> read(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);
        if (foundReview.isEmpty()) {
            throw new BusinessException("Review not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(foundReview.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public void update(@PathVariable("id") Long id, @RequestBody Review review) {
        var foundReview = reviewRepository.findById(id);
        if (foundReview.isEmpty()) {
            throw new BusinessException("Review not found", "Invalid data", HttpStatus.NOT_FOUND);
        }

        var currentUser = this.identityService.getLoggedInUser();
        if (currentUser.isEmpty() || !currentUser.get().equals(foundReview.get().getAppUser())) {
            throw new BusinessException("User can only update his own review", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        foundReview.get().update(review);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void delete(@PathVariable("id") Long id) {
        var foundReview = reviewRepository.findById(id);
        if (foundReview.isEmpty()) {
            throw new BusinessException("Review not found", "Invalid data", HttpStatus.NOT_FOUND);
        }

        var currentUser = this.identityService.getLoggedInUser();
        if (currentUser.isEmpty() || !currentUser.get().equals(foundReview.get().getAppUser())) {
            throw new BusinessException("User can only delete his own review", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        reviewRepository.delete(foundReview.get());
    }
}
