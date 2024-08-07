package fishinghelper.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private Integer id;
    private String name;
    private String description;
    private boolean importance;
    private String status;
    private List<PhotoDTO> photoDTOS;
    private UserDTORequest userDTORequest;
    private List<WordsForFindDTO> tags;
}
