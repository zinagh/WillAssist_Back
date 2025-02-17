package chat_bot.chat_bot.mapper;

import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.models.User;
import java.util.List;

public interface IuserMapper {
    UserDto userTouserdto (User user);
    User userdtoTouser (UserDto userdto);

    List<UserDto> usersTouserdtos (List<User> users);
    List<User> userdtosTousers(List<UserDto> userDtos);

}
