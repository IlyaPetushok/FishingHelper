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
public class ArticleDTOFilter extends FilterRequest {
    private String name;
    private boolean importance;
    private List<String> tags;
}
