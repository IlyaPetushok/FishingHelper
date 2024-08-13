package fishinghelper.admin_service.dto.filter;

import fishinghelper.common_module.filter.FilterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOFilter extends FilterRequest {
    private String name;
    private String mail;
    private boolean mailStatus;
    private Date dateRegistrationStart;
    private Date dateRegistrationFinish;
}
