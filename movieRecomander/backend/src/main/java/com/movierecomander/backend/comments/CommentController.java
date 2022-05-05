package com.movierecomander.backend.comments;

import com.movierecomander.backend.users.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public List<Comment> get() {
        return commentRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public ResponseEntity<Comment> read(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundComment.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public ResponseEntity<Boolean> update(@PathVariable("id") Long id, @RequestBody Comment comment) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foundComment.get().update(comment);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        commentRepository.delete(foundComment.get());
        return ResponseEntity.ok(true);
    }
}
