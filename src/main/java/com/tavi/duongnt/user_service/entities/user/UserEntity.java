package com.tavi.duongnt.user_service.entities.user;

import com.sun.istack.Nullable;
import com.tavi.duongnt.user_service.entities.society.SocietyEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
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

    private Boolean gender;

    private String address;

    private String birthday;

    @Column(name = "init_date", nullable = false)
    @Nullable
    private String initDate;

    @Nullable
    @Column(nullable = false)
    private int status;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "society_id")
    private SocietyEntity society;
}
