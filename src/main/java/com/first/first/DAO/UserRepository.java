package com.first.first.DAO;

import com.first.first.bean.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserInformation,String> {
    public UserInformation findByAccountNumberAndPassword(String accountNumber,String passWord);
    public UserInformation findByAccountNumber(String accountNumber);
    public UserInformation findByPassword(String passWord);
    public UserInformation findByUserName(String userName);
    public UserInformation findAllById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update UserInformation u set u.userName=:userName where id=:id")
    public void UpdateUserName(@Param("id") Integer id,@Param("userName") String userName);

    @Transactional
    @Modifying
    @Query(value = "update UserInformation u set u.accountNumber=:newEmail where id=:id")
    public void UpdateEmail(@Param("id") Integer id,@Param("newEmail") String newEmail);

    @Transactional
    @Modifying
    @Query(value = "update UserInformation u set u.password=:password where id=:id")
    public void UpdatePassword(@Param("id") Integer id,@Param("password") String password);

    @Transactional
    @Modifying
    @Query(value = "update UserInformation  u set u.imgName=:imgName where id=:id")
    public void UpdateImgName(@Param("id")Integer id,@Param("imgName")String imgName);

}
