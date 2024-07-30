package fishinghelper.user_service.service;

import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentsService {
    void createCommentForPlace(CommentDTO commentDTO, Integer id);

    void updateGradePlace(Place place);

    List<CommentDTO> showComments(Integer id);

    List<CommentDTO> showComments(Integer id, FilterRequest filterRequest);
}
