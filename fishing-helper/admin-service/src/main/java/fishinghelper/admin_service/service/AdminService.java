package fishinghelper.admin_service.service;

import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;

import java.util.List;

public interface AdminService {
    void updateRoleForUser(UserDTORequest userDTORequest, Integer id);
    List<UserDTOResponse> findUserByFilter(UserDTOFilter userDTOFilter);
    boolean checkUpdateUserRole(UserDTORequest userDTORequest, Integer id);
    void setConstrainUser(ConstrainDTO constrainDTO,Integer idUser);
}
