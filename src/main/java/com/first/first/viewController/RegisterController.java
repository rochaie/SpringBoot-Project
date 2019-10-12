package com.first.first.viewController;

import com.first.first.Service.MailServiceImp;
import com.first.first.Service.PersonalImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.DetailedInformation;
import com.first.first.bean.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;

@Controller
public class RegisterController {
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private MailServiceImp mailServiceImp;

    @Autowired
    PersonalImp personalImp;

    @RequestMapping("/register")
    public String hello(){
        return "register";
    }

    @RequestMapping("/registerJudge")
    public String register(HttpServletRequest request, Model model){
        String userName=request.getParameter("userName");
        String passWord=request.getParameter("passwordRegister");
        String accountNumber=request.getParameter("email");
        UserInformation hasUserName=userServiceImp.findByUserName(userName);
        UserInformation hasEmail=userServiceImp.findByAccountNumber(accountNumber);
        if (hasUserName!=null){
            model.addAttribute("hasUserName","用户名已被注册!");
            return "register";
        }else if (hasEmail!=null){
            model.addAttribute("hasEmail","邮箱已被注册!");
            return "register";
        }else {
            UserInformation userInformation = new UserInformation();
            userInformation.setAccountNumber(accountNumber);
            userInformation.setPassword(passWord);
            userInformation.setUserName(userName);
            userInformation.setRole("user");
            userServiceImp.InsertRegister(userInformation);
            DetailedInformation detailedInformation=new DetailedInformation();
            personalImp.save(detailedInformation);
            return "login";
        }
    }

    @RequestMapping("/sendMail")
    @ResponseBody
    public String sendMail(HttpServletRequest request,Model model,@RequestParam(value = "email",required = false) String email){
        System.out.println(email);

       String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<6;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
       String to=email;
       String subject="激活码";
       String content=sb.toString();
       try {
           mailServiceImp.sendMail(to, subject, content);
           return sb.toString();
       }catch (Exception e){
           System.out.println("失败");
           return "发送失败!请检查邮箱地址是否正确!";
       }
    }

}
