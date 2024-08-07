package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTOResponseAll {
    private Integer id;
    private String name;
    private String coordinates;
    private boolean requirePayment;
    private TypePlaceDTO typePlace;
    private List<PhotoDTOResponse> photos;
}
