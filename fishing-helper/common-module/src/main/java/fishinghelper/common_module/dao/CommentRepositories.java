package fishinghelper.common_module.dao;

import fishinghelper.common_module.entity.common.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositories extends JpaRepository<Comment,Integer> {
    List<Comment> findCommentByPlaceId(Integer id);
    List<Comment> findCommentByPlaceId(Integer id, Pageable pageable);
//    void save(Comment comment,Integer idPlace);
}
