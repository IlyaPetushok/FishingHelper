package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:19:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTOResponse toDTOResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTOResponse userDTOResponse = new UserDTOResponse();

        userDTOResponse.setName( user.getName() );
        userDTOResponse.setLogin( user.getLogin() );
        userDTOResponse.setPassword( user.getPassword() );
        userDTOResponse.setMail( user.getMail() );
        userDTOResponse.setMailStatus( user.isMailStatus() );
        userDTOResponse.setDateRegistration( user.getDateRegistration() );
        List<Role> list = user.getRoles();
        if ( list != null ) {
            userDTOResponse.setRoles( new ArrayList<Role>( list ) );
        }

        return userDTOResponse;
    }
}
