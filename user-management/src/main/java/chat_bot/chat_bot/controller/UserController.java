package chat_bot.chat_bot.controller;

import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    @Autowired
    IuserService userService;
    @GetMapping("/retrieve-user/{userName}")
    public UserDto retrieveUser(@PathVariable("userName") String userName) {
        UserDto userdto = userService.retrieveUser(userName);
        return userdto;
    }
}
