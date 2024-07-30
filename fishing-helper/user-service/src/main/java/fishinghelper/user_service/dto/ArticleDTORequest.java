package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTORequest {
    private String name;
    private String description;
    private Integer idUser;
    private boolean importance;
    private List<TagDTO> tagsList;
    private List<PhotoDTORequest> photos;
}
