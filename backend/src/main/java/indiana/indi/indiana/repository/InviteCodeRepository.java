package indiana.indi.indiana.repository;

import indiana.indi.indiana.entity.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {

    Optional<InviteCode> findFirstByUsedFalse();

    Optional<InviteCode> findByCode(String code);

    List<InviteCode> findAllByExpiresAtBefore(LocalDateTime time);
}
