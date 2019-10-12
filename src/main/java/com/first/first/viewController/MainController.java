package com.first.first.viewController;

import com.auth0.jwt.interfaces.Claim;
import com.first.first.Configuration.JwtUtil;
import com.first.first.Service.CommentServiceImp;
import com.first.first.Service.ContentServiceImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.Comment;
import com.first.first.bean.Content;
import com.first.first.bean.UserInformation;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.io.FilenameUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private ContentServiceImp contentService;

    @Autowired
    private UserServiceImp userServiceImp;


    @RequestMapping(value = "/index")
    public String home(Model model,HttpServletRequest request) {
        List<Content> contentList=contentService.getAll();
        String []content=new String[contentList.size()];
        String []topic=new String[contentList.size()];
        String []time=new String[contentList.size()];
        int j=contentList.size()-1;
        for (int i=0;i<contentList.size();i++){
            content[j]=contentList.get(i).getContent();
            topic[j]=contentList.get(i).getTopic();
            time[j]=contentList.get(i).getTime();
            j--;
        }
        Integer id=(Integer) request.getSession().getAttribute("id");
        if (id!=null) {
            UserInformation userInformation = userServiceImp.findAllById(id);
            model.addAttribute("userName", userInformation.getUserName());
            model.addAttribute("imgName",userInformation.getImgName());
        }
        model.addAttribute("content",content);
        model.addAttribute("topic",topic);
        model.addAttribute("time",time);
       model.addAttribute("contentList",contentList);
        Number pageNum=contentList.size();
        Number pageSize=1;
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("pageSize",pageSize);
       // model.addAttribute("isLogin",request.getSession().getAttribute("isLogin"));
        try {
            Map<String, Claim> map = JwtUtil.verifyToken((String) request.getSession().getAttribute("token"));
            model.addAttribute("isLogin",(String)request.getSession().getAttribute("token"));
        }catch (Exception e){
            model.addAttribute("isLogin",null);
        }
        model.addAttribute("admin",request.getSession().getAttribute("admin"));

        return "index";
    }

    @RequestMapping("/editor")
    public String editor(){
        return "Editor";
    }

    @RequestMapping("/add")
    public String insert(Model model){
        ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=servletRequestAttributes.getRequest();
        String newContent=request.getParameter("content");
        String newTime=request.getParameter("time");
        String newTopic=request.getParameter("topic");
        Content content=new Content();
        content.setContent(newContent);
        content.setTime(newTime);
        content.setTopic(newTopic);
        contentService.InsertContent(content);
        List<Content> contentList=contentService.getAll();
        model.addAttribute("contentList",contentList);
        return "redirect:/index";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public HashMap<String,Object> upload(@RequestParam(value = "img")MultipartFile file,
                                     HttpServletRequest request)throws IOException {
        byte[] b=file.getBytes();
        String base64=Base64.getEncoder().encodeToString(b);
        System.out.println(base64);
        Integer errno=0;
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("errno",errno);
        map.put("data",base64);
        return map;
    }

    @RequestMapping("/403")
    @ResponseBody
    public String Cantvisit(){
        return "您没有权限访问!";
    }


    @RequestMapping("/test")
    public String test(Model model){
        List<Content> contentList=contentService.getAll();
        model.addAttribute("contentList",contentList);
        return "a";
    }
}