package fishinghelper.user_service.service;

import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.PlaceDTORequest;
import fishinghelper.user_service.dto.PlaceDTOResponseAll;
import fishinghelper.user_service.dto.PlaceWithStatisticDTOResponse;
import fishinghelper.user_service.dto.TimeToCatchFish;
import fishinghelper.user_service.dto.filter.PlaceDTOFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceService {

    List<PlaceDTOResponseAll> showAllPlaces();

    List<PlaceDTOResponseAll> showAllPlaces(FilterRequest filterRequest);

    List<PlaceDTOResponseAll> showAllPlacesFilter(PlaceDTOFilter placeDTOFilter);

    PlaceWithStatisticDTOResponse showPlace(Integer id);

    TimeToCatchFish calculateStatisticTimeToCatch(List<Survey> surveyList);

    void createPlace(PlaceDTORequest placeDTORequest);
}
