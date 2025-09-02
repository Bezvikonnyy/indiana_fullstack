package indiana.indi.indiana.repository.users;

import indiana.indi.indiana.entity.users.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {

    Optional<InviteCode> findFirstByUsedFalse();

    Optional<InviteCode> findByCode(String code);

    List<InviteCode> findAllByExpiresAtBefore(LocalDateTime time);
}
