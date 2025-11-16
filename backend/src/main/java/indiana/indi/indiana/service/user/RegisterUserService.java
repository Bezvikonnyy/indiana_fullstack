package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.users.InviteCode;
import indiana.indi.indiana.entity.users.UserRequest;
import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;
import indiana.indi.indiana.repository.users.InviteCodeRepository;
import indiana.indi.indiana.repository.users.RoleRepository;
import indiana.indi.indiana.repository.users.UserRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final InviteCodeRepository inviteCodeRepository;

    private final RoleRepository roleRepository;

    private final CRUDUserServiceImpl userDetailsService;

    private final UserRequestRepository repoRequestUser;

    public int searchRole(int roleId, String inviteCode) {
        int defaultRoleId = 1;
        switch (roleId) {
            case 1,2 -> {
            }
            case 3 -> {
                if (inviteCode != null) {
                    Optional<InviteCode> optionalInvite = inviteCodeRepository.findByCode(inviteCode);
                    if (optionalInvite.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Инвайт код недействителен!");
                    }
                    InviteCode invite = optionalInvite.get();
                    invite.setUsed(true);
                    inviteCodeRepository.save(invite);
                    defaultRoleId = 3;
                }
                else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Инвайт код отсутствует!");
                }
            }
        }
        return defaultRoleId;
    }

    @Transactional
    public User saveUserRole(int roleId, String username, String password) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Нет такой роли"));
        return userDetailsService.createUser(
                username,
                password,
                role
        );
    }

    public void requestAuthor(int roleId,User user) {
        if(roleId==2) {
            UserRequest reqUser = new UserRequest();
            reqUser.setUser(user);
            reqUser.setBodyRequest("Заявка на роль автор от пользователя" + user.getUsername() + "!");
            repoRequestUser.save(reqUser);
        }
    }
}
