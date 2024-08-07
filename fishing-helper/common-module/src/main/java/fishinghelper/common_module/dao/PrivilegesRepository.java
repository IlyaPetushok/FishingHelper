package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.user.Privileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegesRepository extends JpaRepository<Privileges,Integer> {
    List<Privileges> findAllByNameIn(List<String> name);
}
