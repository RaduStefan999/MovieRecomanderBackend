package com.movierecommender.backend.movies.moviegenrereview;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.reviews.Review;
import com.movierecommender.backend.reviews.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/v1/genre/reviews")
public class MovieGenreReviewController {
    private final ReviewRepository reviewRepository;
    private final IdentityService identityService;
    @Autowired
    MovieRepository movieRepository;
    public MovieGenreReviewController(ReviewRepository reviewRepository, IdentityService identityService) {
        this.reviewRepository = reviewRepository;
        this.identityService = identityService;
    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody MovieGenre movieGenre) {
        var currentAppUser = this.identityService.getLoggedInAppUser();
        if (currentAppUser.isEmpty()) {
            throw new BusinessException("Could not find current app user", "Invalid permission", HttpStatus.FORBIDDEN);
        }
        Review review=new Review();
        review.setAppUser(currentAppUser.get());
        review.setReviewValue(5);

        Random random=new Random();
        List<Movie> movies=findAllMovieByGenre(movieGenre);
        if (movies.isEmpty()) {
            throw new BusinessException("The list of movies for this genre is empty", "Invalid permission", HttpStatus.INSUFFICIENT_STORAGE);
        }
        int randomMovie = random.nextInt(movies.size());
        review.setMovie(movies.get(randomMovie));

        reviewRepository.save(review);
    }

    private List<Movie> findAllMovieByGenre(MovieGenre movieGenre) {
        List<Movie> movies=new ArrayList<>();
        for(var movie :movieRepository.findAll()){
            if(movie.getMovieGenres().equals(movieGenre)){
                movies.add(movie);
            }
        }
        return movies;
    }

}
