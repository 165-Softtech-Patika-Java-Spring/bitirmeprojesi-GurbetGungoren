package com.softtech.finalproject.security.sec;

import com.softtech.finalproject.model.UserEntity;
import com.softtech.finalproject.service.EntityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private  final UserEntityService userEntityService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userName = userEntityService.findByUserName(username);
        return JwtUserDetails.create(userName);
    }
    public UserDetails loadUserByUserName(Long id){
        UserEntity userName = userEntityService.getByIdWithControl(id);
        return JwtUserDetails.create(userName);
    }
}
