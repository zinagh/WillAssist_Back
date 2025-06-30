package messenger.messenger.repository;

import messenger.messenger.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<Chat, String> {
    List<Chat> findByChatId(String s);
}