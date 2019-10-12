package com.first.first.viewController;

import com.first.first.Service.CommentServiceImp;
import com.first.first.Service.ContentServiceImp;
import com.first.first.bean.Content;
import net.minidev.json.JSONArray;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.*;

@Controller
public class ArticleController {
    @Autowired
    private ContentServiceImp contentServiceImp;

    @Autowired
    private CommentServiceImp commentServiceImp;

    @RequestMapping("/ArticleManager")
    public String ArticleManager(Model model){
        List<Content> contentList=contentServiceImp.getAll();
        String []content=new String[contentList.size()];
        String []topic=new String[contentList.size()];
        String []time=new String[contentList.size()];
        int j=contentList.size()-1;
        for (int i=0;i<contentList.size();i++){
            content[j]=contentList.get(i).getContent();
            topic[j]=contentList.get(i).getTopic();
            j--;
        }
        model.addAttribute("content",content);
        model.addAttribute("topic",topic);
        model.addAttribute("contentList",contentList);
        return "ArticleManage";
    }

    @RequestMapping(value = "/ArticleData",produces ="application/json;charset=UTF-8")
    @ResponseBody
    public String ArticleData(){
        LinkedHashMap<String,Object> map=new LinkedHashMap<String, Object>();
       // JSONObject result=new JSONObject();
        JSONArray data=new JSONArray();
        List<Content> contentList=contentServiceImp.getAll();
        Integer []id=new Integer[contentList.size()];
        String []topic=new String[contentList.size()];
        String []time=new String[contentList.size()];
        int j=contentList.size()-1;
        for (int i=0;i<contentList.size();i++) {
            JSONObject jo=new JSONObject();
            id[j] = contentList.get(i).getId();
            topic[j] = contentList.get(i).getTopic();
            time[j] = contentList.get(i).getTime();
            jo.put("id",id[j]);
            jo.put("topic",topic[j]);
            jo.put("time",time[j]);
            data.add(jo);
            j--;
        }
        map.put("code",0);
        map.put("msg","");
        map.put("count",contentList.size());
        map.put("data",data);
       String result= JSONObject.toJSONString(map);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/deleteOneArticle")
    @ResponseBody
    @Transactional
    public String DeleteOne(@RequestParam(value = "id",required = false) Integer id){
        System.out.println(id);
        try{
            contentServiceImp.DeleteContent(id);
            commentServiceImp.deleteCommentWhenAtr(id);
            return "删除成功!";
        }catch (Exception e){
            return "删除失败!";
        }
    }

    @RequestMapping("/editorArticle")
    @ResponseBody
    public HashMap<String, Object> editorArticle(@RequestParam(value = "id",required = false) Integer id){
        Content content=contentServiceImp.FindAllById(id);
        HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("content", content.getContent());
            map.put("topic", content.getTopic());
            return map;
    }

    @RequestMapping("/submitEditor")
    @ResponseBody
    public String submitEditor(@RequestParam(value = "topic",required = false) String topic,
                               @RequestParam(value = "id",required = false) Integer id,
                               @RequestParam(value = "content",required = false)String content){

                 contentServiceImp.UpdateContent(id,content,topic);
                 return "更新成功!";
    }
}
