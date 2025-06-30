package messenger.messenger.repository;

import messenger.messenger.models.Status;
import messenger.messenger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    List<User> findAllByStatus(Status status);
    List<User> findAllByNickNameIn(Set<String> nickNames);


}
