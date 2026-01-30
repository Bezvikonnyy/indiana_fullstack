package indiana.indi.indiana.controller;

import indiana.indi.indiana.dto.users.InviteCodeDto;
import indiana.indi.indiana.dto.users.UserForAdminPanelDto;
import indiana.indi.indiana.service.user.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class UserAdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public Page<UserForAdminPanelDto> getAdminPanel(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size){
        return adminService.getAllUsers(page, size);
    }

    @PutMapping("/approve/{userId}")
    public UserForAdminPanelDto approvedRequest(@PathVariable Long userId){
        return adminService.approveRequest(userId);
    }

    @DeleteMapping("/delete/request/{userId}")
    public void deleteRequest(@PathVariable Long userId){
        adminService.deleteRequest(userId);
    }

    @PostMapping("/createInvite")
    public InviteCodeDto createInviteCode(){
        return adminService.createInviteCode();
    }

    @GetMapping("/inviteCode")
    public List<InviteCodeDto> getInviteCodes(){
        return adminService.getAllInviteCode();
    }

    @DeleteMapping("/delete/invite/{inviteCodeId}")
    public void deleteInviteCode(@PathVariable Long inviteCodeId){
        adminService.deleteInviteCode(inviteCodeId);
    }

    @DeleteMapping("/delete/user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        adminService.deleteUser(userId);
    }
}
