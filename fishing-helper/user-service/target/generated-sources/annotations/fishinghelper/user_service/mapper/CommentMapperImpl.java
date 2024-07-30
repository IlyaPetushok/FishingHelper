package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:03:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setText( commentDTO.getText() );
        comment.setGrade( commentDTO.getGrade() );
        comment.setUser( userDTOToUser( commentDTO.getUser() ) );

        return comment;
    }

    @Override
    public List<CommentDTO> toDTOs(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( commentToCommentDTO( comment ) );
        }

        return list;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setName( userDTO.getName() );

        return user;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );

        return userDTO;
    }

    protected CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setText( comment.getText() );
        commentDTO.setGrade( comment.getGrade() );
        commentDTO.setUser( userToUserDTO( comment.getUser() ) );

        return commentDTO;
    }
}
