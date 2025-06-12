package com.store.security.store_security.service;

import com.store.security.store_security.entity.UserEntity;
import com.store.security.store_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSecurityDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(null != username)
        {
           Optional<UserEntity> user = userRepository.findByUsername(username);
           if(user.isPresent() && user.get().getId() > 0)
           {
               List<GrantedAuthority> authorities =
                       user.get().getAuthoritiesList().stream()
                               .map(auth->
                                       new SimpleGrantedAuthority(auth.getAuthority())).collect(Collectors.toList());
               return new User(user.get().getUsername(), user.get().getPassword(), authorities);
           }
        }
        throw new UsernameNotFoundException("User not found");
    }
}
