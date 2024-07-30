package fishinghelper.user_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTOResponse {
    private String name;
    private String description;
    private UserDTO user;
    private boolean importance;
}
