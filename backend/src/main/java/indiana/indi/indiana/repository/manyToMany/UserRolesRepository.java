package indiana.indi.indiana.repository.manyToMany;

import indiana.indi.indiana.entity.manyToManyEntities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
}
