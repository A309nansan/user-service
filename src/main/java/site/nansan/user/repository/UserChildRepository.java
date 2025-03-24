package site.nansan.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.nansan.user.domain.UserChild;

import java.util.List;

public interface UserChildRepository extends JpaRepository<UserChild, Long> {

    @Query("SELECT uc.childId FROM UserChild uc WHERE uc.userId = :userId")
    List<Long> findChildIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT uc.childId FROM UserChild uc WHERE uc.userId = :userId")
    Long findChildIdByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndChildId(Long userId, Long childId);

    UserChild findByUserIdAndChildId(Long userId, Long childId);

    void deleteByUserIdAndChildId(Long userId, Long childId);
}