package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.place.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepositories extends JpaRepository<Owner,Integer> {
    Owner findOwnerByNumber(String number);
}
