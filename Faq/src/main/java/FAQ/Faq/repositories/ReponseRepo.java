package FAQ.Faq.repositories;

import FAQ.Faq.models.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseRepo extends JpaRepository<Reponse,Long> {
}
