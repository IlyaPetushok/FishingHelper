package fishinghelper.user_service.dto.filter;

import fishinghelper.common_module.entity.place.TypePlace;
import fishinghelper.common_module.filter.FilterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTOFilter extends FilterRequest {
    private TypePlace placeType;
    private boolean paid;
    private Integer ratingStart;
    private Integer ratingFinish;
    private List<String> fishNameList;
}
