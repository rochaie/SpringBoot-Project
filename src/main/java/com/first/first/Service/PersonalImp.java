package com.first.first.Service;

import com.first.first.DAO.PersonalRepository;
import com.first.first.bean.DetailedInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalImp implements PersonalService {
    @Autowired
    private PersonalRepository personalRepository;

    public DetailedInformation save(DetailedInformation detailedInformation){
        return personalRepository.save(detailedInformation);
    }

    public DetailedInformation findAllByUser_id(Integer user_id){
       return personalRepository.findAllByUserid(user_id);
    }
}
