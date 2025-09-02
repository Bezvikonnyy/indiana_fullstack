package indiana.indi.indiana.dtoInterface;

import java.util.List;
import java.util.Set;

public interface UserForAdminPanelDtoInter {
    Long getId();
    String getUsername();
    Set<RoleDtoInter> getRoles();
    List<CartItemDtoInter> getGame();
    String getRequestUsers();
}
