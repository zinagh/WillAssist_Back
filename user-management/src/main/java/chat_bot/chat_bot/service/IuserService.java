package chat_bot.chat_bot.service;

import chat_bot.chat_bot.dto.UserDto;

public interface IuserService {
    UserDto retrieveUser(String userName);
}
