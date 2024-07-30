package fishinghelper.user_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTORequest {
    private String name;
    private String coordinates;
    private boolean requirePayment;
    private String description;
    private List<PhotoDTORequest> photos;
    private List<Integer> fish;
    private TypePlaceDTO typePlace;
    private OwnerDTO owner;
}
