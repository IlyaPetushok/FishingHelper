package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.user_service.dto.FishDTO;
import fishinghelper.user_service.dto.SurveyDTORequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:03:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class SurveyMapperImpl implements SurveyMapper {

    @Override
    public Survey toEntity(SurveyDTORequest surveyDTORequest) {
        if ( surveyDTORequest == null ) {
            return null;
        }

        Survey survey = new Survey();

        survey.setUser( mapToUserList( surveyDTORequest.getUserId() ) );
        survey.setMorning( surveyDTORequest.isMorning() );
        survey.setAfternoon( surveyDTORequest.isAfternoon() );
        survey.setEvening( surveyDTORequest.isEvening() );
        survey.setFishList( fishDTOListToFishList( surveyDTORequest.getFishList() ) );

        return survey;
    }

    @Override
    public SurveyDTORequest toDTO(Survey survey) {
        if ( survey == null ) {
            return null;
        }

        SurveyDTORequest surveyDTORequest = new SurveyDTORequest();

        surveyDTORequest.setUserId( getIdUser( survey.getUser() ) );
        surveyDTORequest.setMorning( survey.isMorning() );
        surveyDTORequest.setAfternoon( survey.isAfternoon() );
        surveyDTORequest.setEvening( survey.isEvening() );
        surveyDTORequest.setFishList( fishListToFishDTOList( survey.getFishList() ) );

        return surveyDTORequest;
    }

    protected Fish fishDTOToFish(FishDTO fishDTO) {
        if ( fishDTO == null ) {
            return null;
        }

        Fish fish = new Fish();

        fish.setId( fishDTO.getId() );
        fish.setName( fishDTO.getName() );

        return fish;
    }

    protected List<Fish> fishDTOListToFishList(List<FishDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Fish> list1 = new ArrayList<Fish>( list.size() );
        for ( FishDTO fishDTO : list ) {
            list1.add( fishDTOToFish( fishDTO ) );
        }

        return list1;
    }

    protected FishDTO fishToFishDTO(Fish fish) {
        if ( fish == null ) {
            return null;
        }

        FishDTO fishDTO = new FishDTO();

        fishDTO.setId( fish.getId() );
        fishDTO.setName( fish.getName() );

        return fishDTO;
    }

    protected List<FishDTO> fishListToFishDTOList(List<Fish> list) {
        if ( list == null ) {
            return null;
        }

        List<FishDTO> list1 = new ArrayList<FishDTO>( list.size() );
        for ( Fish fish : list ) {
            list1.add( fishToFishDTO( fish ) );
        }

        return list1;
    }
}
