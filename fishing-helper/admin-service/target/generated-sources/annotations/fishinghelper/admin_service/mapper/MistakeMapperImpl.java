package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.MistakeDTO;
import fishinghelper.common_module.entity.common.Mistake;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:19:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class MistakeMapperImpl implements MistakeMapper {

    @Override
    public Mistake toEntity(MistakeDTO mistakeDTO) {
        if ( mistakeDTO == null ) {
            return null;
        }

        Mistake mistake = new Mistake();

        mistake.setText( mistakeDTO.getText() );

        return mistake;
    }
}
