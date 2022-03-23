package com.softtech.finalproject.model;

import com.softtech.finalproject.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="USERS")
public class UserEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "UserEntity" , sequenceName = "USERS_ID_SEQ")
    @GeneratedValue(generator = "UserEntity")
    private Long id;
    @Column(name = "NAME",nullable = false)
    private String name;
    @Column(name = "SURNAME",nullable = false)
    private String surname;
    @Column(name = "USERNAME",nullable = false,unique = true)
    private String userName;
    @Column(name = "PASSWORD",nullable = false)
    private String password;
}
