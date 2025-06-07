package indiana.indi.indiana.controller;

import indiana.indi.indiana.controller.payload.NewUsersPayload;
import indiana.indi.indiana.dto.GameDto;
import indiana.indi.indiana.dto.RoleDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.entity.RequestUsers;
import indiana.indi.indiana.entity.Role;
import indiana.indi.indiana.entity.User;
import indiana.indi.indiana.repository.RoleRepository;
import indiana.indi.indiana.service.user.CustomUserDetails;
import indiana.indi.indiana.service.user.DefaultUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UsersController {

    private final DefaultUserDetailsService userDetailsService;

    private final RoleRepository roleRepository;

    private final RequestUsers requestUsers;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        UserDto dto = userDetailsService.userDtoCreate(user);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerUser (@Valid @RequestBody NewUsersPayload payload) {
        int roleId = 1;
        switch (payload.roleId()) {
            case 1 -> roleId = 1;
            case 2 -> {
                roleId = 1;
                requestUsers.setBodyRequest("Заявка на получение роли автора от пользователя.");
            }
            case 3 -> {
                if (payload.inviteCode()!= null) {roleId = 3;}
                else if(payload.inviteCode()==null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }
            }
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Нет такой роли"));
        Set<Role> roles = Set.of(role);
        User user = userDetailsService.createUser(
                payload.username(),
                payload.password(),
                roles
        );
        UserDto dto = userDetailsService.userDtoCreate(user);
        return ResponseEntity.ok(dto);
    }

//    @PutMapping("/edit_profile")
//    public ResponseEntity<?> editProfile () {
//
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser() {
//
//    }
}
