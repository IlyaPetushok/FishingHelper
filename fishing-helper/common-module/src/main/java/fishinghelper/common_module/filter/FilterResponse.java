package fishinghelper.common_module.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterResponse<T> {
    private Long itemCounts;
    private Integer pageSize;
    private List<T> listObject;
}
