package com.first.first.bean;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Role;

import javax.persistence.*;
import java.sql.Blob;
import java.util.List;

@Getter
@Setter
@Entity
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String accountNumber;
    private String password;
    private String userName;
    private String role;
    private String imgName;
   @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "user_id")
    private DetailedInformation detailedInformation;
}
