package fishinghelper.security_server.service;

import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.exception.UserFailedCreateException;
import fishinghelper.security_server.exception.UserNotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class for managing users in Keycloak.
 * Handles user creation, password updates, and email verification.
 * <p>
 * Author: Ilya Petushok
 */
@Slf4j
@Service
public class KeyCloakService {
    @Value("${spring.security.keycloak.realm.name}")
    private String realmName;

    private final Keycloak keycloak;

    @Autowired
    public KeyCloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    /**
     * Creates a password credentials representation.
     *
     * @param password The password.
     * @return The CredentialRepresentation object with the password.
     */
    private CredentialRepresentation createPasswordCredentials(String password){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    /**
     * Adds a new user to Keycloak and assigns a role.
     * <p>
     * If the user creation fails, throws a {@link UserFailedCreateException}.
     *
     * @param user The user object containing user details.
     */
    public void addUser(User user){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getLogin());
        userRepresentation.setFirstName(user.getName());
        userRepresentation.setLastName(user.getName());
        userRepresentation.setEmail(user.getMail());
        userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(user.getPassword())));
        userRepresentation.setEnabled(true);

        UsersResource usersResource = keycloak.realm(realmName).users();
        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            log.info("User created int keycloak successfully.");
        } else {
            log.error("Failed to create keycloak user. HTTP status: " + response.getStatus());
            log.error("Response body: " + response.readEntity(String.class));
            throw new UserFailedCreateException(HttpStatus.BAD_REQUEST,"Failed to create user");
        }

        String userId = response.getLocation().getPath().split("/")[5];
        assignRolesToUser(userId, Collections.singletonList("USER_REALM"));
    }

    /**
     * Assigns roles to a user.
     *
     * @param userId   The ID of the user.
     * @param roleNames List of role names to be assigned.
     */
    private void assignRolesToUser(String userId, List<String> roleNames) {
        UserResource userResource = keycloak.realm(realmName).users().get(userId);

        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> keycloak.realm(realmName).roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());

        userResource.roles().realmLevel().add(roles);
    }

    /**
     * Updates the password for an existing user.
     *
     * @param login    The login (username) of the user.
     * @param password The new password for the user.
     */
    public void updateUserPassword(String login, String password){
        UserRepresentation userRepresentation=findUserByName(login);
        userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(password)));
        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    /**
     * Updates the email verification status for an existing user.
     *
     * @param login The login (username) of the user.
     */
    public void updateUserStatusEmail(String login){
        UserRepresentation userRepresentation=findUserByName(login);
        userRepresentation.setEmailVerified(true);
        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    /**
     * Finds a user by their login
     *
     * @param login The username of the user.
     * @return The UserRepresentation object for the user.
     */
    public UserRepresentation findUserByName(String login){
        UsersResource usersResource = keycloak.realm(realmName).users();
        List<UserRepresentation> users = usersResource.search(login, true);

        if (users.isEmpty()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found in keycloak");
        }

        return users.get(0);
    }

    public void updateUserRole(User user){
        UserRepresentation userRepresentation = findUserByName(user.getLogin());
        List<RoleRepresentation> roles = user.getRoles().stream()
                .map(roleName -> keycloak.realm(realmName).roles().get(roleName.getName()+"_REALM").toRepresentation())
                .collect(Collectors.toList());

        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.get(userRepresentation.getId()).roles().realmLevel().add(roles);
    }
}
