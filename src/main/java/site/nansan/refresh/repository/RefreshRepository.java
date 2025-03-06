package site.nansan.refresh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.nansan.refresh.domain.Refresh;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
