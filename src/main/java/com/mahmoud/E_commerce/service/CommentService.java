package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.CommentDTO;
import com.mahmoud.E_commerce.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Long productId, Long userId, CommentDTO commentDTO);
    List<Comment> getCommentsByProduct(Long productId);
}
