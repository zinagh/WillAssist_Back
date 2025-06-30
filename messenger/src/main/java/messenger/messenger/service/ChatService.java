package messenger.messenger.service;

import lombok.RequiredArgsConstructor;
import messenger.messenger.models.Chat;
import messenger.messenger.repository.ChatRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService{
    private final ChatRepo chatRepo;
    private final IChatRoom chatRoomService;
    public Chat save(Chat chatMessage){
        var chatId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId()
                , true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        return chatRepo.save(chatMessage);
    }
    public List<Chat> findChatMessages (String senderId , String recipientId){
        var chattId = chatRoomService.getChatRoomId(senderId , recipientId , false);
        return  chattId.map(chatRepo::findByChatId).orElse(new ArrayList<>());
    }
}