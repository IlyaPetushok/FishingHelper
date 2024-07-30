package fishinghelper.admin_service.controller;

import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;
import fishinghelper.admin_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    public AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasAuthority('UPDATE') and (hasRole('ROLE_AUTHOR') or" +
            " (hasRole('ROLE_MODERATOR') and @adminServiceImpl.checkUpdateUserRole(#userDTORequest,#id)))")
    @PostMapping("/admin/user/{id}/role")
    public ResponseEntity<?> adminUpdateRole(@PathVariable("id") Integer id, @RequestBody UserDTORequest userDTORequest) {
        adminService.updateRoleForUser(userDTORequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ') and (hasRole('ROLE_AUTHOR') or hasRole('ROLE_MODERATOR'))")
    @PostMapping("/users/filter")
    public ResponseEntity<?> findUserByFilter(@RequestBody UserDTOFilter userDTOFilter) {
        return new ResponseEntity<>(adminService.findUserByFilter(userDTOFilter), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/{id}/constrain")
    public ResponseEntity<?> userSetConstrain(@PathVariable("id") Integer id, @RequestBody ConstrainDTO constrainDTO){
        adminService.setConstrainUser(constrainDTO,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
