package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.CommentDTO;
import com.mahmoud.E_commerce.entity.Comment;
import com.mahmoud.E_commerce.mapper.CommentMapper;
import com.mahmoud.E_commerce.service.CommentService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import com.mahmoud.E_commerce.utils.Helper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products/{productId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final Helper helper;
    private final CommentMapper commentMapper;

    @Operation(summary = "User can add comment on a product")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<GlobalResponse<CommentDTO>> addComment(@PathVariable Long productId, @RequestBody CommentDTO commentDTO) {
        long userId = helper.extractUserId();
        Comment comment = commentService.addComment(productId, userId, commentDTO);
        GlobalResponse<CommentDTO> response = new GlobalResponse<>(commentMapper.toDTO(comment));
        response.setMessage("comment added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Everyone can view comments on a product")
    @GetMapping
    public ResponseEntity<GlobalResponse<List<CommentDTO>>> getAllComments(@PathVariable Long productId) {
        List<Comment> comments = commentService.getCommentsByProduct(productId);
        GlobalResponse<List<CommentDTO>> response = new GlobalResponse<>(comments.stream()
                .map(commentMapper::toDTO).toList());
        response.setMessage("all comments found successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
