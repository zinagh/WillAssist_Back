package FAQ.Faq.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    private String question;
    private String username;
}
