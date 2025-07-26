package indiana.indi.indiana.service.user.admin;

import indiana.indi.indiana.dto.InviteCodeDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.dto.UserForAdminPanelDto;
import indiana.indi.indiana.entity.InviteCode;
import indiana.indi.indiana.entity.RequestUsers;
import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.mapper.InviteCodeMapper;
import indiana.indi.indiana.mapper.UserForAdminPanelMapper;
import indiana.indi.indiana.mapper.UserMapper;
import indiana.indi.indiana.repository.InviteCodeRepository;
import indiana.indi.indiana.repository.RequestUsersRepository;
import indiana.indi.indiana.repository.RoleRepository;
import indiana.indi.indiana.repository.UserRepository;
import indiana.indi.indiana.service.invite.CRUDInviteCodeService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final InviteCodeRepository inviteRepository;

    private final UserMapper mapper;

    private final InviteCodeMapper inviteCodeMapper;

    private final UserForAdminPanelMapper userMapper;

    private final RequestUsersRepository requestUsers;

    private final RoleRepository roleRepository;

    private final CRUDInviteCodeService inviteCode;

    private void checkAdmin(CustomUserDetails user) throws AccessDeniedException {
        if(!user.isAdmin()){
            throw new AccessDeniedException("Access denied!");
        }
    }

    public List<UserForAdminPanelDto> getAllUsers(CustomUserDetails user) throws AccessDeniedException {
        checkAdmin(user);
        List<User> users = userRepository.findAll();
        return users.stream().map(us -> {
            RequestUsers request = requestUsers.findByUserId(us.getId()).orElse(null);
            String bodyRequest = request != null ? request.getBodyRequest() : null;
            return userMapper.toDto(us, bodyRequest);
        }).toList();
    }

    public UserDto approveRequest(Long id, CustomUserDetails admin) throws AccessDeniedException {
        checkAdmin(admin);
        requestUsers.findByUserId(id).ifPresent(requestUsers::delete);
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found."));
        Role role = roleRepository.findById(2).orElseThrow(() -> new EntityNotFoundException("Role not found."));
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return mapper.toDto(user);
    }

    public void deleteRequest(Long id, CustomUserDetails admin) throws AccessDeniedException {
        checkAdmin(admin);
        requestUsers.findByUserId(id).ifPresent(requestUsers::delete);
    }

    public InviteCodeDto createInviteCode(CustomUserDetails admin) throws AccessDeniedException {
        checkAdmin(admin);
        return inviteCodeMapper.toDto(inviteCode.createInviteCode());
    }

    public List<InviteCodeDto> getAllInviteCode(CustomUserDetails admin) throws AccessDeniedException {
        checkAdmin(admin);
        List<InviteCode> inviteCodes = inviteRepository.findAll().stream().toList();
        return inviteCodes.stream().
                map(inviteCodeMapper::toDto).toList();
    }

    public void deleteInviteCode(CustomUserDetails admin, Long id) throws AccessDeniedException {
        checkAdmin(admin);
        inviteRepository.findById(id).ifPresent(inviteRepository::delete);
    }

    public void deleteUser(CustomUserDetails admin, Long id) throws AccessDeniedException {
        checkAdmin(admin);
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
