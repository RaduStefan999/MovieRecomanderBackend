package com.movierecomander.backend.comments;

import com.movierecomander.backend.users.user.AppUser;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.comments.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    public List<Comment> get() {
        return commentRepository.findAll();
    }

    @PostMapping("/")
    public void post(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> read(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundComment.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Long id, @RequestBody Comment comment) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        foundComment.get().update(comment);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);

        if (foundComment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        commentRepository.delete(foundComment.get());
        return ResponseEntity.ok(true);
    }
}
