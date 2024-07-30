package fishinghelper.user_service.dto.filter;

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
public class SurveyDTOFilter extends FilterRequest {
    private boolean morning;
    private boolean afternoon;
    private boolean evening;
    private List<Integer> fishIdList;

}
