package com.example.nagoyameshi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;    
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;        
    }
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  
        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした。"));
            String userRoleName = user.getRole().getName();
            Collection<GrantedAuthority> authorities = new ArrayList<>();         
            authorities.add(new SimpleGrantedAuthority(userRoleName));
            return new UserDetailsImpl(user, authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }
    }   

}
