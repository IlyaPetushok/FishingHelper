package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.user_service.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {CommentMapperImpl.class})
public class CommentMapperTest {
    @Autowired
    private  CommentMapper commentMapper;

    @Test
    public void testToEntity() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1);
        commentDTO.setText("This is a comment");

        Comment comment = commentMapper.toEntity(commentDTO);

        assertNotNull(comment);
        assertEquals(1, comment.getId());
        assertEquals("This is a comment", comment.getText());
    }

    @Test
    public void testToDTOs() {
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setText("This is the first comment");

        Comment comment2 = new Comment();
        comment2.setId(2);
        comment2.setText("This is the second comment");

        List<Comment> comments = List.of(comment1, comment2);

        List<CommentDTO> commentDTOs = commentMapper.toDTOs(comments);

        assertNotNull(commentDTOs);
        assertEquals(2, commentDTOs.size());

        CommentDTO dto1 = commentDTOs.get(0);
        assertEquals(1, dto1.getId());
        assertEquals("This is the first comment", dto1.getText());

        CommentDTO dto2 = commentDTOs.get(1);
        assertEquals(2, dto2.getId());
        assertEquals("This is the second comment", dto2.getText());
    }
}
