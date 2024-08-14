package fishinghelper.admin_service.service;

import fishinghelper.admin_service.dto.*;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;

import java.util.List;

public interface AdminService {
    void updateRoleForUser(UserDTORequest userDTORequest, Integer id);
    List<UserDTOResponse> findUserByFilter(UserDTOFilter userDTOFilter);
    boolean checkUpdateUserRole(UserDTORequest userDTORequest, Integer id);
    void setConstrainUser(ConstrainDTO constrainDTO,Integer idUser);
    List<UserDTOResponseFindRole> findUsersByRole(String role);
    void deleteConstrainUser(ConstrainDTO constrainDTO, Integer id);
}
