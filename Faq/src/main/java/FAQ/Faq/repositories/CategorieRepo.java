package FAQ.Faq.repositories;

import FAQ.Faq.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepo extends JpaRepository<Categorie,Long> {

    List<Categorie> findByCategorieNameContainingIgnoreCase(String name);
    Categorie findByCategorieName(String name);


}
