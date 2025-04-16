package FAQ.Faq.dto;

import FAQ.Faq.models.Reponse;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long idQuestion;
    private String questionText;
    private String createdby;
    private Date creationDate;
    private UserDto userdto;
    private Reponse reponse;


}
