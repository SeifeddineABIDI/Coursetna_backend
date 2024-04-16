package tn.esprit.pidev.services;

import tn.esprit.pidev.dto.CommentsDto;
import tn.esprit.pidev.entities.Comment;

import java.util.List;

public interface IGestionComment {
    List<CommentsDto> getAllCommentsForPost(Long postId);
    void create(CommentsDto commentsDto);
    void deleteComment(Long commentId);
}
