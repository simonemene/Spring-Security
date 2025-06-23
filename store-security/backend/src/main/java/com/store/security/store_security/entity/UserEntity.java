package com.store.security.store_security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    private int age;

    @Column(name = "tmst_insert", nullable = false)
    private LocalDateTime tmstInsert;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<AuthoritiesEntity> authoritiesList;

}
