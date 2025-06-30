package messenger.messenger.service;

import messenger.messenger.models.Chat;

import java.util.List;
import java.util.Optional;

public interface IChatService {

    public Chat save(Chat chatMessage);
    public List<Chat> findChatMessages (String senderId , String recipientId);

}
