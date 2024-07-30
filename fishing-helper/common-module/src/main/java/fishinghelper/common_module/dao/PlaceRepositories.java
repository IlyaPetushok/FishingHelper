package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.place.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepositories extends JpaRepository<Place, Integer> {
//    List<Place> findAll();
//    List<Place> getFindByStatusProcessing(boolean flag);
//    List<Place> selectPlaceFindByType(String typeName);
//    List<Place> selectPlaceFindByPaid(boolean flag);
//    List<Place> selectPlaceFindByProcessingStatus(boolean flag);
    List<Place> findAllByStatus(String status);
    List<Place> findAllByStatus(String status,Pageable pageable);
    Page<Place> findAll(Specification<Place> placeSpecification, Pageable pageable);
}
