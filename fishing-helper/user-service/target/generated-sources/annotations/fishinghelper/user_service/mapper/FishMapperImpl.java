package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.user_service.dto.FishDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:03:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class FishMapperImpl implements FishMapper {

    @Override
    public List<Fish> toEntities(List<FishDTO> fishDTOS) {
        if ( fishDTOS == null ) {
            return null;
        }

        List<Fish> list = new ArrayList<Fish>( fishDTOS.size() );
        for ( FishDTO fishDTO : fishDTOS ) {
            list.add( fishDTOToFish( fishDTO ) );
        }

        return list;
    }

    protected Fish fishDTOToFish(FishDTO fishDTO) {
        if ( fishDTO == null ) {
            return null;
        }

        Fish fish = new Fish();

        fish.setId( fishDTO.getId() );
        fish.setName( fishDTO.getName() );

        return fish;
    }
}
