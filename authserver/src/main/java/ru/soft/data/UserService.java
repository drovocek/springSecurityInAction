package ru.soft.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.data.model.Otp;
import ru.soft.data.model.User;
import ru.soft.data.repo.OtpRepository;
import ru.soft.data.repo.UserRepository;

import java.util.Optional;

import static ru.soft.data.GenerateCodeUtil.generateCode;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final String BAD_CRED_EXC_TEXT = "Bad credentials";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public void addUser(User user) {
        log.info("add user {}", user);

        Optional<User> userByUsernameOpt = this.userRepository.findUserByUsername(user.getUsername());
        if (userByUsernameOpt.isPresent()) {
            throw new BadCredentialsException(BAD_CRED_EXC_TEXT);
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        log.info("add user SUCCESS");
    }

    public boolean auth(User user) {
        log.info("auth user {}", user);
        String username = user.getUsername();
        Optional<User> dbUserOpt = this.userRepository.findUserByUsername(username);

        if (dbUserOpt.isPresent()) {
            User dbUser = dbUserOpt.get();
            validatePassword(user.getPassword(), dbUser.getPassword());
            renewOtp(username);
            log.info("auth - SUCCESS");
            return true;
        }

        log.info("auth - ERROR");
        return false;
    }

    private void validatePassword(String rawPassword, String password) {
        log.info("validatePassword");
        if (!this.passwordEncoder.matches(rawPassword, password)) {
            log.info("validatePassword - ERROR");
            throw new BadCredentialsException(BAD_CRED_EXC_TEXT);
        }
        log.info("validatePassword - SUCCESS");
    }

    private void renewOtp(String username) {
        log.info("renewOtp by username {}", username);
        String code = generateCode();
        Optional<Otp> userOtp = this.otpRepository.findOtpByUsername(username);

        if (userOtp.isPresent()) {
            log.info("renewOtp UPDATE");
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            log.info("renewOtp ADD NEW");
            Otp otp = new Otp();
            otp.setUsername(username);
            otp.setCode(code);
            this.otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = this.otpRepository.findOtpByUsername(otpToValidate.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            return otpToValidate.getCode().equals(otp.getCode());
        }

        return false;
    }
}