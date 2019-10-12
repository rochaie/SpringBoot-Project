package com.first.first.DAO;

import com.first.first.bean.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ContentRepository extends JpaRepository<Content,String> {
    public void deleteById(Integer id);
    public Content findAllById(Integer id);
    public Content findByTopic(String topic);

    @Transactional
    @Modifying
    @Query(value = "update Content c set c.content=:content,c.topic=:topic where id=:id")
    public void UpdateContent(@Param("id") Integer id, @Param("content") String content,@Param("topic") String topic);
}
