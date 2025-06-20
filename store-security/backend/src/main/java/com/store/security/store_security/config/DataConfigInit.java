package com.store.security.store_security.config;

import com.store.security.store_security.constants.RoleConstants;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataConfigInit implements CommandLineRunner {

    private final AuthoritiesRepository authoritiesRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("admin@gmail.com");
        user.setPassword("{bcrypt}$2a$12$iJm0Yh1KkHVdZne/Qhd.yuUl/zJYuDie340oLwPNCPcndbh1LfQ6C");
        user.setAge(20);
        user.setTmstInsert(LocalDateTime.now());

        AuthoritiesEntity authorities = new AuthoritiesEntity();
        authorities.setAuthority(RoleConstants.ADMIN.getRole());
        authorities.setUser(user);

        user.setAuthoritiesList(List.of(authorities));

        userRepository.save(user);
    }
}
