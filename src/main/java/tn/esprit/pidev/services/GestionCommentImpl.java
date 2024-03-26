package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.repository.ICommentRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionCommentImpl implements IGestionComment {
    @Autowired
    ICommentRepository commentRepository;

    @Override
    public List<Comment> retrieveAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment retrieveComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
