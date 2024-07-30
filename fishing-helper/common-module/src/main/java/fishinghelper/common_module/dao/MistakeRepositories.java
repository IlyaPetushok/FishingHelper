package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Mistake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MistakeRepositories extends JpaRepository<Mistake,Integer> {
}
