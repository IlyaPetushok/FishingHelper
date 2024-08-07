package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.fish.TypeFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFishRepositories extends JpaRepository<TypeFish,Integer> {
}
