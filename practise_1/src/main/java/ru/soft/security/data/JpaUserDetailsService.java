package ru.soft.security.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.soft.data.model.User;
import ru.soft.data.repo.UserRepository;

import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final Supplier<UsernameNotFoundException> problemAuthExc = () -> new UsernameNotFoundException("Problem during authentication!");
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        log.info("loadUserByUsername '{}'", username);
        User user = this.userRepository.findUserByUsername(username).orElseThrow(problemAuthExc);
        log.info("loadUserByUsername SUCCESS");
        return new CustomUserDetails(user);
    }
}
