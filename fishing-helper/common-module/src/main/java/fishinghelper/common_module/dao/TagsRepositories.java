package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepositories extends JpaRepository<Tags,Integer> {
//    @Query(value = "Select Tags from Tags where Tags.name in (:names)")
//    List<Tags> findAllByNames(@Param("names") List<String> name);
}
