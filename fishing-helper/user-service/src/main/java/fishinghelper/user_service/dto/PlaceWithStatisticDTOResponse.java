package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PlaceWithStatisticDTOResponse {
    private PlaceDTOResponse placeDTOResponse;
    private TimeToCatchFish timeToCatchFish;
}
