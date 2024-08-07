package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.place.TypePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePlaceRepositories extends JpaRepository<TypePlace,Integer> {
    TypePlace findTypePlaceByName(String name);
}
