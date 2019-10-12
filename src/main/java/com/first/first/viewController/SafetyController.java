package com.first.first.viewController;

import com.first.first.Service.MailServiceImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class SafetyController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    MailServiceImp mailServiceImp;

    @RequestMapping("/safety")
    public String Safety(HttpServletRequest request, Model model){
        Integer id=(Integer) request.getSession().getAttribute("id");
        UserInformation userInformation=userServiceImp.findAllById(id);
        model.addAttribute("email",userInformation.getAccountNumber());
        return "safety";
    }

    @RequestMapping("/getCode")
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
        UserInformation userInformation=userServiceImp.findByAccountNumber(email);
        try {
            if (userInformation!=null){
                return "此邮箱已被注册!";
            }else {
                mailServiceImp.sendMail(to, subject, content);
                return sb.toString();
            }
        }catch (Exception e){
            return "发送失败!请检查邮箱地址是否正确!";
        }
    }

    @RequestMapping("/newEmail")
    @ResponseBody
    public String newEmail(@RequestParam(value = "newEmail",required = false) String newEmail,
                           HttpServletRequest request){
        Integer id=(Integer) request.getSession().getAttribute("id");
        try {
                userServiceImp.UpdateEmail(id, newEmail);
                return "修改成功!";
        }catch (Exception e){
            return "发生异常!";
        }
    }

    @RequestMapping("/newPassword")
    @ResponseBody
    public String newPassword(@RequestParam(value = "oldPassword",required = false) String oldPassword,
                              @RequestParam(value = "newPassword",required = false) String newPassword,
                              HttpServletRequest request){
        Integer id=(Integer) request.getSession().getAttribute("id");
        UserInformation userInformation=userServiceImp.findAllById(id);
        try {
            if (!oldPassword.equals(userInformation.getPassword())){
                return "密码错误!";
            }else {
                userServiceImp.UpdatePassword(id,newPassword);
                return "修改成功!";
            }
        }catch (Exception e){
            return "发生异常!";
        }
    }

}
