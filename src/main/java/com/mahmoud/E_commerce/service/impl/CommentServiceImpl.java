package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.dto.CommentDTO;
import com.mahmoud.E_commerce.entity.Comment;
import com.mahmoud.E_commerce.entity.Product;
import com.mahmoud.E_commerce.entity.User;
import com.mahmoud.E_commerce.repository.CommentRepository;
import com.mahmoud.E_commerce.service.CommentService;
import com.mahmoud.E_commerce.service.ProductService;
import com.mahmoud.E_commerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public Comment addComment(Long productId, Long userId, CommentDTO commentDTO) {
        Product product = productService.getProductById(productId);
        User user = userService.getUserById(userId);

        Comment comment = Comment.builder()
                .product(product)
                .user(user)
                .content(commentDTO.getContent())
                .score(commentDTO.getScore())
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByProduct(Long productId) {
        return commentRepository.findByProductId(productId);
    }
}
