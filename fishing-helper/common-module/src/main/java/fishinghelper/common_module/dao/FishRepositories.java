package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.fish.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepositories extends JpaRepository<Fish,Integer> {
}
