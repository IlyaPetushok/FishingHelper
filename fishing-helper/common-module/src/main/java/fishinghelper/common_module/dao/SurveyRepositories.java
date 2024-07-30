package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SurveyRepositories extends JpaRepository<Survey,Integer> {
//    @Modifying
//    @Query(value = "insert into survey(survey_id_user,survey_id_place,survey_id_time) values (:id_user,:id_place,:id:time)",nativeQuery = true)
//    void saveSurvey(@Param("id_user") Integer idUser,@Param("id_place") Integer idPlace,@Param("id_time") Integer idTime);
//
//    @Modifying
//    @Query(value = "insert  into survey_fish value (:id_fish,:id_survey)",nativeQuery = true)
//    void saveSurveyToFish(@Param("id_fish") Integer idFish,@Param("id_survey") Integer idSurvey);
    List<Survey> findAllByPlaceId(Integer idPlace);
}
