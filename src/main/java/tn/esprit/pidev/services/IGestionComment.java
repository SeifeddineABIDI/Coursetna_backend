package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Comment;

import java.util.List;

public interface IGestionComment {
    List<Comment> retrieveAllComments();
    Comment addComment(Comment comment);
    Comment updateComment(Comment comment);
    void removeComment(Long commentId);
    Comment retrieveComment(Long commentId);
}
