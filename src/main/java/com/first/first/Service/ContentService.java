package com.first.first.Service;


import com.first.first.bean.Content;

import java.util.List;

public interface ContentService {
      List<Content> getAll();
      Content InsertContent(Content content);
      void DeleteContent(Integer id);
      Content FindAllById(Integer id);
      void UpdateContent(Integer id,String content,String topic);
      Content findByTopic(String topic);
}
