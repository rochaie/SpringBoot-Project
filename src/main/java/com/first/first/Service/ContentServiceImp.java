package com.first.first.Service;

import com.first.first.DAO.ContentRepository;
import com.first.first.bean.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImp implements ContentService{
    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAll(){
        return contentRepository.findAll();
    }

    public Content InsertContent(Content content){
        return contentRepository.save(content);
    }

    public void DeleteContent(Integer id){
         contentRepository.deleteById(id);
    }

    public Content FindAllById(Integer id){
        return contentRepository.findAllById(id);
    }

    public void UpdateContent(Integer id,String content,String topic){
        contentRepository.UpdateContent(id,content,topic);
    }

    public Content findByTopic(String topic){
        return contentRepository.findByTopic(topic);
    }
}
