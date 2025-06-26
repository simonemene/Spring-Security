package com.store.security.store_security.config;

import com.store.security.store_security.constants.RoleConstants;
import com.store.security.store_security.dto.StockDto;
import com.store.security.store_security.entity.AuthoritiesEntity;
import com.store.security.store_security.entity.StockEntity;
import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.repository.AuthoritiesRepository;
import com.store.security.store_security.repository.StockRepository;
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
    private final StockRepository stockRepository;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("admin@gmail.com");
        user.setPassword("{bcrypt}$2a$12$jl2ifkyae8I/Tkg9OvnaK.rc.qkjX.N9CXrRkrOJmcW8BG5LAWJwq");
        user.setAge(20);
        user.setTmstInsert(LocalDateTime.now());

        AuthoritiesEntity authorities = new AuthoritiesEntity();
        authorities.setAuthority(RoleConstants.ADMIN.getRole());
        authorities.setUser(user);


        user.setAuthoritiesList(List.of(authorities));

        StockEntity stock = StockEntity.builder().build();

        stockRepository.save(stock);
        userRepository.save(user);
        authoritiesRepository.save(authorities);
    }
}
