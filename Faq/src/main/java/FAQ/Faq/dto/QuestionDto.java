package FAQ.Faq.dto;

import FAQ.Faq.models.Categorie;
import FAQ.Faq.models.Reponse;
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
    private ReponseDto reponse;
    private CategorieDto categorie;


}
