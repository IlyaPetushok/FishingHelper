package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.auth_service.exception.UserNotFoundException;
import fishinghelper.auth_service.mapper.UserMapper;
import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.RoleType;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.service.KeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RegistrationService {
    private final String SUBJECT="Confirm Email";

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepositories roleRepositories;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final KeyCloakService keyCloakService;

    @Autowired
    public RegistrationService(UserRepositories userRepositories, PasswordEncoder passwordEncoder, RoleRepositories roleRepositories, UserMapper userMapper, EmailService emailService, KeyCloakService keyCloakService) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositories = roleRepositories;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.keyCloakService = keyCloakService;
    }

    /**
     * Service class for creating new users.
     * This class handles the registration process by encoding the user's password,
     * mapping the DTO to the entity, and saving the user to the database.
     *
     * @param userDTORequestRegistration DTO containing user registration details
     */

    public void createUser(UserDTORequestRegistration userDTORequestRegistration) {
        keyCloakService.addUser(userMapper.toEntity(userDTORequestRegistration));

        userDTORequestRegistration.setPassword(passwordEncoder.encode(userDTORequestRegistration.getPassword()));
        User user=userMapper.toEntity(userDTORequestRegistration);

        List<Role> roleList = List.of(roleRepositories.findRoleByName(RoleType.USER.name()));
        user.setRoles(roleList);
        user.setDateRegistration(new Date());
        user=userRepositories.save(user);

//add rabit mq
        String encodedEmail = Base64.getEncoder().encodeToString(user.getMail().getBytes(StandardCharsets.UTF_8));
        String body="Dear "+user.getName()+"!!!\n"
                +"Please confirm your mail for FishingHelpers\n"
                +"http://localhost:8081/auth/confirm/email/"
                + encodedEmail;

        emailService.sendMessage(user,SUBJECT,body);

        log.info("User successfully registered");
    }

    /**
     * Service class .
     * This class handles the registration process by encoding the user's password,
     * mapping the DTO to the entity, and saving the user to the database.
     *
     * @param email this mail for confirm user
     */
    public void updateStatusConfirmEmail(String email){
        log.info("User confirm mail ....");

        String mailDecode=new String(Base64.getDecoder().decode(email), StandardCharsets.UTF_8);
        User user =userRepositories.findUserByMail(mailDecode);

        if (user == null) {
            log.error("User not found for email: " + mailDecode);
            throw new UserNotFoundException(HttpStatus.NOT_FOUND,"user not found by email:" +mailDecode);
        }

        user.setMailStatus(true);
        userRepositories.save(user);
        keyCloakService.updateUserStatusEmail(user.getLogin());
        log.info("User successfully confirm mail");
    }
}
