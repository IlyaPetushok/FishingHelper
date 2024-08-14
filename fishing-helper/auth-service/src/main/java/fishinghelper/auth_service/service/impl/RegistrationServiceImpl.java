package fishinghelper.auth_service.service.impl;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.auth_service.exception.UserNotFoundException;
import fishinghelper.auth_service.mapper.UserMapper;
import fishinghelper.auth_service.service.RegistrationService;
import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.RoleType;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.config.RabbitConfig;
import fishinghelper.notification_service.messaging.producer.RabbitMQProducer;
import fishinghelper.security_server.service.KeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(
        isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED
)
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepositories roleRepositories;
    private final UserMapper userMapper;
    private final KeyCloakService keyCloakService;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public RegistrationServiceImpl(UserRepositories userRepositories, PasswordEncoder passwordEncoder, RoleRepositories roleRepositories, UserMapper userMapper, KeyCloakService keyCloakService, RabbitMQProducer rabbitMQProducer) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositories = roleRepositories;
        this.userMapper = userMapper;
        this.keyCloakService = keyCloakService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    /**
     * Service class for creating new users.
     * This class handles the registration process by encoding the user's password,
     * mapping the DTO to the entity, and saving the user to the database.
     *
     * @param userDTORequestRegistration DTO containing user registration details
     */
    @Override
    public void createUser(UserDTORequestRegistration userDTORequestRegistration) {
        User user = userMapper.toEntity(userDTORequestRegistration);
        user.setPassword(passwordEncoder.encode(userDTORequestRegistration.getPassword()));

        List<Role> roleList = List.of(roleRepositories.findRoleByName(RoleType.USER.name()));
        user.setRoles(roleList);
        user.setDateRegistration(new Date());
        user = userRepositories.save(user);

        keyCloakService.addUser(userMapper.toEntity(userDTORequestRegistration));

        rabbitMQProducer.sendMessageQueue(user.getMail(), RabbitConfig.ROUTING_KEY_2);

        log.info("User successfully registered");
    }

    /**
     * Service class .
     * This class handles the registration process by encoding the user's password,
     * mapping the DTO to the entity, and saving the user to the database.
     *
     * @param email this mail for confirm user
     */
    @Override
    public void updateStatusConfirmEmail(String email) {
        log.info("User confirm mail ....");

        String mailDecode = new String(Base64.getDecoder().decode(email), StandardCharsets.UTF_8);
        User user = userRepositories.findUserByMail(mailDecode);

        if (user == null) {
            log.error("User not found for email: " + mailDecode);
            throw new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by email:" + mailDecode);
        }

        user.setMailStatus(true);
        userRepositories.save(user);
        keyCloakService.updateUserStatusEmail(user.getLogin());
        log.info("User successfully confirm mail");
    }
}
