package fishinghelper.security_server.service;

import fishinghelper.common_module.entity.user.User;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyCloakService {
    @Value("${spring.security.keycloak.realm.name}")
    private String realmName;

    private final Keycloak keycloak;

    @Autowired
    public KeyCloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    private CredentialRepresentation createPasswordCredentials(String password){
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

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
            System.out.println("User created successfully.");
        } else {
            System.out.println("Failed to create user. HTTP status: " + response.getStatus());
            System.out.println("Response body: " + response.readEntity(String.class));
        }

        String userId = response.getLocation().getPath().split("/")[5];
        assignRolesToUser(userId, Collections.singletonList("USER_REALM"));
    }

    private void assignRolesToUser(String userId, List<String> roleNames) {
        UserResource userResource = keycloak.realm(realmName).users().get(userId);

        List<RoleRepresentation> roles = roleNames.stream()
                .map(roleName -> keycloak.realm(realmName).roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());

        userResource.roles().realmLevel().add(roles);
    }

    public void updateUserPassword(String login, String password){
        UserRepresentation userRepresentation=findUserByName(login);
        userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(password)));
        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    public void updateUserStatusEmail(String login){
        UserRepresentation userRepresentation=findUserByName(login);
        userRepresentation.setEmailVerified(true);
        UsersResource usersResource = keycloak.realm(realmName).users();
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    public UserRepresentation findUserByName(String userName){
        UsersResource usersResource = keycloak.realm(realmName).users();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user.get(0);
    }
}
