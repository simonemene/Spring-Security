package com.store.security.store_security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="authorities")
@Entity
public class AuthoritiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String authority;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

}
