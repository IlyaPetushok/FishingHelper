package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepositories  extends JpaRepository<Photo,Integer> {
}
