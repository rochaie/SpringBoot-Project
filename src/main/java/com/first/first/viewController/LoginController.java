package com.first.first.viewController;

import com.first.first.Configuration.JwtUtil;
import com.first.first.Service.MailServiceImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.UserInformation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class LoginController {
    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private MailServiceImp mailServiceImp;

    @RequestMapping("/login")
    public String login(){
       return "login";
    }

    @RequestMapping("/loginJudge")
    public String Judge(HttpServletRequest request,Model model,UserInformation userInformation){
        String accoutNumber=request.getParameter("accountNumber");
        String passWord=request.getParameter("passWord");
        UsernamePasswordToken userToken = new UsernamePasswordToken(accoutNumber, passWord);
        Subject subject = SecurityUtils.getSubject();
            try {
                subject.login(userToken);
                UserInformation user = (UserInformation) subject.getPrincipal();
                if (user.getAccountNumber().equals("819335491@qq.com")&&user.getPassword().equals("luochi123")){
                    request.getSession().setAttribute("admin","admin");
                }else {
                    request.getSession().setAttribute("admin",null);
                }
                request.getSession().setAttribute("id",user.getId());
                request.getSession().setAttribute("email",user.getAccountNumber());
                request.getSession().setAttribute("userName",user.getUserName());
               // request.getSession().setAttribute("isLogin", user);

                try {
                    String token = JwtUtil.CreateToken("1", "luo");
                    request.getSession().setAttribute("token",token);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return "redirect:/index";
            } catch (IncorrectCredentialsException e) {
                model.addAttribute("noAccount", "密码错误!");
                return "login";
            } catch (UnknownAccountException e) {
                model.addAttribute("noAccount", "账号不存在!");
                return "login";
            }catch (LockedAccountException e){
                model.addAttribute("noAccount","账号暂时冻结!");
                return "login";
            }
    }

    @RequestMapping("/logout")
    public String Logout(HttpServletRequest request){
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        request.getSession().setAttribute("isLogin",null);
        System.out.println("注销");
        return "redirect:/index";
    }

    @RequestMapping("/findPassword")
    @ResponseBody
    public String findPassword(@RequestParam(value = "findEmail",required = false)String email){
        UserInformation userInformation=userServiceImp.findByAccountNumber(email);
        if (userInformation==null){
            return "邮箱不存在!";
        }else {
            String to=email;
            String subject="密码";
            String content=userInformation.getPassword();
            try {
                mailServiceImp.sendMail(to, subject, content);
                return "密码已发送到邮箱!请查收！";
            }catch (Exception e){
                System.out.println("失败");
                return "发送失败!";
            }
        }
    }
}
