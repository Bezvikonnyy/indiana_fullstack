package indiana.indi.indiana.service.user.admin;

import indiana.indi.indiana.dto.users.InviteCodeDto;
import indiana.indi.indiana.dto.users.UserForAdminPanelDto;
import indiana.indi.indiana.dtoInterface.users.UserForAdminPanelDtoInter;
import indiana.indi.indiana.entity.users.InviteCode;
import indiana.indi.indiana.mapper.users.InviteCodeMapper;
import indiana.indi.indiana.mapper.users.UserForAdminPanelMapper;
import indiana.indi.indiana.repository.users.InviteCodeRepository;
import indiana.indi.indiana.repository.users.RequestUsersRepository;
import indiana.indi.indiana.repository.users.UserRepository;
import indiana.indi.indiana.service.invite.CRUDInviteCodeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    private final InviteCodeRepository inviteRepository;

    private final InviteCodeMapper inviteCodeMapper;

    private final UserForAdminPanelMapper userMapper;

    private final RequestUsersRepository requestUsers;

    private final CRUDInviteCodeService inviteCode;

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserForAdminPanelDto> getAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserForAdminPanelDtoInter> users = userRepository.getAllUsers(pageable);
        return users.map(userMapper::toDto);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserForAdminPanelDto approveRequest(Long userId){
        requestUsers.findByUserId(userId).ifPresent(requestUsers::delete);
        userRepository.updateUserRole(userId, 2L);
        UserForAdminPanelDtoInter user = userRepository.getUserForAdminPanel(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        return userMapper.toDto(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRequest(Long userId){
        requestUsers.deleteByUserId(userId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public InviteCodeDto createInviteCode(){
        return inviteCodeMapper.toDto(inviteCode.createInviteCode());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<InviteCodeDto> getAllInviteCode(){
        List<InviteCode> inviteCodes = inviteRepository.findAll().stream().toList();
        return inviteCodes.stream().
                map(inviteCodeMapper::toDto).toList();
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteInviteCode(Long inviteId){
        inviteRepository.deleteById(inviteId);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId){
        userRepository.deleteUserById(userId);
    }
}
