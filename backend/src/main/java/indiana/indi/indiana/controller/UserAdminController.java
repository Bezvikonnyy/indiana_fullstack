package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.InviteCodeDto;
import indiana.indi.indiana.dto.UserDto;
import indiana.indi.indiana.dto.UserForAdminPanelDto;
import indiana.indi.indiana.service.user.CustomUserDetails;
import indiana.indi.indiana.service.user.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class UserAdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserForAdminPanelDto> getAdminPanel(@AuthenticationPrincipal CustomUserDetails userDetails)
            throws AccessDeniedException {return adminService.getAllUsers(userDetails);
    }

    @PutMapping("/approve/{id}")
    public UserDto approvedRequest(@AuthenticationPrincipal CustomUserDetails admin, @PathVariable Long id)
            throws AccessDeniedException {return adminService.approveRequest( id,admin);
    }

    @DeleteMapping("/delete/request/{id}")
    public void deleteRequest(@AuthenticationPrincipal CustomUserDetails admin, @PathVariable Long id)
            throws AccessDeniedException {adminService.deleteRequest(id, admin);}

    @PostMapping("/create_invite")
    public InviteCodeDto createInviteCode(@AuthenticationPrincipal CustomUserDetails admin)
            throws AccessDeniedException {return adminService.createInviteCode(admin);}

    @GetMapping("/invite_code")
    public List<InviteCodeDto> getInviteCodes(@AuthenticationPrincipal CustomUserDetails admin)
            throws AccessDeniedException {return adminService.getAllInviteCode(admin);}

    @DeleteMapping("/delete/invite/{id}")
    public void deleteInviteCode(@AuthenticationPrincipal CustomUserDetails admin, @PathVariable Long id)
            throws AccessDeniedException {adminService.deleteInviteCode(admin,id);}

    @DeleteMapping("/delete/user/{id}")
    public void deleteUser(@AuthenticationPrincipal CustomUserDetails admin, @PathVariable Long id)
            throws AccessDeniedException {adminService.deleteUser(admin,id);}
}
