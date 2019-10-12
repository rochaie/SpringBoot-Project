package com.first.first.Service;

import com.first.first.DAO.UserRepository;
import com.first.first.bean.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    public List<UserInformation> getAll(){
        return userRepository.findAll();
    }

    public UserInformation findByAccountNumberAndPassword(String accountNumber, String passWord){
        return userRepository.findByAccountNumberAndPassword(accountNumber,passWord);
    }

    public UserInformation findByAccountNumber(String accountNumber){
        return userRepository.findByAccountNumber(accountNumber);
    }

    public UserInformation findByPassword(String passWord){
        return userRepository.findByPassword(passWord);
    }

    public UserInformation InsertRegister(UserInformation userInformation){
        return userRepository.save(userInformation);
    }

    public UserInformation findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public UserInformation findAllById(Integer id){
        return userRepository.findAllById(id);
    }

    public void UpdateUserName(Integer id,String userName){
        userRepository.UpdateUserName(id,userName);
    }

    public void UpdateEmail(Integer id,String newEmail){
        userRepository.UpdateEmail(id,newEmail);
    }

    public void UpdatePassword(Integer id,String password){
        userRepository.UpdatePassword(id,password);
    }

    public void UpdateImgName(Integer id,String imgName){
        userRepository.UpdateImgName(id,imgName);
    }
}
