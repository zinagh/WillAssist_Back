package FAQ.Faq.dto;

import FAQ.Faq.models.Question;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategorieDto {
    private Long idCategorie;
    private String categorieName;
}
