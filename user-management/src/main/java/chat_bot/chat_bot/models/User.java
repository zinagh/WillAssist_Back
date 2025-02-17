package chat_bot.chat_bot.models;

import chat_bot.chat_bot.dto.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "t_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userName;
    private String nom;
    private String prenom;
    private String email;
    private Date dateNaissance;
    private Long cin;
    private Long numTel ;
    private Role role ;

}
