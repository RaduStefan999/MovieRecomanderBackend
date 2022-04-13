package com.movierecomander.backend.reviews;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ReviewKey implements Serializable {
    @Column(name = "userId")
    private Long userId;
    @Column(name = "movieId")
    private Long movieId;

    public ReviewKey() {
    }

    public ReviewKey(Long userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewKey reviewKey = (ReviewKey) o;
        return userId.equals(reviewKey.userId) && movieId.equals(reviewKey.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}
