package chat_bot.chat_bot.dto;


import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private Long cin;
    private Long numTel ;
    private Role role ;

}
