package fishinghelper.admin_service.controller;

import fishinghelper.admin_service.dto.ArticleDTO;
import fishinghelper.admin_service.dto.EntityType;
import fishinghelper.admin_service.dto.PlaceDTO;
import fishinghelper.admin_service.dto.RedactorDTO;
import fishinghelper.admin_service.service.ModeratorService;
import fishinghelper.common_module.filter.FilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ModeratorController {
    public ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_MODERATOR')")
    @GetMapping("/redactor/article/{status}")
    public ResponseEntity<?> showArticles(@PathVariable("status") String status, @RequestBody FilterRequest filterRequest){
        return new ResponseEntity<>(moderatorService.showArticleByStatus(status,filterRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE') and hasRole('ROLE_MODERATOR')")
    @PostMapping("/redactor/article/{id}/status")
    public ResponseEntity<?> updateStatusArticle(@PathVariable("id") Integer id, @RequestBody RedactorDTO redactorDTO){
        moderatorService.updateEntityStatus(redactorDTO,id, EntityType.ARTICLE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE') and hasRole('ROLE_MODERATOR')")
    @PostMapping("/redactor/article/{id}/update")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Integer id, @RequestBody ArticleDTO articleDTO){
        moderatorService.updateArticle(articleDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE') and hasRole('ROLE_MODERATOR')")
    @GetMapping("/article/{id}/delete")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Integer id){
        moderatorService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE') and hasRole('ROLE_MODERATOR')")
    @GetMapping("/redactor/place/{status}")
    public ResponseEntity<?> updateStatusPlace(@PathVariable("status") String status,@RequestBody FilterRequest filterRequest){
        return new ResponseEntity<>(moderatorService.showPlaceByStatus(status,filterRequest) ,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE') and hasRole('ROLE_MODERATOR')")
    @PostMapping("/redactor/place/{id}/status")
    public ResponseEntity<?> showPlaceNotProcessing(@PathVariable("id") Integer id,@RequestBody RedactorDTO redactorDTO){
        moderatorService.updateEntityStatus(redactorDTO,id, EntityType.PLACE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE') and hasRole('ROLE_MODERATOR')")
    @GetMapping("/place/{id}/delete")
    public ResponseEntity<?> deletePlace(@PathVariable("id") Integer id){
        moderatorService.deletePlace(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('UPDATE') and hasRole('ROLE_MODERATOR')")
    @PostMapping("/redactor/place/{id}/update")
    public ResponseEntity<?> updatePlace(@PathVariable("id") Integer id,@RequestBody PlaceDTO placeDTO){
        moderatorService.updatePlace(placeDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
