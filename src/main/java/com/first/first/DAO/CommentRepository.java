package com.first.first.DAO;

import com.first.first.bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    public List<Comment> findAllByArticleid(Integer id);
    @Transactional
    @Modifying
    @Query(value = "delete from Comment c where c.content=:content and c.time=:time")
    public void deleteComment(@Param("content")String conent,@Param("time")String time);
    @Transactional
    @Modifying
    @Query(value = "delete from Comment c where c.articleid=:articleid")
    public void deleteCommentWhenAr(@Param("articleid")Integer id);
}
