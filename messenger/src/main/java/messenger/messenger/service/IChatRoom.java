package messenger.messenger.service;

import messenger.messenger.models.Chat;

import java.util.List;
import java.util.Optional;

public interface IChatRoom {
    public Optional<String> getChatRoomId(
            String senderId ,
            String recipientId ,
            boolean createRoomIfNotExists);

}
