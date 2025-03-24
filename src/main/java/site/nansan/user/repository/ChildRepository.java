package site.nansan.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nansan.user.domain.Child;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findAllByIdIn(List<Long> ids);
}