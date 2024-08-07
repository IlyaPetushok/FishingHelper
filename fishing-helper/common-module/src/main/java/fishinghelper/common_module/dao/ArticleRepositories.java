package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepositories extends JpaRepository<Article, Integer> {
    List<Article> findAllByStatus(String status);
    List<Article> findAllByStatus(String status,Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tagsList t WHERE t.name IN :tags")
    List<Article> findByTags(@Param("tags") List<String> tags);

    Page<Article> findAll(Specification<Article> articleSpecification, Pageable pageable);
}

