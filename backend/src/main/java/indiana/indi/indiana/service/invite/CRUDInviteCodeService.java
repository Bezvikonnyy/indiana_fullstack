package indiana.indi.indiana.service.invite;

import indiana.indi.indiana.entity.users.InviteCode;
import indiana.indi.indiana.repository.users.InviteCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CRUDInviteCodeService implements CRUDInviteCodeInterfaceService{

    private final InviteCodeRepository repository;

    @Override
    public InviteCode createInviteCode() {
        InviteCode inviteCode = new InviteCode();
        inviteCode.setCode(UUID.randomUUID().toString());
        inviteCode.setUsed(false);
        inviteCode.setCreatedAt(LocalDateTime.now());
        inviteCode.setExpiresAt(LocalDateTime.now().plusDays(1));
        return this.repository.save(inviteCode);
    }

    @Override
    public InviteCode getInviteCode() {
        return repository.findFirstByUsedFalse().orElseThrow(() -> new EntityNotFoundException("Not found inviteCode"));
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void deleteInviteCode() {
        LocalDateTime time = LocalDateTime.now();
        List<InviteCode> delete = repository.findAllByExpiresAtBefore(time);
        repository.deleteAll(delete);
    }

    @Override
    public void deleteInviteCodeByCode(String code) {
        InviteCode invite = this.repository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Invite code not found."));
        repository.delete(invite);
    }
}
