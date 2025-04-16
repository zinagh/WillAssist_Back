package chat_bot.chat_bot.service;

import chat_bot.chat_bot.configuration.KeycloakSecurity;
import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.mapper.Imapper;
import chat_bot.chat_bot.mapper.UserMapper;
import chat_bot.chat_bot.models.User;
import chat_bot.chat_bot.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService  implements IuserService {
    @Autowired
    UserRepo userRepository;

    @Autowired
    UserMapper usermapper;


    @Autowired
    KeycloakSecurity keycloakSecurity;
    @Value("${realm}")
    private String realm;

    @Autowired
    Imapper imapper;

    private final WebClient.Builder webClient;
    @Value("${principle-attribute}")
    private String principleAttribut;
    private SecurityContextHolder securityContextHolder;
    @Override
    public UserDto retrieveUser(String userName) {
        User user = userRepository.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName));

        return usermapper.userTouserdto(user);
    }


    @Override
    public List<UserDto> retrieveAllUsers() {
        List<User>  users =userRepository.findAll();
        List<UserDto> userdtos = usermapper.usersTouserdtos(users);
        return userdtos;
    }

    @Override
    public void addUser(UserDto userdto ) {
        UserRepresentation userRepresentation = imapper.mapuserRep(userdto);
        System.out.println(userRepresentation);
        Keycloak keycloak =keycloakSecurity.getKeycloakInstance();
        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if(response.getStatus() == 201) {
            String userId = keycloak.realm(realm).users()
                    .search(userRepresentation
                            .getUsername()).get(0).getId();
            imapper.assignerole(userdto.getRole().toString() ,userId);
            User user= usermapper.userdtoTouser(userdto) ;
            System.out.println("Saving user to MySQL: " + user);
            userRepository.save(user);
            System.out.println("User saved successfully!");
        }

    }
    @Override
    public User modifyUser(UserDto userdto) {
        Keycloak keycloak = keycloakSecurity.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak
                .realm(realm).users()
                .search(userdto.getUserName());

        if (!userRepresentations.isEmpty()) {
            String id = userRepresentations.get(0).getId();
            UserRepresentation userToUpdate = imapper.mapuserRepToUpdate(userdto);
            keycloak.realm(realm).users().get(id).update(userToUpdate);
            User user = usermapper.userdtoTouser(userdto);
            userRepository.save(user);
            return user;
        } else {
            // Handle the case where no user with the given username is found
            throw new NoSuchElementException("No user found with the given username");
        }
    }
    @Override
    public String updatepassword(String username ,String newpass ,String verifpass){
        if (newpass.equals(verifpass)) {
            Keycloak keycloak = keycloakSecurity.getKeycloakInstance();
            List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(username);
            UserRepresentation userRepresentation = userRepresentations.get(0);
            List<CredentialRepresentation> creds = new ArrayList<>();
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setTemporary(false);
            cred.setValue(newpass);
            creds.add(cred);
            userRepresentation.setCredentials(creds);
            userRepresentation.setRequiredActions(Collections.emptyList());
            keycloak.realm(realm).users().get(userRepresentation.getId()).update(userRepresentation);
            return "password is updated";
        } return "comparaison failed";
    }

    public String getUsername() {
        Authentication authentication = securityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return (String) jwt.getClaim(principleAttribut);
        }
        throw new IllegalStateException("Could not retrieve token from SecurityContext");
    }





    @Override
    public void removeUser(String userName) {
        Keycloak keycloak = keycloakSecurity.getKeycloakInstance();
        List<UserRepresentation> userRepresentations = keycloak.realm(realm).users().search(userName);
        String id = userRepresentations.get(0).getId();
        keycloak.realm(realm).users().delete(id);
        userRepository.deleteById(userName);


    }
}
