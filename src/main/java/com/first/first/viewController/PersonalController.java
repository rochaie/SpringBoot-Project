package com.first.first.viewController;

import com.first.first.Service.PersonalImp;
import com.first.first.Service.UserServiceImp;
import com.first.first.bean.DetailedInformation;
import com.first.first.bean.UserInformation;

import com.sun.mail.util.BASE64DecoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

@Controller
public class PersonalController {
    @Autowired
    private PersonalImp personalImp;

    @Autowired
    private UserServiceImp userServiceImp;

    @RequestMapping("/personal")
    public String Personal(HttpServletRequest request, Model model){
        UserInformation userInformation=userServiceImp.findAllById((Integer) request.getSession().getAttribute("id"));
        model.addAttribute("email",userInformation.getAccountNumber());
        model.addAttribute("userName",userInformation.getUserName());
        DetailedInformation detailedInformations=personalImp.findAllByUser_id((Integer)request.getSession().getAttribute("id"));
        try {
            model.addAttribute("imgName",userInformation.getImgName());
            model.addAttribute("qq", detailedInformations.getQq());
            model.addAttribute("age", detailedInformations.getAge());
            model.addAttribute("area", detailedInformations.getArea());
            model.addAttribute("sex", detailedInformations.getSex());
            model.addAttribute("hobies", detailedInformations.getHobies());
            model.addAttribute("isFootballFans", detailedInformations.getIsFootballFans());
            model.addAttribute("introduce", detailedInformations.getIntroductino());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "personal";
    }

    @RequestMapping("/changeInformation")
    @ResponseBody
    public String Change(@RequestParam(value = "user_name",required = false) String userName,
                         @RequestParam(value = "qq",required = false) String qq,
                         @RequestParam(value = "age",required = false) String age,
                         @RequestParam(value = "area",required = false) String area,
                         @RequestParam(value = "sex",required = false) String sex,
                         @RequestParam(value = "like",required = false) String hobies,
                         @RequestParam(value = "isFootballFans",required = false) String isFootballFans,
                         @RequestParam(value = "introduce",required = false) String introduce,
                         HttpServletRequest request){
        Integer id=(Integer) request.getSession().getAttribute("id");
        UserInformation userInformation=userServiceImp.findAllById(id);


        DetailedInformation detailedInformation=personalImp.findAllByUser_id(id);
        detailedInformation.setUserid(id);
        detailedInformation.setAge(age);
        detailedInformation.setArea(area);
        detailedInformation.setQq(qq);
        detailedInformation.setSex(sex);
        detailedInformation.setHobies(hobies);
        detailedInformation.setIsFootballFans(isFootballFans);
        detailedInformation.setIntroductino(introduce);

        try {
            if (userName == null) {
                return "修改失败,用户名不能为空!";

            } else if (!userName.equals(userInformation.getUserName()) && userServiceImp.findByUserName(userName) != null) {
                return "用户名已存在!";
            } else {

                    userServiceImp.UpdateUserName(id, userName);
                    personalImp.save(detailedInformation);
                    return "修改成功!";
                }
            }catch(Exception e){
                return "出现错误!";
            }
    }

    @RequestMapping(value = "imgSave")
    @ResponseBody
    public String imgSave(@RequestParam (value = "imgData",required = false)String imgData
            ,HttpServletRequest request){
        imgData = imgData.replaceAll("%3A", ":");
        imgData = imgData.replaceAll("%2F", "/");
        imgData = imgData.replaceAll("%3B", ";");
        imgData = imgData.replaceAll("%2C", ",");
        imgData = imgData.replaceAll("%2B", "+");
        imgData = imgData.replaceAll("%3D", "=");
        Integer id=(Integer) request.getSession().getAttribute("id");
        UserInformation userInformation=userServiceImp.findAllById(id);
        try {
            userServiceImp.UpdateImgName(id,imgData);
            return "上传成功!";
        }catch (Exception e){
            e.printStackTrace();
            return "上传错误!";
        }
}
}
