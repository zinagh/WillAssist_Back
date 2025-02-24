package chat_bot.chat_bot.service;

import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.models.User;

import java.util.List;

public interface IuserService {
    UserDto retrieveUser(String userName) ;

    List<UserDto> retrieveAllUsers();
    void addUser(UserDto userdto );
    public User modifyUser(UserDto userdto);
    String updatepassword(String username ,String newpass ,String verifpass);

    void removeUser(String userName);
}
