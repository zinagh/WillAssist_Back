package FAQ.Faq.service;

import FAQ.Faq.dto.ChatRequest;
import FAQ.Faq.dto.ChatResponse;
import FAQ.Faq.mapper.ChatMapper;
import FAQ.Faq.mapper.IChatMapper;
import FAQ.Faq.models.ChatMessage;
import FAQ.Faq.repositories.ChatMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {
    private final WebClient botClient;
    private final ChatMapper mapper;
    private final ChatMessageRepo repo;

    public ChatResponse ask(ChatRequest req) {
        ChatResponse resp = botClient.post()
                .uri("/ask")
                .bodyValue(req)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        repo.save(mapper.toEntity(req, resp));
        return resp;
    }

    public List<ChatMessage> history(String username) {
        return repo.findByUsernameOrderByCreatedAtDesc(username);
    }
}
