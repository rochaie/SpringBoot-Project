package com.first.first.Service;

import com.first.first.bean.UserInformation;

import java.util.List;

public interface UserService {
    List<UserInformation> getAll();
    UserInformation findByAccountNumberAndPassword(String accountNumber, String passWord);
    UserInformation findByAccountNumber(String accountNumber);
    UserInformation findByPassword(String passWord);
    UserInformation InsertRegister(UserInformation userInformation);
    UserInformation findByUserName(String userName);
    UserInformation findAllById(Integer id);
    void UpdateUserName(Integer id,String userName);
    void UpdateEmail(Integer id,String newEmail);
    void UpdatePassword(Integer id,String password);
    void UpdateImgName(Integer id,String imgName);
}
