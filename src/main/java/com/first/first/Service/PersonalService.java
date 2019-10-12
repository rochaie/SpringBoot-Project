package com.first.first.Service;

import com.first.first.bean.DetailedInformation;

public interface PersonalService {
    public DetailedInformation save(DetailedInformation detailedInformation);
    public DetailedInformation findAllByUser_id(Integer user_id);
}
