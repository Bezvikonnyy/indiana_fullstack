package indiana.indi.indiana.service.user;

import indiana.indi.indiana.entity.InviteCode;
import indiana.indi.indiana.entity.RequestUsers;
import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.InviteCodeRepository;
import indiana.indi.indiana.repository.RequestUsersRepository;
import indiana.indi.indiana.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

    private final InviteCodeRepository inviteCodeRepository;

    private final RoleRepository roleRepository;

    private final CRUDUserDetailsService userDetailsService;

    private final RequestUsersRepository repoRequestUser;

    public int searchRole(int roleId, String inviteCode) {
        int defaultRoleId = 1;
        switch (roleId) {
            case 1,2 -> defaultRoleId = 1;
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

    public User saveUserRole(int id, String username, String password) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Нет такой роли"));
        final Set<Role> roles = Set.of(role);
        User user = userDetailsService.createUser(
                username,
                password,
                roles
        );
        return user;
    }

    public void requestAuthor(int roleId,User user) {
        if(roleId==2) {
            RequestUsers reqUser = new RequestUsers();
            reqUser.setUser(user);
            reqUser.setBodyRequest("Заявка на роль автор от пользователя" + user.getUsername() + "!");
            repoRequestUser.save(reqUser);
        }
    }
}
