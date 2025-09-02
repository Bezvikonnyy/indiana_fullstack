package indiana.indi.indiana.dtoInterface.users;

import indiana.indi.indiana.dtoInterface.cartAndPay.CartItemDtoInter;

import java.util.List;
import java.util.Set;

public interface UserDtoInter {
    Long getId();
    String getUserName();
    Set<RoleDtoInter> getRoles();
    List<CartItemDtoInter> getGames();
}
