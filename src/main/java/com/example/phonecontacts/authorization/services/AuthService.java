package com.example.phonecontacts.authorization.services;

import com.example.phonecontacts.authorization.interfaces.IAuth;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dao.repositories.ConfirmationTokenRepository;
import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;
import com.example.phonecontacts.email.EmailService;
import com.example.phonecontacts.entities.ConfirmationToken;
import com.example.phonecontacts.entities.User;
import com.example.phonecontacts.exceptions.APIException;
import com.example.phonecontacts.exceptions.VerifyEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements IAuth {

    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final EmailService emailService;
    private final IUserDao iUserDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService, IUserDao iUserDao, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
        this.iUserDao = iUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Optional<User> optionalUser = iUserDao.findByUsernameOrEmail(loginDto.getLogin(), loginDto.getLogin());

        if (optionalUser.isPresent() && optionalUser.get().isEnabled()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getLogin(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "User signed-in successfully!";
        }

        throw new VerifyEmailException("Please verify email");
    }

    @Override
    public String register(SignUpDto signUpDto) {
        if (iUserDao.existsByUsername(signUpDto.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (iUserDao.existsByEmail(signUpDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        User user = User.builder()
                .name(signUpDto.getName())
                .userName(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .isEnabled(false)
                .build();

        iUserDao.create(user);

        sendEmailToUser(user);

        return "Verify email by the link sent on your email address";
    }

    @Override
    public String confirmEmail(String confirmationToken) {
        Optional<ConfirmationToken> tokenOptional = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (tokenOptional.isPresent()) {
            Optional<User> optionalUser = iUserDao.findUserByEmail(tokenOptional.get().getUser().getEmail());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setEnabled(true);
                iUserDao.update(user, user.getId());
                return "Email verified successfully!";
            }
        }
        throw new VerifyEmailException("Error: Couldn't verify email");
    }

    private void sendEmailToUser(User user) {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .user(user)
                .createdDate(Date.valueOf(LocalDate.now()))
                .confirmationToken(UUID.randomUUID().toString())
                .build();

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
        new Thread(() -> emailService.sendEmail(mailMessage)).start();

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
    }
}
