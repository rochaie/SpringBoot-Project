package com.first.first.bean;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class DetailedInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    private String sex;
    private String age;
    private String qq;
    private String area;
    private String hobies;
    private String isFootballFans;
    private String introductino;
}
