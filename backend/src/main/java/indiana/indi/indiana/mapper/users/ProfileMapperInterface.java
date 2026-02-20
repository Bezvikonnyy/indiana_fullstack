package indiana.indi.indiana.mapper.users;

import indiana.indi.indiana.dto.users.ProfileDto;
import indiana.indi.indiana.dto.users.RoleDto;
import indiana.indi.indiana.dtoInterface.users.ProfileDtoInter;
import indiana.indi.indiana.repository.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileMapperInterface {

    private final UserRepository userRepository;

    public ProfileDto toDto(Long id) {
        ProfileDtoInter profileInter = userRepository.getProfile(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        return new ProfileDto(
                profileInter.getId(),
                profileInter.getUsername(),
                new RoleDto(profileInter.getRole()),
                profileInter.getCreatedAt()
        );
    }
}
