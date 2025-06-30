package FAQ.Faq.dto;

import lombok.*;

import java.util.Map;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private String answer;
    private int elapsed_ms;
    private String source;
    private Map<String, Object> debug;
}
