package com.roya.the_club_application_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final AppUserDao userDao;

    @Override
    public AuthUser loadUserByUsername(String phone) throws UsernameNotFoundException {
        return userDao.findByPhoneNumber(phone)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phone));
    }

}
