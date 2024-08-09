package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepositories extends JpaRepository<Role,Integer> {
    Role findRoleByName(String name);
    List<Role> findRolesByNameIn(List<String> name);
//    List<Role> selectFindByPriviliges(String namePriviliges);
}
