package fishinghelper.user_service.controller;

import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.TagsDTO;
import fishinghelper.user_service.dto.filter.ArticleDTOFilter;
import fishinghelper.user_service.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/article")
public class UserArticleController {
    private final ArticleService articleService;

    @Autowired
    public UserArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findArticleById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(articleService.findArticleById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @PostMapping("/find")
    public ResponseEntity<?> findArticleByTags(@RequestBody TagsDTO tagsDTO) {
        return new ResponseEntity<>(articleService.findArticle(tagsDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> findArticlesByFilter(@RequestBody FilterRequest filterRequest){
        return new ResponseEntity<>(articleService.showAllArticle(filterRequest),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and hasRole('ROLE_USER')")
    @PostMapping("/filter")
    public ResponseEntity<?> findArticleByFilter(@RequestBody ArticleDTOFilter articleDTOFilter){
        return new ResponseEntity<>(articleService.showAllArticleFilter(articleDTOFilter),HttpStatus.OK);
    }
}
