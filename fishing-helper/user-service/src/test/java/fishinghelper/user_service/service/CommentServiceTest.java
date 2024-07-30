package fishinghelper.user_service.service;

import fishinghelper.common_module.dao.CommentRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.mapper.CommentMapper;
import fishinghelper.user_service.service.impl.CommentsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepositories commentRepositories;

    @Mock
    private PlaceRepositories placeRepositories;

    @InjectMocks
    private CommentsServiceImpl commentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCommentForPlace() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setGrade(5);
        Integer placeId = 1;

        Place place = new Place();
        place.setId(placeId);

        Comment comment = new Comment();
        comment.setGrade(5);

        when(placeRepositories.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(place));
        when(commentMapper.toEntity(any(CommentDTO.class))).thenReturn(comment);
        when(commentMapper.toDTOs(any())).thenReturn(List.of(commentDTO));

        commentsService.createCommentForPlace(commentDTO, placeId);

        verify(commentRepositories, times(1)).save(comment);
        verify(placeRepositories, times(1)).save(place);
    }

    @Test
    void testShowComments() {
        Integer placeId = 1;
        Comment comment = new Comment();
        comment.setGrade(5);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setGrade(5);

        when(commentRepositories.findCommentByPlaceId(anyInt())).thenReturn(List.of(comment));
        when(commentMapper.toDTOs(any())).thenReturn(List.of(commentDTO));

        List<CommentDTO> result = commentsService.showComments(placeId);

        verify(commentRepositories, times(1)).findCommentByPlaceId(anyInt());
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getGrade());
    }

    @Test
    void testShowCommentsWithFilter() {
        Integer placeId = 1;
        FilterRequest filterRequest = new FilterRequest();
        Comment comment = new Comment();
        comment.setGrade(4);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setGrade(4);

        when(commentRepositories.findCommentByPlaceId(anyInt(), any())).thenReturn(List.of(comment));
        when(commentMapper.toDTOs(any())).thenReturn(List.of(commentDTO));

        List<CommentDTO> result = commentsService.showComments(placeId, filterRequest);

        verify(commentRepositories, times(1)).findCommentByPlaceId(anyInt(), any());
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getGrade());
    }
}
