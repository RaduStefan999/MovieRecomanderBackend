package com.movierecommender.backend.reviews;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.security.config.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void post(@Valid @RequestBody Review review) {
        var currentAppUser = this.identityService.getLoggedInAppUser();
        if (currentAppUser.isEmpty()) {
            throw new BusinessException("Could not find current app user", "Invalid permission", HttpStatus.FORBIDDEN);
        }
        review.setAppUser(currentAppUser.get());
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
    public void update(@PathVariable("id") Long id,@Valid @RequestBody Review review) {
        var foundReview = reviewRepository.findById(id);
        if (foundReview.isEmpty()) {
            throw new BusinessException("Review not found", "Invalid data", HttpStatus.NOT_FOUND);
        }

        if (!this.userCanModify(foundReview.get())) {
            throw new BusinessException("User can't modify this", "Invalid permission", HttpStatus.FORBIDDEN);
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

        if (!this.userCanModify(foundReview.get())) {
            throw new BusinessException("User can't modify this", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        reviewRepository.delete(foundReview.get());
    }

    private boolean userCanModify(Review review) {
        var currentUser = this.identityService.getLoggedInUser();

        return (currentUser.isPresent() &&
            (
                UserRoles.valueOf(currentUser.get().getRole()) == UserRoles.ADMIN ||
                !currentUser.get().equals(review.getAppUser())
            )
        );
    }
}
