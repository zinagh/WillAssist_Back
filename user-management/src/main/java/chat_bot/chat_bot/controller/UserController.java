package chat_bot.chat_bot.controller;

import chat_bot.chat_bot.configuration.KeycloakSecurity;
import chat_bot.chat_bot.dto.UserDto;
import chat_bot.chat_bot.mapper.Imapper;
import chat_bot.chat_bot.models.User;
import chat_bot.chat_bot.service.IuserService;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {
    @Autowired
    IuserService userService;
    @Autowired
    KeycloakSecurity keycloakSecurity;
    @Autowired
    Imapper imapper;
    @Value("${realm}")
    private String realm ;

    @PostMapping("/add-user")
    public Response addUser(@RequestBody UserDto u) {
        userService.addUser(u);
        return Response.ok().build();
    }

    @PutMapping("/modify-user")
    public Response modifyUser(@RequestBody UserDto u) {
        User user = userService.modifyUser(u);
        return Response.ok(u).build();
    }
    @PutMapping("/updatepass")
    public Response updatepassword(@RequestParam String username,
                                   @RequestParam String newpass ,
                                   @RequestParam String veripass) {
        String message= userService.updatepassword(username,newpass,veripass);
        return Response.ok(message).build();
    }



    @GetMapping("/retrieve-all-users")
    public List<UserDto> retrieveAllUsers() {
        List<UserDto> listUserdtos = userService.retrieveAllUsers();
        return listUserdtos;
    }

    @GetMapping("/retrieve-user/{userName}")
    public UserDto retrieveUser(@PathVariable("userName") String userName) {
        UserDto userdto = userService.retrieveUser(userName);
        return userdto;
    }

  @DeleteMapping("/remove-user/{userName}")
    public void removeUser(@PathVariable("userName") String userName) {
      userService.removeUser(userName);
    }

}
