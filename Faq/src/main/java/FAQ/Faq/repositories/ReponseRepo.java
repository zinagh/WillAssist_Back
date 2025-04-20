package FAQ.Faq.repositories;

import FAQ.Faq.models.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReponseRepo extends JpaRepository<Reponse,Long> {

    @Query("SELECT r FROM Reponse r WHERE r.question.idQuestion = :questionId")
    Optional<Reponse> findByQuestionId(@Param("questionId") Long questionId);
}
