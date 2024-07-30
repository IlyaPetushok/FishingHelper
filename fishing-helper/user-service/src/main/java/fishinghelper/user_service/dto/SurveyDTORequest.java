package fishinghelper.user_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDTORequest {
    private Integer userId;
    private boolean morning;
    private boolean afternoon;
    private boolean evening;
    private List<FishDTO> fishList;
}
