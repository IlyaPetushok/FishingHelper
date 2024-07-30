package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.common_module.entity.user.Role;
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
public class RoleMapperImpl implements RoleMapper {

    @Override
    public List<Role> toEntities(List<RoleDTO> roleDTOList) {
        if ( roleDTOList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( roleDTOList.size() );
        for ( RoleDTO roleDTO : roleDTOList ) {
            list.add( roleDTOToRole( roleDTO ) );
        }

        return list;
    }

    protected Role roleDTOToRole(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( roleDTO.getId() );

        return role;
    }
}
