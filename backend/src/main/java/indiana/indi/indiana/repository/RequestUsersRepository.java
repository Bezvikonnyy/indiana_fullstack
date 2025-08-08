package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.RequestUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestUsersRepository extends JpaRepository <RequestUsers, Long> {
    Optional<RequestUsers> findByUserId(Long userId);

}
