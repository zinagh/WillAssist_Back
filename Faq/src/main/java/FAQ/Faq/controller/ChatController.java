package FAQ.Faq.controller;

import FAQ.Faq.dto.ChatRequest;
import FAQ.Faq.dto.ChatResponse;
import FAQ.Faq.models.ChatMessage;
import FAQ.Faq.service.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final IChatService chatService;


    @PostMapping("/ask")
    public ChatResponse ask(@RequestBody ChatRequest req) {
        return chatService.ask(req);
    }

    @GetMapping("/history/{username}")
    public List<ChatMessage> history(@PathVariable String username) {
        return chatService.history(username);
    }
}
