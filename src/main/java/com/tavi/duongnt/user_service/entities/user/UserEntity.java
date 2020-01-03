package com.tavi.duongnt.user_service.entities.user;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_User_CMS", schema = "dbo")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String email;

    @Column(name= "phone_number")
    private String phoneNumber;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private Integer gender;

    private String address;

    private String birthday;

    private Integer social;

    @Column(name = "init_date", nullable = false)
    @Nullable
    private String initDate;

    @Nullable
    @Column(nullable = false)
    private Integer status;

    private Boolean deleted;

}
