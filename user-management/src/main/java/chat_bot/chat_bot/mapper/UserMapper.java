package chat_bot.chat_bot.mapper;

import chat_bot.chat_bot.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chat_bot.chat_bot.models.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper  implements IuserMapper {


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto userTouserdto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User userdtoTouser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public List<UserDto> usersTouserdtos(List<User> users) {
        return users.stream()
                .map(this::userTouserdto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> userdtosTousers(List<UserDto> userDtos) {
        return userDtos.stream()
                .map(this::userdtoTouser)
                .collect(Collectors.toList());
    }
}
