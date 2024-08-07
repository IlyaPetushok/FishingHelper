package fishinghelper.user_service.controller;

import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.PlaceDTORequest;
import fishinghelper.user_service.dto.SurveyDTORequest;
import fishinghelper.user_service.service.PlaceService;
import fishinghelper.user_service.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class AuthorController {
    private final PlaceService placeService;
    private final SurveyService surveyService;

    @Autowired
    public AuthorController(PlaceService placeService, SurveyService surveyService) {
        this.placeService = placeService;
        this.surveyService = surveyService;
    }

    @PreAuthorize("hasAuthority('CREATE') and hasRole('ROLE_AUTHOR')")
    @PostMapping("/create/place")
    public ResponseEntity<?> createPlace(@RequestBody PlaceDTORequest placeDTORequest){
        placeService.createPlace(placeDTORequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CREATE') and hasRole('ROLE_AUTHOR')")
    @PostMapping("/place/{id}/survey")
    public  ResponseEntity<?> creteSurvey(@RequestBody SurveyDTORequest surveyDTORequest, @PathVariable("id") Integer id){
        surveyService.createSurvey(surveyDTORequest,id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_AUTHOR')")
    @PostMapping("/survey/filter")
    public ResponseEntity<?> surveyFindByFilter(@RequestBody FilterRequest filterRequest){
        return new ResponseEntity<>(surveyService.findSurveyByFilter(filterRequest),HttpStatus.OK);
    }
}