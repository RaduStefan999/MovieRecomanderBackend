package com.movierecommender.backend.reviews;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.movies.moviegenre.MovieGenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;

@RestController
@RequestMapping("api/v1/genre/reviews")
public class MovieGenreReviewController {
    private final ReviewRepository reviewRepository;
    private final IdentityService identityService;
    private final MovieRepository movieRepository;
    private final SecureRandom random = new SecureRandom();

    @Autowired
    public MovieGenreReviewController(ReviewRepository reviewRepository, IdentityService identityService, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.identityService = identityService;
        this.movieRepository = movieRepository;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody MovieGenreDTO movieGenreDTO) {
        var currentAppUser = this.identityService.getLoggedInAppUser();
        if (currentAppUser.isEmpty()) {
            throw new BusinessException("Could not find current app user", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        List<Movie> movies=findAllMovieByGenre(new MovieGenre(movieGenreDTO));
        if (movies.isEmpty()) {
            throw new BusinessException("The list of movies for this genre is empty", "Invalid permission", HttpStatus.INSUFFICIENT_STORAGE);
        }
        int randomMovie = random.nextInt(movies.size());

        Review review = new Review(currentAppUser.get(), movies.get(randomMovie), 5);

        reviewRepository.save(review);
    }

    private List<Movie> findAllMovieByGenre(MovieGenre movieGenre) {
        return movieRepository.findAll().stream()
                .filter(m -> m.getMovieGenres().contains(movieGenre))
                .toList();
    }

}
