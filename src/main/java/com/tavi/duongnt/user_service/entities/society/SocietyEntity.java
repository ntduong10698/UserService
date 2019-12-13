package com.tavi.duongnt.user_service.entities.society;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@Table(name = "societies", schema = "dbo", catalog = "user_service")
public class SocietyEntity implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column (nullable = false)
    private String name;

    private String description;

    @NotNull
    @Column (nullable = false)
    private boolean status;
}
