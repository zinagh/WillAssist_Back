package FAQ.Faq.service;

import FAQ.Faq.dto.ChatRequest;
import FAQ.Faq.dto.ChatResponse;
import FAQ.Faq.models.ChatMessage;

import java.util.List;

public interface IChatService {
    public ChatResponse ask(ChatRequest req) ;
    public List<ChatMessage> history(String username) ;
}
