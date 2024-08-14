package fishinghelper.user_service.service.impl;

import fishinghelper.common_module.dao.CommentRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.exception.PlaceNotFoundCustomException;
import fishinghelper.user_service.mapper.CommentMapper;
import fishinghelper.user_service.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing comments related to places.
 * @author Ilya Petushok
 */


@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {
    private final CommentMapper commentMapper;
    private final CommentRepositories commentRepositories;
    private final PlaceRepositories placeRepositories;

    @Autowired
    public CommentsServiceImpl(CommentMapper commentMapper, CommentRepositories commentRepositories, PlaceRepositories placeRepositories) {
        this.commentMapper = commentMapper;
        this.commentRepositories = commentRepositories;
        this.placeRepositories = placeRepositories;
    }


    /**
     * Creates a new comment for the specified place.
     *
     * @param commentDTO The DTO containing comment information to be created.
     * @param id         The unique identifier of the place where the comment is being created.
     * @throws PlaceNotFoundCustomException if no place exists with the specified ID.
     */
    @Override
//    transactional
    public void createCommentForPlace(CommentDTO commentDTO, Integer id) {
        log.info("Starting comment service method: createCommentForPlace");
        log.info("Received request to create comment: {} for place ID: {}", commentDTO, id);

        Place place=placeRepositories.findById(id)
                .orElseThrow(() -> new PlaceNotFoundCustomException(HttpStatus.NOT_FOUND,"Place not found for create comment"));

        Comment comment=commentMapper.toEntity(commentDTO);
        comment.setPlace(place);
        commentRepositories.save(comment);

        updateGradePlace(place);

        log.info("Comment created successfully for place ID: {}", id);
    }


    /**
     * Updates the grade (rating) of a place based on the comments associated with it.
     *
     * @param place The Place object whose grade is to be updated.
     *              This method assumes that the comments for the place have already been fetched and are up-to-date.
     */
    @Override
    public void updateGradePlace(Place place){
        List<Integer> grades=new ArrayList<>();

        showComments(place.getId()).forEach(commentDTO -> grades.add(commentDTO.getGrade()));
        place.setRating(grades.stream().mapToInt(grade -> grade).sum()/grades.size());

        placeRepositories.save(place);
    }

    /**
     * Retrieves a list of comments for the specified place.
     *
     * @param id The unique identifier of the place for which comments are being retrieved.
     * @return A list of CommentDTO objects mapped from the retrieved comments.
     */
    @Override
    public List<CommentDTO> showComments(Integer id) {
        log.info("Starting comment service method: showComments");
        log.info("Fetching comments for place ID: {}", id);
        return commentMapper.toDTOs(commentRepositories.findCommentByPlaceId(id));
    }

    /**
     * Retrieves comments for a specific place identified by the given ID.
     *
     * @param id             The ID of the place for which comments are to be retrieved.
     * @param filterRequest  The filter criteria for retrieving comments, including pagination details.
     * @return A list of {@link CommentDTO} objects representing comments associated with the place.
     */
    @Override
    public List<CommentDTO> showComments(Integer id, FilterRequest filterRequest) {
        log.info("Fetching comments for place with ID {} with filter: {}", id, filterRequest);
        return commentMapper.toDTOs(commentRepositories.findCommentByPlaceId(id,filterRequest.getPageable()));
    }
}
