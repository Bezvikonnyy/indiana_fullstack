package indiana.indi.indiana.dtoInterface.users;

public interface UserForAdminPanelDtoInter {
    Long getId();
    String getUsername();
    RoleDtoInter getRole();
    String getRequestUsers();
}
