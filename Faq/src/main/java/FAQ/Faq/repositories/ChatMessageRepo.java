package FAQ.Faq.repositories;

import FAQ.Faq.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepo  extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByUsernameOrderByCreatedAtDesc(String username);

}
