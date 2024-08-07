package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTOResponse {
    private Integer id;
    private String name;
    private String coordinates;
    private String description;
    private boolean requirePayment;
    private OwnerDTO owner;
    private TypePlaceDTO typePlace;
    private List<PhotoDTOResponse> photos;
    private List<FishDTO> fish;
    private List<CommentDTO> comments;
}
