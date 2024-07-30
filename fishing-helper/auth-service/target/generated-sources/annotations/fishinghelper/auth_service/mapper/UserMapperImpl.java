package fishinghelper.auth_service.mapper;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.common_module.entity.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T16:38:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTORequestRegistration userDTORequestRegistration) {
        if ( userDTORequestRegistration == null ) {
            return null;
        }

        User user = new User();

        user.setName( userDTORequestRegistration.getName() );
        user.setLogin( userDTORequestRegistration.getLogin() );
        user.setPassword( userDTORequestRegistration.getPassword() );
        user.setMail( userDTORequestRegistration.getMail() );

        return user;
    }
}
