package chat_bot.chat_bot.mapper;

import chat_bot.chat_bot.dto.UserDto;
import org.keycloak.representations.idm.UserRepresentation;

public interface Imapper {
    UserRepresentation mapuserRep (UserDto userdto);
    void assignerole(String role , String id);

    UserRepresentation mapuserRepToUpdate(UserDto  userdto);


}