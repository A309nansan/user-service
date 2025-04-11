package site.nansan.userchild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.nansan.userchild.domain.UserChild;
import site.nansan.userchild.domain.UserChildId;

import java.util.List;

public interface UserChildRepository extends JpaRepository<UserChild, UserChildId> {

    @Query("SELECT uc.id.childId FROM UserChild uc WHERE uc.id.userId = :userId")
    List<Long> findChildIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT uc.id.childId FROM UserChild uc WHERE uc.id.userId = :userId")
    Long findChildIdByUserId(@Param("userId") Long userId);

    boolean existsById_UserIdAndId_ChildId(Long userId, Long childId);

    UserChild findById_UserIdAndId_ChildId(Long userId, Long childId);

    void deleteById_UserIdAndId_ChildId(Long userId, Long childId);
}
