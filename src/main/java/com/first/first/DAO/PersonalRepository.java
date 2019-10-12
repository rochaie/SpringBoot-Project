package com.first.first.DAO;

import com.first.first.bean.DetailedInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<DetailedInformation,String> {
    public DetailedInformation findAllByUserid(Integer user_id);
}
