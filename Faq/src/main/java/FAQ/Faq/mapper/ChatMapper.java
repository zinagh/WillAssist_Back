package FAQ.Faq.mapper;

import FAQ.Faq.dto.ChatRequest;
import FAQ.Faq.dto.ChatResponse;
import FAQ.Faq.models.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper  implements IChatMapper{

    public ChatMessage toEntity(ChatRequest req, ChatResponse resp) {
        ChatMessage m = new ChatMessage();
        m.setUsername(req.getUsername());
        m.setUserQuestion(req.getQuestion());
        m.setChatbotAnswer(resp.getAnswer());
        m.setSource(resp.getSource());
        m.setElapsedMs(resp.getElapsed_ms());
        return m;
    }
}
