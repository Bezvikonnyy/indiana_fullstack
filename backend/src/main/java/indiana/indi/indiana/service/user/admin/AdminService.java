package indiana.indi.indiana.service.user.admin;

import indiana.indi.indiana.dto.users.InviteCodeDto;
import indiana.indi.indiana.dto.users.UserDto;
import indiana.indi.indiana.dto.users.UserForAdminPanelDto;
import indiana.indi.indiana.entity.users.InviteCode;
import indiana.indi.indiana.entity.users.RequestUsers;
import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.mapper.users.InviteCodeMapper;
import indiana.indi.indiana.mapper.users.UserForAdminPanelMapper;
import indiana.indi.indiana.mapper.users.UserMapper;
import indiana.indi.indiana.repository.users.InviteCodeRepository;
import indiana.indi.indiana.repository.users.RequestUsersRepository;
import indiana.indi.indiana.repository.users.RoleRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import indiana.indi.indiana.service.invite.CRUDInviteCodeService;
import indiana.indi.indiana.service.user.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;

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
        user.setRoles(new HashSet<>(List.of(role)));
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
