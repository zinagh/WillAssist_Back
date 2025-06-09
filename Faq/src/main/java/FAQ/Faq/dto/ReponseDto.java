package FAQ.Faq.dto;

import FAQ.Faq.models.Question;
import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReponseDto {
    private Long idReponse;
    private String responseText;
    private String createdby;
    private Date creationDate;
    private Question question;


}
