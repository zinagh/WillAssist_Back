package FAQ.Faq.mapper;

import FAQ.Faq.dto.ChatRequest;
import FAQ.Faq.dto.ChatResponse;
import FAQ.Faq.models.ChatMessage;

public interface IChatMapper {

    public ChatMessage toEntity(ChatRequest req, ChatResponse resp);
}
