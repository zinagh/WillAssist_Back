package FAQ.Faq.repositories;

import FAQ.Faq.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Long> {
    List<Question> findByQuestionTextContainingIgnoreCase(String keyword);
    List<Question> findByCreatedbyContainingIgnoreCase(String createdBy);


}
