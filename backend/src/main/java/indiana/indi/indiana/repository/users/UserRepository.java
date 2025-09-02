package indiana.indi.indiana.repository.users;

import indiana.indi.indiana.entity.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername (String username);

    boolean existsByIdAndPurchasedGames_Id(Long userId, Long gameId);
}
