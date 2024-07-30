package fishinghelper.common_module.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    private Integer page=0;
    private Integer size=10;
    private String sortDirection="asc";
    private String sortByName="id";
    private Pageable pageable;


    public Pageable getPageable(){
        return pageable= PageRequest.of(page,size, Sort.by(CustomSortDirection.getSortDirection(sortDirection),sortByName));
    }
}
