package com.movierecommender.backend.comments;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.security.config.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final IdentityService identityService;

    private static final String INVALID_PERMISSION = "Invalid permission";
    private static final String COMMENT_NOT_FOUND = "Comment not found";
    private static final String INVALID_DATA = "Invalid data";

    @Autowired
    public CommentController(CommentRepository commentRepository, IdentityService identityService) {
        this.commentRepository = commentRepository;
        this.identityService = identityService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Comment>> get() {
        return ResponseEntity.ok(commentRepository.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void post(@RequestBody CommentDTO commentDTO) {
        var currentAppUser = identityService.getLoggedInAppUser();
        if (currentAppUser.isEmpty()) {
            throw new BusinessException("Could not find current app user", INVALID_PERMISSION, HttpStatus.FORBIDDEN);
        }
        commentDTO.setAppUser(currentAppUser.get());
        commentRepository.save(new Comment(commentDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Comment> read(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException(COMMENT_NOT_FOUND, INVALID_DATA, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(foundComment.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public void update(@PathVariable("id") Long id, @RequestBody CommentDTO commentDTO) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException(COMMENT_NOT_FOUND, INVALID_DATA, HttpStatus.NOT_FOUND);
        }

        if (!this.userCanModify(foundComment.get())) {
            throw new BusinessException("User can't modify this", INVALID_PERMISSION, HttpStatus.FORBIDDEN);
        }

        foundComment.get().update(commentDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void delete(@PathVariable("id") Long id) {
        var foundComment = commentRepository.findById(id);
        if (foundComment.isEmpty()) {
            throw new BusinessException(COMMENT_NOT_FOUND, INVALID_DATA, HttpStatus.NOT_FOUND);
        }

        if (!this.userCanModify(foundComment.get())) {
            throw new BusinessException("User can't modify this", INVALID_PERMISSION, HttpStatus.FORBIDDEN);
        }

        commentRepository.delete(foundComment.get());
    }

    private boolean userCanModify(Comment comment) {
        var currentUser = this.identityService.getLoggedInUser();

        return (currentUser.isPresent() &&
            (
                UserRoles.valueOf(currentUser.get().getRole()) == UserRoles.ADMIN ||
                !currentUser.get().equals(comment.getAppUser())
            )
        );
    }
}
