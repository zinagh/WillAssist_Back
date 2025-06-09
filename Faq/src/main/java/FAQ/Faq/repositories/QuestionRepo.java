package FAQ.Faq.repositories;

import FAQ.Faq.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Long> {
    List<Question> findByQuestionTextContainingIgnoreCase(String keyword);
    List<Question> findByCreatedbyContainingIgnoreCase(String createdBy);
    @Query("SELECT q FROM Question q WHERE q.creationDate BETWEEN :startOfDay AND :endOfDay")
    List<Question> findByCreationDateBetween(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
    @Query("SELECT q FROM Question q WHERE YEAR(q.creationDate) = :year")
    List<Question> findByYear(@Param("year") int year);

    // Find questions by month
    @Query("SELECT q FROM Question q WHERE MONTH(q.creationDate) = :month")
    List<Question> findByMonth(@Param("month") int month);

    // Find questions by day
    @Query("SELECT q FROM Question q WHERE DAY(q.creationDate) = :day")
    List<Question> findByDay(@Param("day") int day);
}