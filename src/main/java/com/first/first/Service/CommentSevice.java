package com.first.first.Service;

import com.first.first.bean.Comment;

import java.util.List;

public interface CommentSevice {
    List<Comment> findAllByArticleid(Integer id);
    Comment Save(Comment comment);
    void deleteCommentAndTime(String content,String time);
    void deleteCommentWhenAtr(Integer id);
}
