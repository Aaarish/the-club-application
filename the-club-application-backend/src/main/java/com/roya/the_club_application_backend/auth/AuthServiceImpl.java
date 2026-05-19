package com.roya.the_club_application_backend.auth;

import com.roya.the_club_application_backend.auth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDao userDao;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(String phone, String email, String username, String password) {
        AppUser user = new AppUser(phone, email, username, passwordEncoder.encode(password));
        AppUser savedUser = userDao.save(user);

        AuthUser authUser = new AuthUser(savedUser);
        log.info("username: {}", authUser.getUsername());

        String token = jwtUtil.generateToken(authUser);

        return AuthResponse.builder()
                .userId(savedUser.getUserId())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse login(String phone, String password) {
        // username + password verification check: if the entered password on hashing matches the corresponding hashed password for the given username
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

        // once username and password verification checks are successful, a jwt token is generated
        String token = jwtUtil.generateToken(userDetails);

        // jwt token is returned in response
        return AuthResponse.builder()
                .userId(userDetails.getUsername())
                .token(token)
                .build();
    }

}
