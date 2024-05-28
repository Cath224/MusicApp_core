package ru.musicapp.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.dto.security.AuthDto;
import ru.musicapp.coreservice.model.dto.security.JwtTokenResponse;
import ru.musicapp.coreservice.security.JwtGenerator;
import ru.musicapp.coreservice.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtGenerator jwtGenerator;

    private final AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("musicAppUserDetailService")
    private UserDetailsService userDetailsService;

    @Transactional(readOnly = true)
    @Override
    public JwtTokenResponse auth(AuthDto dto) {
        UserExtendedDetails userExtendedDetails = (UserExtendedDetails) userDetailsService.loadUserByUsername(dto.getLogin());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getLogin(),
                dto.getPassword()
        ));

        String token = jwtGenerator.generateJwt(userExtendedDetails);
        return JwtTokenResponse.builder()
                .token(token)
                .build();
    }
}
