package ru.musicapp.coreservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musicapp.coreservice.mapper.RoleMapper;
import ru.musicapp.coreservice.model.UserExtendedDetails;
import ru.musicapp.coreservice.model.entity.user.User;
import ru.musicapp.coreservice.repository.UserRepository;

import java.util.stream.Collectors;

@Service("musicAppUserDetailService")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByCredentials_Login(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " is not found"));

        return new UserExtendedDetails(
                user.getId(),
                username,
                user.getCredentials().getPassword(),
                user.getRoles().stream()
                        .map(roleMapper::toDto)
                        .collect(Collectors.toSet())
        );
    }

}
