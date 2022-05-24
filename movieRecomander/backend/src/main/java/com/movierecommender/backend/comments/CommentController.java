package com.movierecommender.backend.comments;

import com.movierecommender.backend.advice.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Comment>> get() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Comment> read(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException("Comment not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(foundComment.get());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public void update(@PathVariable("id") Long id, @RequestBody Comment comment) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException("Comment not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        foundComment.get().update(comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void delete(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException("Comment not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        commentRepository.delete(foundComment.get());
    }
}
