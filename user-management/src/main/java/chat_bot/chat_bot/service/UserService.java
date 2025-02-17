package chat_bot.chat_bot.service;

import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.mapper.UserMapper;
import chat_bot.chat_bot.models.User;
import chat_bot.chat_bot.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService  implements IuserService {
    @Autowired
    UserRepo userRepository;

    @Autowired
    UserMapper usermapper;
    @Override
    public UserDto retrieveUser(String userName) {
        User user=  userRepository.findById(userName).get();
        UserDto userdto = usermapper.userTouserdto(user);
        return userdto;
    }
}
