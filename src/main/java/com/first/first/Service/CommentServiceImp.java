package com.first.first.Service;

import com.first.first.DAO.CommentRepository;
import com.first.first.bean.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements CommentSevice{
    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllByArticleid(Integer id){
        return commentRepository.findAllByArticleid(id);
    }

    public Comment Save(Comment comment){
        return commentRepository.save(comment);
    }

    public void deleteCommentAndTime(String content,String time){
        commentRepository.deleteComment(content,time);
    }

    public void deleteCommentWhenAtr(Integer id){
        commentRepository.deleteCommentWhenAr(id);
    }
}
