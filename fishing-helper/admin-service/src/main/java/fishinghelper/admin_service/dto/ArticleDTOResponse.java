package fishinghelper.admin_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTOResponse {
    private String name;
    private String description;
    private boolean importance;
    private boolean processingStatus;
}
