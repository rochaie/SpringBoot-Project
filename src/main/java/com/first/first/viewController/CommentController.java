package com.first.first.viewController;

import com.first.first.Service.CommentServiceImp;
import com.first.first.Service.ContentServiceImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.Comment;
import com.first.first.bean.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentServiceImp commentServiceImp;

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    ContentServiceImp contentServiceImp;

    @RequestMapping("/addComment")
    @ResponseBody
    public String addComment(@RequestParam(value = "content",required = false) String content,
                             @RequestParam(value = "getIdbyTopic",required = false)String getIdbyTopic,
                             HttpServletRequest request){
        Integer id=(Integer) request.getSession().getAttribute("id");
        Integer articleId=contentServiceImp.findByTopic(getIdbyTopic).getId();
        try {
            UserInformation userInformation=userServiceImp.findAllById(id);
            Comment comment=new Comment();
            comment.setContent(content);
            comment.setName(userInformation.getUserName());
            comment.setArticleid(articleId);
            Calendar calendar=Calendar.getInstance();
            comment.setTime(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
            commentServiceImp.Save(comment);
            return "评论成功!";
        }catch (Exception e){
            return "发生错误!";
        }
    }

    @RequestMapping("/getArticleId")
    @ResponseBody
    public HashMap<String,Object> getArticleId(
                               @RequestParam(value = "getIdbyTopic",required = false) String getIdbyTopic,
                               HttpServletRequest request){
            Integer articleId=contentServiceImp.findByTopic(getIdbyTopic).getId();
            List<Comment> comment = commentServiceImp.findAllByArticleid(articleId);
            HashMap<String,Object> map=new HashMap<String, Object>();
            String content[]=new String[comment.size()];
            String name[]=new String[comment.size()];
            String time[]=new String[comment.size()];
            int j=comment.size()-1;
            for (int i=0;i<comment.size();i++){
                content[j]=comment.get(i).getContent();
                name[j]=comment.get(i).getName();
                time[j]=comment.get(i).getTime();
                j--;
            }
                if (comment != null) {
                    map.put("content", content);
                    map.put("ArticleTime", time);
                    map.put("ArticleName", name);
                    map.put("ArticleLength",comment.size());
                    return map;
                } else {
                    return null;
                }
    }

    @RequestMapping("/deleteComment")
    @ResponseBody
    public String deleteComment(@RequestParam(value = "commentContent",required = false) String content,
                                @RequestParam(value = "commentTime",required = false) String time){
            try {
                commentServiceImp.deleteCommentAndTime(content, time);
                return "删除成功!";
            }catch (Exception e){
                return "出现错误!";
            }

    }
}
