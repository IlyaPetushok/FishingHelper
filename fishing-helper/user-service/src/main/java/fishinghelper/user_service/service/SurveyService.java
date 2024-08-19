package fishinghelper.user_service.service;

import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.common_module.filter.FilterResponse;
import fishinghelper.user_service.dto.SurveyDTORequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SurveyService {
    void createSurvey(SurveyDTORequest surveyDTORequest, Integer idPlace);

    FilterResponse<SurveyDTORequest> findSurveyByFilter(FilterRequest filterRequest);
}
