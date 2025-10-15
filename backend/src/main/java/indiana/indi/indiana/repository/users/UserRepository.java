package indiana.indi.indiana.repository.users;

import indiana.indi.indiana.dtoInterface.users.*;
import indiana.indi.indiana.entity.users.Role;
import indiana.indi.indiana.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT
                u.id as id,
                u.username as username,
                u.password as password,
                r.title as role
            FROM User u
            JOIN u.role r
            WHERE u.username =:username
                """)
    Optional<AuthDtoInter> findByUsername(String username);

    @Query("""
            SELECT
                u.id as id,
                u.username as username,
                u.password as password
            FROM User u
            WHERE u.id =:userId
                """)
    Optional<EditUserDtoInter> findByIdEditUserDto(@Param("userId") Long userId);

    @Query("""
            SELECT 
                u.id as id,
                u.username as username,
                u.password as password,
                u.role.title as role
            FROM User u
            WHERE u.id=:userId  
                """)
    Optional<AdminEditUserDtoInter> findByIdAdminEditUserDto(Long userId);

    @Query("""
            SELECT 
                u.id as id,
                u.username as username,
                u.role.title as role,
                u.createdAt as createdAt
            FROM User u
            WHERE u.id =:userId
            """)
    Optional<ProfileDtoInter> getProfile(Long userId);

    @Query("""
            SELECT 
                u.id as userId,
                u.username as username,
                u.role.title as role,
                r.bodyRequest as requestUsers
            FROM User u
            LEFT JOIN RequestUsers r ON r.user.id = u.id
            WHERE u.id = :userId
            """)
    Optional<UserForAdminPanelDtoInter> getUserForAdminPanel(@Param("userId") Long userId);

    @Query("""
            SELECT 
                u.id as userId,
                u.username as username,
                u.role.title as role,
                r.bodyRequest as requestUsers
            FROM User u
            LEFT JOIN RequestUsers r ON r.user.id = u.id
            """)
    Page<UserForAdminPanelDtoInter> getAllUsers(Pageable pageable);

    @Modifying
    @Transactional
    @Query("""
            UPDATE User u
            SET u.username = :username,
                u.password = :password
            WHERE u.id =:userId
                """)
    int updateUserProfile(Long userId, String username, String password);

    @Modifying
    @Transactional
    @Query("""
            UPDATE User u
            SET u.username = :username,
                u.password = :password,
                u.role = :role
            WHERE u.id =:userId
                """)
    int updateUserByAdmin(Long userId, String username, String password, Role role);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM User u WHERE u.id =:userId
            """)
    void deleteUserById(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("""
            UPDATE User u
            SET u.role.id =:roleId
            WHERE u.id =:userId
            """)
    void updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_favorite_games(user_id, game_id)VALUES(:userId, :gameId)", nativeQuery = true)
    void addGameFavorite(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_favorite_games WHERE user_id=:userId AND game_id=:gameId", nativeQuery = true)
    void removeGameFavorite(@Param("userId") Long userId, @Param("gameId") Long gameId);

    boolean existsByIdAndPurchasedGamesId(Long userId, Long gameId);
}