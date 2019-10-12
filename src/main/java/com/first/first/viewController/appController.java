package com.first.first.viewController;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class appController {

    @PostMapping("/firstRequest")
    @ResponseBody
    public HashMap<String,Object> home(@RequestBody JSONObject jsonObject){
        HashMap<String, Object> map=new HashMap<String, Object>();
        System.out.println(jsonObject);
         if (jsonObject.get("accountNumber").toString().equals("123")&&jsonObject.get("passWord").toString().equals("123")){
             map.put("loginmsg","ok");
         }else {
             map.put("loginmsg","no");
         }
         return map;
    }
}
