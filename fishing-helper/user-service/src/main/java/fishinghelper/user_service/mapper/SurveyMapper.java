package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.SurveyDTORequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
    @Mapping(target="user",source = "userId", qualifiedByName = "mapToUserList")
    Survey toEntity(SurveyDTORequest surveyDTORequest);

    @Mapping(target = "userId", source = "user",qualifiedByName = "getIdUser")
    SurveyDTORequest toDTO(Survey survey);

    @Named("mapToUserList")
    default User mapToUserList(Integer usersId){
        return new User(usersId);
    }

    @Named("getIdUser")
    default Integer getIdUser(User user){
        return user.getId();
    }
}
