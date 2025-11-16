package indiana.indi.indiana.repository.users;

import indiana.indi.indiana.entity.users.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {
    Optional<UserRequest> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM UserRequest r WHERE r.user.id =:userId
            """)
    void deleteByUserId(@Param("userId")Long userId);

}
