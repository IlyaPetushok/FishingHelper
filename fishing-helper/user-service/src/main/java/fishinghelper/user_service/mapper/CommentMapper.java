package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.user_service.dto.CommentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDTO commentDTO);
    List<CommentDTO> toDTOs(List<Comment> comments);
}
