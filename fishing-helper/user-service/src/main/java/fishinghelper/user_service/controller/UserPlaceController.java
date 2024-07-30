package fishinghelper.user_service.controller;

import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.dto.filter.PlaceDTOFilter;
import fishinghelper.user_service.exception.PlaceNotFoundCustomException;
import fishinghelper.user_service.service.CommentsService;
import fishinghelper.user_service.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/place")
public class UserPlaceController {

    private final PlaceService placeService;
    private final CommentsService commentsService;

    @Autowired
    public UserPlaceController(PlaceService placeService, CommentsService commentsService) {
        this.placeService = placeService;
        this.commentsService = commentsService;
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> showAllPlace() {
        return new ResponseEntity<>(placeService.showAllPlaces(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> showPlace(@PathVariable("id") Integer id) throws PlaceNotFoundCustomException {
        return new ResponseEntity<>(placeService.showPlace(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE') and hasRole('ROLE_USER')")
    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createComment(@PathVariable("id") Integer id, @RequestBody CommentDTO commentDTO) {
        commentsService.createCommentForPlace(commentDTO, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @GetMapping("/{id}/comments")
    public ResponseEntity<?> showComments(@PathVariable("id") Integer id,@RequestBody FilterRequest filterRequest) {
        return new ResponseEntity<>(commentsService.showComments(id,filterRequest), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> findPlaceByFilter(@RequestBody FilterRequest filterRequest){
        return new ResponseEntity<>(placeService.showAllPlaces(filterRequest),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @PostMapping("/filter")
    public ResponseEntity<?> findPlaceByFilter(@RequestBody PlaceDTOFilter placeDTOFilter){
        return new ResponseEntity<>(placeService.showAllPlacesFilter(placeDTOFilter),HttpStatus.OK);
    }
}
