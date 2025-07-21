package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.RequestUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestUsersRepository extends JpaRepository <RequestUsers, Long> {
    Optional<RequestUsers> findByUserId(Long userId);

}
