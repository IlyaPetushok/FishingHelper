package fishinghelper.user_service.controller;


import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class
JournalistController {

    private final ArticleService articleService;

    @Autowired
    public JournalistController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("hasAuthority('CREATE') and hasRole('ROLE_JOURNALIST')")
    @PostMapping("/create/article")
    public ResponseEntity<?> createArticle(@RequestBody ArticleDTORequest articleDTORequest){
        articleService.createArticle(articleDTORequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
